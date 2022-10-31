package com.hyj.memory;


/**
 * 虚拟机栈溢出
 * vm args -Xss128k  栈容量
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();
        try {
            javaVMStackSOF.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length : " + javaVMStackSOF.stackLength);
            throw  e;
        }
    }

}
