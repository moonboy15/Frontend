package com.example.lusogitemlist.Listeners;

import com.example.lusogitemlist.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
