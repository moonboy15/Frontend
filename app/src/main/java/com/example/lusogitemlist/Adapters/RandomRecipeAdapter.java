package com.example.lusogitemlist.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lusogitemlist.Models.Meal;
import com.example.lusogitemlist.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<Meal> list;

    public RandomRecipeAdapter(Context context, List<Meal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).strMeal);
        holder.textView_title.setSelected(true);
        holder.textView_categories.setText(list.get(position).strCategory);
        Picasso.get().load((Uri) list.get(position).strImageSource).into(holder.imageView_food);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView random_list_container;
    TextView textView_title, textView_categories;
    ImageView imageView_food;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_categories = itemView.findViewById(R.id.textView_categories);
        imageView_food = itemView.findViewById(R.id.imageView_food);

    }
}
