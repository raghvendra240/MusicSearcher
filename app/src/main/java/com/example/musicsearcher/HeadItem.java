package com.example.musicsearcher;

public class HeadItem extends ListItem {

    private String date;

    public String getHeader() {
        return date;
    }

    public void setHeader(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}