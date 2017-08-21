package thiagopaiva.bakingrecipes.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable{

    public final static String KEY_ID = "id";
    public final static String KEY_SHORT_DESCRIPTION = "shortDescription";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_VIDEO_URL = "videoURL";
    public final static String KEY_THUMBNAIL_URL = "thumbnailURL";

    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStep(){
        this.id = "";
        this.shortDescription = "";
        this.description = "";
        this.videoURL = "";
        this.thumbnailURL = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    //Parcelable Implementation
    public static final Parcelable.Creator<RecipeStep> CREATOR
            = new Parcelable.Creator<RecipeStep>() {
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    private RecipeStep(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(shortDescription);
        out.writeString(description);
        out.writeString(videoURL);
        out.writeString(thumbnailURL);
    }

    public int describeContents() {
        return 0;
    }
}
