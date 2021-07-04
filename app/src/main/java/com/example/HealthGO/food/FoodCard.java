package com.example.HealthGO.food;

import android.widget.ImageView;

import javax.xml.transform.Source;

public class FoodCard {
    private String Title;
    private String Description;
    private String ImageSource;
    private String Source;
    private String id;
    private double Rating;

    public FoodCard(){}

    public FoodCard(String Title, String ImageSource, String source, double Rating, String id) {
        this.Title = Title;
        this.ImageSource = ImageSource;
        this.Source = source;
        this.Rating = Rating;
        this.id = id;
    }
    public FoodCard(String Title, String Description, String ImageSource, String source) {
        this.Title = Title;
        this.Description = Description;
        this.ImageSource = ImageSource;
        this.Source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getImageSource() {
        return ImageSource;
    }

    public String getSource(){
        return this.Source;
    }

    public double getRating(){
        return this.Rating;
    }
}
