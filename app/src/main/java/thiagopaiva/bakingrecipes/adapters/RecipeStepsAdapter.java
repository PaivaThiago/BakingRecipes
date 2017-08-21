package thiagopaiva.bakingrecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.activities.MainActivity;
import thiagopaiva.bakingrecipes.activities.RecipeStepDetailActivity;
import thiagopaiva.bakingrecipes.activities.RecipeStepDetailFragment;
import thiagopaiva.bakingrecipes.activities.RecipeStepListActivity;
import thiagopaiva.bakingrecipes.objects.Recipe;
import thiagopaiva.bakingrecipes.objects.RecipeStep;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private Recipe recipe;
    private ArrayList<Recipe> recipes;
    private boolean isTwoPane;

    public RecipeStepsAdapter(Recipe recipe, ArrayList<Recipe> recipes, boolean isTwoPane) {
        this.recipe = recipe;
        this.recipes = recipes;
        this.isTwoPane = isTwoPane;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RecipeStep step;
        @BindView(R.id.content)
        TextView mContentView;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.step = recipe.getSteps()[position];
        holder.mContentView.setText(holder.step.getShortDescription());
        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeStepDetailFragment.ARG_STEP, holder.step);
                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_step_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                    intent.putExtra(RecipeStepDetailFragment.ARG_STEP, holder.step);
                    intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                    intent.putParcelableArrayListExtra(MainActivity.TAG_RECIPES,recipes);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(recipe.getSteps() != null)
            return recipe.getSteps().length;
        else return 0;
    }
}
