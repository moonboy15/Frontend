package com.example.signuploginfirebase;

import android.text.TextUtils;
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
    private List<String> ingredientsImageList;
    private IngredientsAdapter.OnIngredientClickListener onIngredientClickListener;

    public UnhealthyIngredientsAdapter(List<String> ingredientList, List<String> ingredientsImageList,IngredientsAdapter.OnIngredientClickListener listener) {
        this.ingredientList = ingredientList;
        this.ingredientsImageList = ingredientsImageList;
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
        holder.ingredientTextView.setSelected(true);

        if(ingredientsImageList != null && position < ingredientsImageList.size()){
            String ingredientImage = ingredientsImageList.get(position);
            if(TextUtils.isEmpty(ingredientImage)){
                holder.ingredientImageView.setImageResource(R.drawable.no_image);
            }else {
                int resourceId = holder.itemView.getContext().getResources().getIdentifier(ingredientImage, "drawable", holder.itemView.getContext().getPackageName());
                holder.ingredientImageView.setImageResource(resourceId);
            }
        } else{
            holder.ingredientImageView.setImageResource(R.drawable.no_image);
        }

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
