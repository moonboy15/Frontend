package com.example.signuploginfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnhealthyIngredientsAdapter extends RecyclerView.Adapter<UnhealthyIngredientsAdapter.ViewHolder> {

    private List<String> ingredientList;

    private IngredientsAdapter.OnIngredientClickListener onIngredientClickListener;

    public UnhealthyIngredientsAdapter(List<String> ingredientList, IngredientsAdapter.OnIngredientClickListener listener) {
        this.ingredientList = ingredientList;
        this.onIngredientClickListener = listener;
    }

    public interface OnIngredientClickListener {
        void onIngredientClick(String ingredient);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_ingredients, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredient = ingredientList.get(position);

        holder.ingredientTextView.setText(ingredient);

        holder.ingredientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIngredientClickListener != null) {
                    onIngredientClickListener.onIngredientClick(ingredient);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        ImageView ingredientImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.textView_ingredients_name);
            ingredientImageView = itemView.findViewById(R.id.imageView_ingredients);
        }
    }
}
