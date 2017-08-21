package thiagopaiva.bakingrecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.adapters.RecipeStepsAdapter;
import thiagopaiva.bakingrecipes.objects.Recipe;

public class RecipeStepListActivity extends AppCompatActivity {

    public final static String TAG_RECIPE = "recipe";
    private boolean isTwoPane;
    private Recipe recipe;
    private ArrayList<Recipe> recipes;

    @BindView(R.id.recipe_step_list) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.TAG_RECIPES))
            recipes = intent.getParcelableArrayListExtra(MainActivity.TAG_RECIPES);

        if(savedInstanceState != null){
            recipe = savedInstanceState.getParcelable(TAG_RECIPE);
            setupRecyclerView(recyclerView);
        } else if(intent.hasExtra(TAG_RECIPE)) {
            recipe = intent.getParcelableExtra(TAG_RECIPE);
            setupRecyclerView(recyclerView);
        }

        if (findViewById(R.id.recipe_step_detail_container) != null) {
            isTwoPane = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TAG_RECIPE, recipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipe = savedInstanceState.getParcelable(TAG_RECIPE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecipeStepsAdapter(recipe, recipes, isTwoPane));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putParcelableArrayListExtra(MainActivity.TAG_RECIPES, recipes);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
