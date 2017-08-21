package thiagopaiva.bakingrecipes.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {

    public final static String KEY_QUANTITY = "quantity";
    public final static String KEY_MEASURE = "measure";
    public final static String KEY_INGREDIENT = "ingredient";

    private String quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredient(){
        this.quantity = "";
        this.measure = "";
        this.ingredient = "";
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    //Parcelable Implementation
    public static final Parcelable.Creator<RecipeIngredient> CREATOR
            = new Parcelable.Creator<RecipeIngredient>() {
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    private RecipeIngredient(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }

    public int describeContents() {
        return 0;
    }
}
