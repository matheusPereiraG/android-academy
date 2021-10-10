package com.itsector.android.popularmovies.utils;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.model.Review;
import com.itsector.android.popularmovies.model.ReviewCollection;

import java.util.List;

public class RepoUtils {
    public static int ITEMS_PER_PAGE = 20;
    public static int getNextPage(MovieCollection collection, String collectionType){
        int nextPage = 1;
        if (collection != null) {
            if (collection.getCollectionType().equals(collectionType))
                nextPage = collection.getResults().size() / ITEMS_PER_PAGE + 1;
            if(nextPage > collection.getTotalPages())
                nextPage = -1;
        }
        return nextPage;
    }

    public static ReviewCollection mergeReviews(ReviewCollection oldR, ReviewCollection newR) {
        if (oldR == null) return newR;

        List<Review> tempList = oldR.getResults();
        tempList.addAll(newR.getResults());
        oldR.setResults(tempList);

        oldR.setPage(newR.getPage());
        oldR.setTotalPages(newR.getTotalPages());
        oldR.setTotalResults(newR.getTotalResults());

        return oldR;
    }

    public static MovieCollection mergeMovieCollection(MovieCollection oldC, MovieCollection newC) {
        if (oldC == null) return newC;

        if (oldC.getCollectionType().equals(newC.getCollectionType())) {
            oldC.setNewMoviesStartIndex(oldC.getResults().size());
            List<Movie> newMovieList = oldC.getResults();
            newMovieList.addAll(newC.getResults());
            oldC.setResults(newMovieList);

        } else {
            oldC.setNewMoviesStartIndex(0);
            oldC.setResults(newC.getResults());
            oldC.setCollectionType(newC.getCollectionType());
        }

        oldC.setPage(newC.getPage());
        oldC.setTotalPages(newC.getTotalPages());
        oldC.setTotalResults(newC.getTotalResults());

        return oldC;
    }
}
