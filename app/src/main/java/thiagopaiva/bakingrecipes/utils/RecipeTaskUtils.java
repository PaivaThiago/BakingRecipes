package thiagopaiva.bakingrecipes.utils;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import thiagopaiva.bakingrecipes.objects.RecipesReport;

public class RecipeTaskUtils extends AsyncTask<Void, Void, RecipesReport>{

    private final RecipesReport.RecipesDelegate delegate;

    public RecipeTaskUtils(RecipesReport.RecipesDelegate recipesDelegate){
        this.delegate = recipesDelegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected RecipesReport doInBackground(Void... params) {
        RecipesReport report = new RecipesReport();
        try {
            String json = NetworkUtils.getResponseFromHttpUrl();
            report = RecipeJsonUtils.getRecipesFromJson(json);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    protected void onPostExecute(RecipesReport report) {
        delegate.handleRecipesList(report);
    }
}
