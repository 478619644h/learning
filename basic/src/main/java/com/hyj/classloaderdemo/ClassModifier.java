package com.hyj.classloaderdemo;

/**
 * 修改class文件暂时只提供修改常量池常量的功能
 */
public class ClassModifier {

    /**
     * class文件中常量池的起始偏移
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * CONSTANT_Utf8_info 常量的tag标志
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中11种常量所占的长度，CONSTANT_Utf8_info型常量除外，因为他不是定长的
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1,-1,-1,5,5,9,9,3,3,5,5,5,5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     * @param oldStr 修改前的字符串
     * @param newStr 修改后的字符串
     * @return 修改结果
     */
    public byte[] modifyUTF8Constant(String oldStr,String newStr){
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for(int i = 0;i<cpc;i++){
            int tag = ByteUtil.byte2Int(classByte,offset,u1);
            if(tag == CONSTANT_Utf8_info){

                int len = ByteUtil.byte2Int(classByte,offset+u1,u2);
                offset += (u1+u2);
                String str = ByteUtil.bytes2String(classByte,offset,len);

                System.out.println(str);
                if(str.equalsIgnoreCase(oldStr)){
                    byte[] strBytes = ByteUtil.string2Bytes(newStr);
                    byte[] strLen = ByteUtil.int2Bytes(newStr.length(),u2);
                    classByte = ByteUtil.bytesReplace(classByte,offset-u2,u2,strLen);
                    classByte = ByteUtil.bytesReplace(classByte,offset,len,strBytes);
                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    /**
     * 获取常量池中常量的数量
     * @return 常量池数量
     */
    public int getConstantPoolCount(){
        return ByteUtil.byte2Int(classByte,CONSTANT_POOL_COUNT_INDEX,u2);
    }
}
