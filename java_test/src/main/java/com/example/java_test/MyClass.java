package com.example.java_test;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("AAA");
        list.add(null);
        list.add(null);
        for (String s : list) {
            System.out.print(s);
        }

        String s = "123";
        s = s + null;
        Object object = new Object() {
            @Override
            public String toString() {
                return "aaa";
            }
        };
        s = s + object;

        Object object1 = null;

        StringBuilder sb = new StringBuilder();
        sb.append("123");
        String sss = null;
        sb.append(sss);
        sb.toString();
    }
}
