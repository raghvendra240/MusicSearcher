package com.example.musicsearcher;

public class GeneralItem extends ListItem {

    private Result FilteredResult;

    public Result getGeneralItem() {
        return FilteredResult;
    }

    public void setGeneralItem(Result pojoOfJsonArray) {
        this.FilteredResult = pojoOfJsonArray;
    }


    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}
