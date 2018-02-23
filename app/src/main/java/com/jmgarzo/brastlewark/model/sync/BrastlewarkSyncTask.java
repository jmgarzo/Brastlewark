package com.jmgarzo.brastlewark.model.sync;

import android.content.Context;

import com.jmgarzo.brastlewark.Utilities.NetworkUtils;
import com.jmgarzo.brastlewark.model.Inhabitant;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class BrastlewarkSyncTask {



    synchronized public static void syncInhabitants(Context context) {
        try {
            ArrayList<Inhabitant> inhabitantsList = NetworkUtils.getInhabitants(context);

//            if (moviesList != null && moviesList.size() > 0) {
//                ContentValues[] contentValues = new ContentValues[moviesList.size()];
//                for (int i = 0; i < moviesList.size(); i++) {
//                    Movie movie = moviesList.get(i);
//                    contentValues[i] = movie.getContentValues();
//                }
//
//                ContentResolver contentResolver = context.getContentResolver();
//                contentResolver.delete(
//                        PopularMovieContract.MovieEntry.CONTENT_URI,
//                        PopularMovieContract.MovieEntry.REGISTRY_TYPE + " <> ? ",
//                        new String[]{PopularMovieContract.FAVORITE_REGISTRY_TYPE});
//
//                contentResolver.bulkInsert(PopularMovieContract.MovieEntry.CONTENT_URI,
//                        contentValues);
//                //We can't update all trailer and review here because the API have a limitation.
//                //syncTrailersAndReviews(context);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
