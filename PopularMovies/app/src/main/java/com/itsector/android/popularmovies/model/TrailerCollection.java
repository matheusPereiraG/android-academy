
package com.itsector.android.popularmovies.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TrailerCollection implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = new ArrayList<Trailer>();
    public final static Creator<TrailerCollection> CREATOR = new Creator<TrailerCollection>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TrailerCollection createFromParcel(android.os.Parcel in) {
            return new TrailerCollection(in);
        }

        public TrailerCollection[] newArray(int size) {
            return (new TrailerCollection[size]);
        }

    }
    ;

    protected TrailerCollection(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.trailers, (Trailer.class.getClassLoader()));
    }

    public TrailerCollection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return trailers;
    }

    public void setResults(List<Trailer> results) {
        this.trailers = results;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(trailers);
    }

    public int describeContents() {
        return  0;
    }

}
