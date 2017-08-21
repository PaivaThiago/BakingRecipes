package thiagopaiva.bakingrecipes.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    public final static String KEY_ID = "id";
    public final static String KEY_NAME = "name";
    public final static String KEY_INGREDIENTS = "ingredients";
    public final static String KEY_STEPS = "steps";
    public final static String KEY_SERVINGS = "servings";
    public final static String KEY_IMAGE = "image";

    private String id;
    private String name;
    private RecipeIngredient[] ingredients;
    private RecipeStep[] steps;
    private String servings;
    private String image;

    public Recipe(){
        this.id = "";
        this.name = "";
        this.servings = "";
        this.image = "";
        this.ingredients = new RecipeIngredient[0];
        this.steps = new RecipeStep[0];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeIngredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(RecipeIngredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeStep[] getSteps() {
        return steps;
    }

    public void setSteps(RecipeStep[] steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Parcelable Implementation
    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    private Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        ingredients = in.createTypedArray(RecipeIngredient.CREATOR);
        steps = in.createTypedArray(RecipeStep.CREATOR);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(servings);
        out.writeString(image);
        out.writeTypedArray(ingredients, 0);
        out.writeTypedArray(steps, 0);
    }

    public int describeContents() {
        return 0;
    }
}
