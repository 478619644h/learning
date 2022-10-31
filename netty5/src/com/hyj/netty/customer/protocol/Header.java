package com.hyj.netty.customer.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

public final class Header {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(0xabef0101);
        System.out.println(Integer.reverseBytes(buffer.readInt()));
    }

    private int crcCode = 0xabef0101;
    /**
     * 消息长度
     */
    private int length;

    /**
     * 会话id
     */
    private long sessionID;

    /**
     * 消息类型
     */
    private byte type;

    /**
     * 消息优先级
     */
    private byte priority;

    /**
     * 附件
     */
    private Map<String,Object> attachment = new HashMap<>(16);

    public int getCrcCode() {
        return crcCode;
    }

    public Header setCrcCode(int crcCode) {
        this.crcCode = crcCode;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Header setLength(int length) {
        this.length = length;
        return this;
    }

    public long getSessionID() {
        return sessionID;
    }

    public Header setSessionID(long sessionID) {
        this.sessionID = sessionID;
        return this;
    }

    public byte getType() {
        return type;
    }

    public Header setType(byte type) {
        this.type = type;
        return this;
    }

    public byte getPriority() {
        return priority;
    }

    public Header setPriority(byte priority) {
        this.priority = priority;
        return this;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public Header setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Header{");
        sb.append("crcCode=").append(crcCode);
        sb.append(", length=").append(length);
        sb.append(", sessionID=").append(sessionID);
        sb.append(", type=").append(type);
        sb.append(", priority=").append(priority);
        sb.append(", attachment=").append(attachment);
        sb.append('}');
        return sb.toString();
    }
}
