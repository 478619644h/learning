package com.hyj.memory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BTraceTest {

    public int add(int a,int b){
        return  a+b;
    }

    public static void main(String[] args) throws IOException {
        BTraceTest bt = new BTraceTest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            reader.readLine();
            int a = (int)Math.round(Math.random()*1000);
            int b = (int)Math.round(Math.random()*1000);
            System.out.println(bt.add(a,b));
        }


    }

}
