package com.hyj.invoke;

import java.lang.invoke.*;

public class TestInvokeDynamic {


    public static void main(String[] args) throws Throwable {
        INDY_BootstrapMethod().invokeExact("sssss");
    }

    public static void testMethod(String s){
        System.out.println(s);
    }

    public static CallSite BootstrapMethod(MethodHandles.Lookup lookup,String name,MethodType mt) throws Throwable{
        return new ConstantCallSite(lookup.findStatic(TestInvokeDynamic.class,name,mt));
    }

    private static MethodType MT_BootstrapMethod(){
        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;" +
                "Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",null);
    }

    private static MethodHandle MH_BootstrapMethod() throws Throwable{
        return MethodHandles.lookup().findStatic(TestInvokeDynamic.class,"BootstrapMethod",MT_BootstrapMethod());
    }

    private static MethodHandle INDY_BootstrapMethod() throws Throwable{
        CallSite cs = (CallSite)MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(),"testMethod",MethodType.
                fromMethodDescriptorString("(Ljava/lang/String;)V",null));
        return cs.dynamicInvoker();
    }
}
