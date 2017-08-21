package thiagopaiva.bakingrecipes.objects;

import java.util.ArrayList;

public class RecipesReport {

    private ArrayList<Recipe> recipes;
    private String message;

    public interface RecipesDelegate {
        void handleRecipesList(RecipesReport report);
    }

    public RecipesReport(){
        recipes = null;
        message = "";
    }

    public ArrayList<Recipe>  getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe>  recipes) {
        this.recipes = recipes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
