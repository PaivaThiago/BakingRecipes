package thiagopaiva.bakingrecipes.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.activities.MainActivity;
import thiagopaiva.bakingrecipes.activities.RecipeStepListActivity;
import thiagopaiva.bakingrecipes.objects.Recipe;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private ArrayList<Recipe> recipes;

    class RecipesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_TextView) TextView recipeText;
        @BindView(R.id.recipe_ImageView) ImageView recipeImage;
        RecipesViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public RecipesAdapter(){
        this.recipes = null;
    }

    public RecipesAdapter(ArrayList<Recipe> recipes){
        setRecipes(recipes);
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_view, parent, false);
        return new RecipesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecipesViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        String imageUrl = recipe.getImage();
        if(imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(holder.recipeImage.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_load)
                .into(holder.recipeImage);
        }

        holder.recipeText.setText(recipe.getName());
        holder.recipeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeStepListActivity.class);
                intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                intent.putParcelableArrayListExtra(MainActivity.TAG_RECIPES,recipes);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(recipes != null)
            return recipes.size();
        else
            return 0;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.notifyDataSetChanged();
    }
}
