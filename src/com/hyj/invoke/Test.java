package com.hyj.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class Test {

    class GrandFather{

        void thinking(){
            System.out.println(" i am grandfather");
        }
    }

    class Father extends GrandFather{
        @Override
        void thinking(){
            System.out.println("i am father");
           // super.thinking();
        }
    }

    class Son extends Father{

        @Override
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);


                /**
                 * IMPL_LOOKUP 是用来判断私有方法是否被信任的标识，用来控制访问权限的.默认是false,
                 * 默认情况下findSpecial方法中的最后一个参数Class<?> specialCaller 有一个校验 checkSpecialCaller,
                 * 如果当前的lookup的类和specialCaller不一致的话就会检测不通过,
                 * IMPL_LOOKUP.setAccessible(true);设置为true之后，(MethodHandles.Lookup) IMPL_LOOKUP.get(null)
                 * 这是获取一个Lookup，
                 * 这种方式返回的allowedModes为-1 这样的话就可以绕过检查，从而执行执行传入specialCaller类中的方法，当然也有风险，
                 * 舍弃了强校验，很容易抛出NoSuchMethodError.
                 */
                Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                IMPL_LOOKUP.setAccessible(true);
                MethodHandles.Lookup lkp = (MethodHandles.Lookup)IMPL_LOOKUP.get(null);
                MethodHandle m = lkp.findSpecial(GrandFather.class,"thinking",mt,GrandFather.class);
                m.invoke(new Son());

                MethodHandle mh = MethodHandles.lookup().findVirtual(GrandFather.class,"thinking",mt).bindTo(new GrandFather());
                mh.invokeExact();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new Test().new Son()).thinking();
    }
}
