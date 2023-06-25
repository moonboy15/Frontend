package com.example.lusogitemlist.Listeners;

import com.example.lusogitemlist.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
