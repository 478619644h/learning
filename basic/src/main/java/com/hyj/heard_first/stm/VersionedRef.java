package com.hyj.heard_first.stm;

/**
 * 带版本号的对象引用
 * @param <T>
 */
public final class VersionedRef<T> {

    final T value;

    final long version;

    public VersionedRef(T value, long version) {
        this.value = value;
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VersionedRef{");
        sb.append("value=").append(value);
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
