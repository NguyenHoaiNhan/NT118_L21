package com.example.HealthGO.food;

import android.widget.ImageView;

import javax.xml.transform.Source;

public class FoodCard {
    private String Id;
    private String Title;
    private String Description;
    private String ImageSource;
    private String Source;
    private double Rating;

    public FoodCard(){}

    public FoodCard(String Id, String Title, String ImageSource, String source, double Rating) {
        this.Id = Id;
        this.Title = Title;
        this.ImageSource = ImageSource;
        this.Source = source;
        this.Rating = Rating;
    }
    public FoodCard(String Id, String Title, String Description, String ImageSource, String source) {
        this.Id = Id;
        this.Title = Title;
        this.Description = Description;
        this.ImageSource = ImageSource;
        this.Source = source;
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

    public String getId() {return this.Id;}
}
