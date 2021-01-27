package jb.prodution.recipesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable{

    private int id;
    private String title;
    private String image;
    private String sourceName;
    private int servings;
    private float pricePerServing;
    @SerializedName("readyInMinutes")
    @Expose()
    private int prepareTime;

    public Recipe(){}

    public Recipe(int id, String title, String image, String sourceName, int servings, float pricePerServing, int prepareTime) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.sourceName = sourceName;
        this.servings = servings;
        this.pricePerServing = pricePerServing;
        this.prepareTime = prepareTime;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        sourceName = in.readString();
        servings = in.readInt();
        pricePerServing = in.readFloat();
        prepareTime = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public float getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(float pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(sourceName);
        dest.writeInt(servings);
        dest.writeFloat(pricePerServing);
        dest.writeInt(prepareTime);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", servings=" + servings +
                ", pricePerServing=" + pricePerServing +
                ", prepareTime=" + prepareTime +
                '}';
    }
}
