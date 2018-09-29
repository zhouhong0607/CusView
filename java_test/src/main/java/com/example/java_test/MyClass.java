package com.example.java_test;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
    public static void main(String[] args){
        List<String> list=new ArrayList<>();
        list.add("AAA");
        list.add(null);
        list.add(null);
        for(String s:list){
            System.out.print(s);
        }



    }
}
