package com.example.HealthGO.main_menu_screen;

import android.widget.ImageView;

public class FoodCard {
    private String Title;
    private String Description;
    private int ImageSource;

    public FoodCard(){}
    public FoodCard(String Title, String Description, int ImageSource) {
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

    public int getImageSource() {
        return ImageSource;
    }
}
