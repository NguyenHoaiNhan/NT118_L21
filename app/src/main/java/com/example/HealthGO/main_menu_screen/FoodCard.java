package com.example.HealthGO.main_menu_screen;

import android.widget.ImageView;

import javax.xml.transform.Source;

public class FoodCard {
    private String Title;
    private String Description;
    private String ImageSource;
    private String Source;

    public FoodCard(){}
    public FoodCard(String Title, String Description, String ImageSource, String source) {
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
}
