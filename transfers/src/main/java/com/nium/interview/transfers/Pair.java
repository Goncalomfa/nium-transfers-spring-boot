package com.nium.interview.transfers;

public class Pair {

    private int key;
    private float value;

    public Pair(int key, float value) {
        this.key = key;
        this.value = value;
    }
    public int getKey() {
        return key;
    }
    public float getValue() {
        return value;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public void setValue(float value) {
        this.value = value;
    }
}
