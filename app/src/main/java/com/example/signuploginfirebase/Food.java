package com.example.signuploginfirebase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Food implements Parcelable {

    private String name;
    private String recipe;
    private String description;
    private String category;
    private String type;
    private int calories;
    private String benefit;
    private List<String> ingredients;
    private List<String> measurements;
    private String image;
    private List<String> ingredientsImage;

    public Food(String name, String recipe, String description, String image, String category, String type, int calories, String benefit, List<String> ingredients, List<String> measurements, List<String> ingredientsImage) {
        this.name = name;
        this.recipe = recipe;
        this.description = description;
        this.category = category;
        this.type = type;
        this.calories = calories;
        this.benefit = benefit;
        this.ingredients = ingredients;
        this.measurements = measurements;
        this.image = image;
        this.ingredientsImage = ingredientsImage;
    }

    protected Food(Parcel in) {
        name = in.readString();
        recipe = in.readString();
        description = in.readString();
        category = in.readString();
        type = in.readString();
        calories = in.readInt();
        benefit = in.readString();
        ingredients = in.createStringArrayList();
        measurements = in.createStringArrayList();
        image = in.readString();
        ingredientsImage = in.createStringArrayList();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public int getCalories() {
        return calories;
    }

    public String getBenefit() {
        return benefit;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public List<String> getIngredientsImage() {
        return ingredientsImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(recipe);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeInt(calories);
        dest.writeString(benefit);
        dest.writeStringList(ingredients);
        dest.writeStringList(measurements);
        dest.writeString(image);
        dest.writeStringList(ingredientsImage);
    }
}

