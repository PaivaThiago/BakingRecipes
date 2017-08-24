package thiagopaiva.bakingrecipes.utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thiagopaiva.bakingrecipes.data.RecipeContract;
import thiagopaiva.bakingrecipes.objects.Recipe;
import thiagopaiva.bakingrecipes.objects.RecipeIngredient;
import thiagopaiva.bakingrecipes.objects.RecipeStep;
import thiagopaiva.bakingrecipes.objects.RecipesReport;

public final class RecipeJsonUtils {

    public static RecipesReport getRecipesFromJson(String recipeJsonStr, Context context)
            throws JSONException {

        RecipesReport report = new RecipesReport();
        if (recipeJsonStr == null){
            return report;
        }

        JSONArray recipeArray = new JSONArray(recipeJsonStr);
        ArrayList<Recipe> parsedRecipes = new ArrayList<>();

        for(int i = 0; i < recipeArray.length(); i++) {
            Recipe recipe = new Recipe();
            JSONObject recipeObject = recipeArray.getJSONObject(i);

            recipe.setId(recipeObject.optString(Recipe.KEY_ID));
            recipe.setName(recipeObject.optString(Recipe.KEY_NAME));

            JSONArray ingredientsArray = recipeObject.getJSONArray(Recipe.KEY_INGREDIENTS);
            RecipeIngredient[] ingredients = new RecipeIngredient[ingredientsArray.length()];

            for(int j=0; j < ingredientsArray.length(); j++){
                RecipeIngredient ingredient = new RecipeIngredient();
                JSONObject ingredientObject = ingredientsArray.getJSONObject(j);

                ingredient.setQuantity(ingredientObject.optString(RecipeIngredient.KEY_QUANTITY));
                ingredient.setMeasure(ingredientObject.optString(RecipeIngredient.KEY_MEASURE));
                ingredient.setIngredient(ingredientObject.optString(RecipeIngredient.KEY_INGREDIENT));

                ingredients[j] = ingredient;
                insertIngredient(context, recipe, ingredient);
            }

            recipe.setIngredients(ingredients);

            JSONArray stepsArray = recipeObject.getJSONArray(Recipe.KEY_STEPS);
            RecipeStep[] steps = new RecipeStep[stepsArray.length()];

            for(int j=0; j < stepsArray.length(); j++){
                RecipeStep step = new RecipeStep();
                JSONObject stepObject = stepsArray.getJSONObject(j);

                step.setId(stepObject.optString(RecipeStep.KEY_ID));
                step.setShortDescription(stepObject.optString(RecipeStep.KEY_SHORT_DESCRIPTION));
                step.setDescription(stepObject.optString(RecipeStep.KEY_DESCRIPTION));
                step.setVideoURL(stepObject.optString(RecipeStep.KEY_VIDEO_URL));
                step.setThumbnailURL(stepObject.optString(RecipeStep.KEY_THUMBNAIL_URL));

                steps[j] = step;
            }

            recipe.setSteps(steps);

            recipe.setServings(recipeObject.optString(Recipe.KEY_SERVINGS));
            recipe.setImage(recipeObject.optString(Recipe.KEY_IMAGE));

            parsedRecipes.add(recipe);
            insertRecipe(context,recipe);
        }

        report.setRecipes(parsedRecipes);
        return report;
    }

    private static void insertRecipe(Context context, Recipe recipe){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_ID, recipe.getId());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_NAME, recipe.getName());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_SERVINGS, recipe.getServings());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, recipe.getImage());
        context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);
    }

    private static void insertIngredient(Context context, Recipe recipe, RecipeIngredient ingredient){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_RECIPE_ID, recipe.getId());
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_MEASURE, ingredient.getMeasure());
        contentValues.put(RecipeContract.RecipeIngredientEntry.COLUMN_QUANTITY, ingredient.getQuantity());
        context.getContentResolver().insert(RecipeContract.RecipeIngredientEntry.CONTENT_URI, contentValues);
    }
}