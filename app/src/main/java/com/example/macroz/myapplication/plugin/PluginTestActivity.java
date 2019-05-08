package com.example.macroz.myapplication.plugin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macroz.myapplication.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginTestActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_test);
        String apkPath = Environment.getExternalStorageDirectory() + "/plugin_test.apk";
        loadApk(apkPath);

        Resources pluginRes = getPluginResources(apkPath);

        try {
            Drawable pluginDrawable = pluginRes.getDrawable(pluginRes.getIdentifier("plugin_icon", "drawable", "com.example.macroz.plugintestapplication"));

            String pluginText = pluginRes.getString(pluginRes.getIdentifier("plugin_text", "string", "com.example.macroz.plugintestapplication"));

            TextView textView = findViewById(R.id.plugin_test_text);
            ImageView imageView = findViewById(R.id.plugin_test_img);
            textView.setText(pluginText);
            imageView.setImageDrawable(pluginDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Resources getPluginResources(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
             AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, apkPath);
            return new Resources(assetManager, this.getResources().getDisplayMetrics(), getResources().getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void loadApk(String apkPath) {
        Log.v("loadDexClasses", "Dex Preparing to loadDexClasses!");
        File dexOpt = this.getDir("myDexOpt", MODE_PRIVATE);
        final DexClassLoader classloader = new DexClassLoader(apkPath,
                dexOpt.getAbsolutePath(),
                null,
                this.getClassLoader());
        Log.v("loadDexClasses", "Searching for class : " + "com.registry.Registry");
        try {
            Class<?> classToLoad = (Class<?>) classloader.loadClass("com.example.macroz.plugintestapplication.MainActivity");
            Object instance = classToLoad.newInstance();
            Method method = classToLoad.getMethod("method");
            method.invoke(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
