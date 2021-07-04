package com.example.HealthGO.workout;

public class Gym {
    private String name;
    private String imgURL;
    private String source;
    private String id;

    public Gym(String name, String imgURL, String source, String id) {
        this.name = name;
        this.imgURL = imgURL;
        this.source = source;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
