package com.hyj.algorithm;

public class PetEntery {

    private Pet data;

    private long time;

    public PetEntery(Pet data, long time) {
        this.data = data;
        this.time = time;
    }

    public PetEntery() {
        this.data = data;
        this.time = time;
    }

    public Pet getData() {
        return data;
    }

    public void setData(Pet data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
