
package com.itsector.android.popularmovies.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieCollection implements Parcelable
{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("total_results")
    @Expose
    private int totalResults;

    private String collectionType;
    private int newMoviesStartIndex;

    public final static Creator<MovieCollection> CREATOR = new Creator<MovieCollection>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MovieCollection createFromParcel(android.os.Parcel in) {
            return new MovieCollection(in);
        }

        public MovieCollection[] newArray(int size) {
            return (new MovieCollection[size]);
        }

    }
    ;

    protected MovieCollection(android.os.Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.movies, (Movie.class.getClassLoader()));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
    }

    public MovieCollection() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return movies;
    }

    public void setResults(List<Movie> movies) {
        this.movies = movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(movies);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public void setCollectionType(String collectionType){
        this.collectionType = collectionType;
    }

    public String getCollectionType(){
        return this.collectionType;
    }

    public int getNewMoviesStartIndex(){
        return this.newMoviesStartIndex;
    }

    public void setNewMoviesStartIndex(int index) {this.newMoviesStartIndex = index; }

    public int getItemsSize(){
        return this.movies.size();
    }

    public int describeContents() {
        return  0;
    }
}
