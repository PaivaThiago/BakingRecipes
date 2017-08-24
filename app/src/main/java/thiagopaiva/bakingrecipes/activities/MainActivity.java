package thiagopaiva.bakingrecipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.adapters.RecipesAdapter;
import thiagopaiva.bakingrecipes.objects.Recipe;
import thiagopaiva.bakingrecipes.objects.RecipesReport;
import thiagopaiva.bakingrecipes.utils.RecipeTaskUtils;

public class MainActivity extends AppCompatActivity implements RecipesReport.RecipesDelegate {

    public final static String TAG_RECIPES = "recipes";
    private RecipesAdapter mRecipeAdapter;
    private ArrayList<Recipe> recipes;

    @BindView(R.id.recipe_RecyclerView)
    RecyclerView mRecipesRecyclerView;
    @BindView(R.id.recipes_error_TextView)
    TextView mErrorTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecipesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getBaseContext(), numberOfColumns());
        mRecipesRecyclerView.setLayoutManager(mLayoutManager);
        mRecipeAdapter = new RecipesAdapter();
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);

        Intent intent = getIntent();

        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(TAG_RECIPES);
            mRecipeAdapter.setRecipes(recipes);
        } else if (intent.hasExtra(TAG_RECIPES)) {
            recipes = intent.getParcelableArrayListExtra(TAG_RECIPES);
            mRecipeAdapter.setRecipes(recipes);
        } else {
            showProgressBar();
            new RecipeTaskUtils(this, this).execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TAG_RECIPES, recipes);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipes = savedInstanceState.getParcelableArrayList(TAG_RECIPES);
    }

    @Override
    public void handleRecipesList(RecipesReport report) {
        hideProgressBar();
        if (report.getRecipes() != null) {
            hideErrorMessage();
            recipes = report.getRecipes();
            mRecipeAdapter.setRecipes(recipes);
        } else if (!report.getMessage().isEmpty()) {
            mErrorTextView.setText(report.getMessage());
            showErrorMessage();
        } else {
            showErrorMessage();
        }
    }

    public void showErrorMessage() {
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int widthDivider = 500;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    private int getCardSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width / numberOfColumns();
    }
}
