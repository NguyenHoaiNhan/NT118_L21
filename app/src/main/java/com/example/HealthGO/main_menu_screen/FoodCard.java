package com.example.HealthGO.main_menu_screen;

import android.widget.ImageView;

public class FoodCard {
    private String Title;
    private String Description;
    private String ImageSource;

    public FoodCard(){}
    public FoodCard(String Title, String Description, String ImageSource) {
        this.Title = Title;
        this.Description = Description;
        this.ImageSource = ImageSource;
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
}
