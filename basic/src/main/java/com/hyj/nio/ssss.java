package com.hyj.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ssss {


    public static void main(String[] args) {


        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("/Users/huangyujian/fsdownload/123456"))))){

            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
               /* String[] strings = line.split("\\@\\|\\@");

                for (String s : strings) {
                    System.out.print(s + " ");
                }
                System.out.println();*/
            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
