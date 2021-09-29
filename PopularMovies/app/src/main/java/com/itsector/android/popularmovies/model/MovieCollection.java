
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
    private Long page;
    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();
    @SerializedName("total_pages")
    @Expose
    private Long totalPages;
    @SerializedName("total_results")
    @Expose
    private Long totalResults;
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
        this.page = ((Long) in.readValue((Long.class.getClassLoader())));
        in.readList(this.movies, (Movie.class.getClassLoader()));
        this.totalPages = ((Long) in.readValue((Long.class.getClassLoader())));
        this.totalResults = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public MovieCollection() {
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return movies;
    }

    public void setResults(List<Movie> movies) {
        this.movies = movies;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(movies);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return  0;
    }

}
