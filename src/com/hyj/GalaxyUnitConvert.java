package com.hyj;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GalaxyUnitConvert {

    static Map<String,String> galaxyUnitMap = new HashMap<>();

    public static void main(String[] args) {
        /*String filePath = "./test.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String strVal = null;
            while ((strVal = br.readLine()) != null){
                String str = strVal.toUpperCase();
                int index = str.indexOf(" IS ");
                String isBefore = str.substring(0,index);
                String isAfter = str.substring(index+3);
                //设置值 为罗马
                if(!str.endsWith("?")&&"".equals(getNumbers(str))){
                    galaxyUnitMap.put(isBefore.trim(),isAfter.trim());
                }
                //设置值 为数值
                if(!str.endsWith("?")&&!"".equals(getNumbers(str))){
                    String knownRomanStr = "";
                    String unKnownGalaxyStr = "";
                    String galaxyUnits[] = isBefore.split(" ");
                    int arabicNum = Integer.valueOf(getNumbers(isAfter.trim()));
                    for(int i = 0;i < galaxyUnits.length;i++){
                        int flag = 0;
                        if(flag > 1){
                            System.out.println("输入不合法");
                        }
                        if(galaxyUnitMap.containsKey(galaxyUnits[i])){
                            knownRomanStr += galaxyUnitMap.get(galaxyUnits[i]);
                        } else {
                            flag++;
                            unKnownGalaxyStr = galaxyUnits[i];
                        }
                    }
                    int num = arabicNum - romanToInt(knownRomanStr);
                    galaxyUnitMap.put(unKnownGalaxyStr,intToRoman(num));
                }
                if(str.endsWith("?")){
                    isAfter = isAfter.substring(0,isAfter.indexOf("?"));
                    String romanUnit = "";
                    for(String galaxyUnit:isAfter.split(" ")){
                        String s = galaxyUnitMap.get(galaxyUnit.trim());
                        if(s != null&&!"".equals(s)){
                            romanUnit += galaxyUnitMap.get(galaxyUnit.trim());
                        }
                    }
                    System.out.println(isAfter + " is " + romanToInt(romanUnit) + " Credits");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
        System.out.println(romanToInt("MCMXLIV "));
    }

    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    static boolean verify(String str){
        String regEx = "/^M*(?:D?C{0,3}|C[MD])(?:L?X{0,3}|X[CL])(?:V?I{0,3}|I[XV])$/i";
        // 忽略大小写的写法
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    static int romanToInt(String str) {
        if(verify(str)){
            System.out.println("输入的罗马数字不合法 ：" + str);
            System.exit(1);
        }
        char[] s = str.toCharArray();
        int tagVal[] = new int[120];
        tagVal['I'] = 1;
        tagVal['V'] = 5;
        tagVal['X'] = 10;
        tagVal['C'] = 100;
        tagVal['M'] = 1000;
        tagVal['L'] = 50;
        tagVal['D'] = 500;
        int val = 0;
        for(int i = 0; i < s.length; i++){
            if(i+1 >= s.length || tagVal[s[i+1]] <= tagVal[s[i]]){
                val += tagVal[s[i]];
            } else {
                val -= tagVal[s[i]];
            }
        }
        return val;
    }


    static String intToRoman(int num) {
        if(num <= 0) return "";
        String ret = "";
        int number[] = {1000, 900, 500, 400, 100,90, 50, 40, 10, 9, 5, 4, 1};
        String flags[] = {"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for(int i = 0; i < 13 && num > 0; i++){
            if(num < number[i]) continue;
            while(num >= number[i]){
                num-= number[i];
                ret += flags[i];
            }

        }
        return ret;
    }



}
