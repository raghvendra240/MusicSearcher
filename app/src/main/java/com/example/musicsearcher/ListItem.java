package com.example.musicsearcher;

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();
}