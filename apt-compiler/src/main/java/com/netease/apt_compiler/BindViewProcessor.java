package com.netease.apt_compiler;


import com.google.auto.service.AutoService;
import com.netease.apt_annotation.BindGo;
import com.netease.apt_annotation.BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

/**
 * @author: 周宏
 * @Date: 2020-07-08
 * @Description:
 */
@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer mFilerUtils;
    private Types mTypesUtils;
    private Elements mElementsUtils;

    private Map<TypeElement, Set<ViewInfo>> mToBindMap = new HashMap<>(); //用于记录需要绑定的View的名称和对应的id


    private Map<TypeElement, GoInfo> mGoMap = new HashMap<>();

    @Override

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFilerUtils = processingEnv.getFiler();
        mTypesUtils = processingEnv.getTypeUtils();
        mElementsUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        supportTypes.add(BindGo.class.getCanonicalName());
        return supportTypes; //将要支持的注解放入其中
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();// 表示支持最新的Java版本
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        System.out.println("start process");
        if (set != null && set.size() != 0) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);//获得被BindView注解标记的element

            Set<? extends Element> goElements = roundEnv.getElementsAnnotatedWith(BindGo.class);

            categories(elements);//对不同的Activity进行分类


            categoriesGo(goElements);


            //对不同的Activity生成不同的帮助类
            for (TypeElement typeElement : mToBindMap.keySet()) {
                String code = generateCode(typeElement);    //获取要生成的帮助类中的所有代码
                String helperClassName = typeElement.getQualifiedName() + "$$Autobind"; //构建要生成的帮助类的类名

                //输出帮助类的java文件，在这个例子中就是MainActivity$$Autobind.java文件
                //输出的文件在build->source->apt->目录下
                try {
                    JavaFileObject jfo = mFilerUtils.createSourceFile(helperClassName, typeElement);
                    Writer writer = jfo.openWriter();
                    writer.write(code);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            for (TypeElement typeElement : mGoMap.keySet()) {
                String goCode = generateGoCode(typeElement, mGoMap.get(typeElement));
                String helperClassName = typeElement.getQualifiedName() + "$$AutobindGo"; //构建要生成的帮助类的类名

                try {
                    JavaFileObject jfo = mFilerUtils.createSourceFile(helperClassName, typeElement);
                    Writer writer = jfo.openWriter();
                    writer.write(goCode);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return true;
        }

        return false;
    }

    //将需要绑定的View按不同Activity进行分类
    private void categories(Set<? extends Element> elements) {
        for (Element element : elements) {  //遍历每一个element
            VariableElement variableElement = (VariableElement) element;    //被@BindView标注的应当是变量，这里简单的强制类型转换
            TypeElement enclosingElement = (TypeElement) variableElement.getEnclosingElement(); //获取代表Activity的TypeElement
            Set<ViewInfo> views = mToBindMap.get(enclosingElement); //views储存着一个Activity中将要绑定的view的信息
            if (views == null) {    //如果views不存在就new一个
                views = new HashSet<>();
                mToBindMap.put(enclosingElement, views);
            }
            BindView bindAnnotation = variableElement.getAnnotation(BindView.class);    //获取到一个变量的注解
            int id = bindAnnotation.value();    //取出注解中的value值，这个值就是这个view要绑定的xml中的id
            views.add(new ViewInfo(variableElement.getSimpleName().toString(), id));    //把要绑定的View的信息存进views中
        }

    }


    private void categoriesGo(Set<? extends Element> elements) {
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement encloseingElement = (TypeElement) variableElement.getEnclosingElement();
            GoInfo goInfo = mGoMap.get(encloseingElement);
            if (goInfo == null) {
                BindGo bindAnnotation = variableElement.getAnnotation(BindGo.class);
                String key = bindAnnotation.key();
                goInfo = new GoInfo(key, variableElement);
                mGoMap.put(encloseingElement, goInfo);
            }
        }
    }


    //按不同的Activity生成不同的帮助类
    private String generateCode(TypeElement typeElement) {
        String rawClassName = typeElement.getSimpleName().toString(); //获取要绑定的View所在类的名称
        String packageName = ((PackageElement) mElementsUtils.getPackageOf(typeElement)).getQualifiedName().toString(); //获取要绑定的View所在类的包名
        String helperClassName = rawClassName + "$$Autobind";   //要生成的帮助类的名称

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n");   //构建定义包的代码
        builder.append("import com.netease.apt_api.IBindHelper;\n\n"); //构建import类的代码

        builder.append("public class ").append(helperClassName).append(" implements ").append("IBindHelper");   //构建定义帮助类的代码
        builder.append(" {\n"); //代码格式，可以忽略
        builder.append("\t@Override\n");    //声明这个方法为重写IBindHelper中的方法
        builder.append("\tpublic void inject(" + "Object" + " target ) {\n");   //构建方法的代码
        for (ViewInfo viewInfo : mToBindMap.get(typeElement)) { //遍历每一个需要绑定的view
            builder.append("\t\t"); //代码格式，可以忽略
            builder.append(rawClassName + " substitute = " + "(" + rawClassName + ")" + "target;\n");    //强制类型转换

            builder.append("\t\t"); //代码格式，可以忽略
            builder.append("substitute." + viewInfo.viewName).append(" = ");    //构建赋值表达式
            builder.append("substitute.findViewById(" + viewInfo.id + ");\n");  //构建赋值表达式
        }
        builder.append("\t}\n");    //代码格式，可以忽略
        builder.append('\n');   //代码格式，可以忽略
        builder.append("}\n");  //代码格式，可以忽略

        return builder.toString();
    }


    private String generateGoCode(TypeElement typeElement, GoInfo goInfo) {
        String rawClassName = typeElement.getSimpleName().toString(); //获取要绑定的View所在类的名称
        String packageName = ((PackageElement) mElementsUtils.getPackageOf(typeElement)).getQualifiedName().toString(); //获取要绑定的View所在类的包名
        String helperClassName = rawClassName + "$$AutobindGo";   //要生成的帮助类的名称

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n");   //构建定义包的代码
        builder.append("import com.netease.apt_api.IBindGoHelper;\n\n"); //构建import类的代码

        builder.append("public class ").append(helperClassName).append(" implements ").append("IBindGoHelper");   //构建定义帮助类的代码
        builder.append(" {\n"); //代码格式，可以忽略
        builder.append("\t@Override\n");    //声明这个方法为重写IBindHelper中的方法
        builder.append("\tpublic void inject(" + "Object" + " target ) {\n");   //构建方法的代码

        builder.append("\t\t"); //代码格式，可以忽略
        builder.append(rawClassName + " substitute = " + "(" + rawClassName + ")" + "target;\n");    //强制类型转换

        builder.append("\t\t"); //代码格式，可以忽略
        builder.append("substitute." + goInfo.mVariableElement.getSimpleName().toString()).append(" = ");    //构建赋值表达式
        //todo zh 这里如何取到变量的类



        builder.append("(" + goInfo.mVariableElement.asType().toString() + ")" + "substitute.getIntent().getSerializableExtra(" + "\"" + goInfo.key + "\"" + ");\n");  //构建赋值表达式


        builder.append("\t}\n");    //代码格式，可以忽略
        builder.append('\n');   //代码格式，可以忽略
        builder.append("}\n");  //代码格式，可以忽略

        return builder.toString();
    }


    //要绑定的View的信息载体
    class ViewInfo {
        String viewName;    //view的变量名
        int id; //xml中的id

        public ViewInfo(String viewName, int id) {
            this.viewName = viewName;
            this.id = id;
        }
    }

    class GoInfo {
        String key;
        VariableElement mVariableElement;

        public GoInfo(String key, VariableElement variableElement) {
            this.key = key;
            mVariableElement = variableElement;
        }
    }
}


