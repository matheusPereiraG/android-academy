
package com.itsector.android.popularmovies.model;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorDetails implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("avatar_path")
    @Expose
    private String avatarPath;
    @SerializedName("rating")
    @Expose
    private Double rating;
    public final static Creator<AuthorDetails> CREATOR = new Creator<AuthorDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AuthorDetails createFromParcel(android.os.Parcel in) {
            return new AuthorDetails(in);
        }

        public AuthorDetails[] newArray(int size) {
            return (new AuthorDetails[size]);
        }

    }
    ;

    protected AuthorDetails(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.avatarPath = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public AuthorDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(username);
        dest.writeValue(avatarPath);
        dest.writeValue(rating);
    }

    public int describeContents() {
        return  0;
    }

}
