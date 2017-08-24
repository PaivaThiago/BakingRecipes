package thiagopaiva.bakingrecipes.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {

    public static final String AUTHORITY = "thiagopaiva.bakingrecipes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECIPES = "recipes";
    public static final String PATH_INGREDIENTS = "ingredients";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
    }

    public static final class RecipeIngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_RECIPE_ID = "recipeId";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
    }
}
