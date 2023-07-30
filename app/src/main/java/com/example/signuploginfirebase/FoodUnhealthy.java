package com.example.signuploginfirebase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class FoodUnhealthy implements Parcelable {

    private String name;
    private String description;
    private String category;
    private String type;
    private int calories;
    private String disbenefit;
    private String alternative;
    private List<String> ingredients;
    private String image;
    private List<String> ingredientsImage;


    protected FoodUnhealthy(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        type = in.readString();
        calories = in.readInt();
        disbenefit = in.readString();
        alternative = in.readString();
        ingredients = in.createStringArrayList();
        image = in.readString();
        ingredientsImage = in.createStringArrayList();
    }

    public static final Creator<FoodUnhealthy> CREATOR = new Creator<FoodUnhealthy>() {
        @Override
        public FoodUnhealthy createFromParcel(Parcel in) {
            return new FoodUnhealthy(in);
        }

        @Override
        public FoodUnhealthy[] newArray(int size) {
            return new FoodUnhealthy[size];
        }
    };

    public FoodUnhealthy(String name, String description, String image, String category, String type, int calories, String disbenefit, String alternative, List<String> ingredients, List<String> ingredientsImage) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.calories = calories;
        this.disbenefit = disbenefit;
        this.ingredients = ingredients;
        this.alternative = alternative;
        this.image = image;
        this.ingredientsImage = ingredientsImage;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
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
    public String getAlternative(){return alternative;}
    public String getDisbenefit() {
        return disbenefit;
    }
    public List<String> getIngredients() {
        return ingredients;
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
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeInt(calories);
        dest.writeString(disbenefit);
        dest.writeString(alternative);
        dest.writeStringList(ingredients);
        dest.writeString(image);
        dest.writeStringList(ingredientsImage);
    }
}

