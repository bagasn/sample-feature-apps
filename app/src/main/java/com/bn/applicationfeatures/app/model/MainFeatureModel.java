package com.bn.applicationfeatures.app.model;

import androidx.annotation.DrawableRes;

public class MainFeatureModel {
    private long id;
    private int resource;
    private String title;

    public MainFeatureModel(long id, int resource, String title) {
        this.id = id;
        this.resource = resource;
        this.title = title;
    }

    public MainFeatureModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(@DrawableRes int resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
