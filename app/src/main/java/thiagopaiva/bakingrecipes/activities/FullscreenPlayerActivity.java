package thiagopaiva.bakingrecipes.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.objects.Recipe;
import thiagopaiva.bakingrecipes.objects.RecipeStep;
import thiagopaiva.bakingrecipes.utils.ExoPlayerVideoHandler;

public class FullscreenPlayerActivity extends AppCompatActivity {

    private RecipeStep step;
    private Recipe recipe;
    private ArrayList<Recipe> recipes;
    @BindView(R.id.videoPlayerFullscreen) SimpleExoPlayerView exoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_player);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
            recipes = savedInstanceState.getParcelableArrayList(MainActivity.TAG_RECIPES);
            recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
        } else {
            Intent intent = getIntent();
            if (intent.hasExtra(RecipeStepListActivity.TAG_RECIPE))
                recipe = intent.getParcelableExtra(RecipeStepListActivity.TAG_RECIPE);
            if (intent.hasExtra(MainActivity.TAG_RECIPES))
                recipes = intent.getParcelableArrayListExtra(MainActivity.TAG_RECIPES);
            if (intent.hasExtra(RecipeStepDetailFragment.ARG_STEP)) {
                step = intent.getParcelableExtra(RecipeStepDetailFragment.ARG_STEP);
                setTitle(step.getShortDescription());
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ){
            destroyVideo = false;
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            upIntent.putExtra(RecipeStepDetailFragment.ARG_STEP, step);
            upIntent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
            upIntent.putParcelableArrayListExtra(MainActivity.TAG_RECIPES, recipes);
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeStepListActivity.TAG_RECIPE, recipe);
        outState.putParcelableArrayList(MainActivity.TAG_RECIPES, recipes);
        outState.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        recipes = savedInstanceState.getParcelableArrayList(MainActivity.TAG_RECIPES);
        recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
    }

    private boolean destroyVideo = false;
    @Override
    protected void onResume(){
        super.onResume();
        if(ExoPlayerVideoHandler.getInstance().getPlayer() != null)
            exoPlayerView.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());
        else
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(getApplicationContext(), Uri.parse(step.getVideoURL()), exoPlayerView);
        ExoPlayerVideoHandler.getInstance().goToForeground();
    }

    @Override
    public void onBackPressed(){
        destroyVideo = true;
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }
}
