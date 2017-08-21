package thiagopaiva.bakingrecipes.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thiagopaiva.bakingrecipes.objects.Recipe;
import thiagopaiva.bakingrecipes.objects.RecipeIngredient;
import thiagopaiva.bakingrecipes.objects.RecipeStep;
import thiagopaiva.bakingrecipes.objects.RecipesReport;

public final class RecipeJsonUtils {

    public static RecipesReport getRecipesFromJson(String recipeJsonStr)
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
        }

        report.setRecipes(parsedRecipes);
        return report;
    }

}