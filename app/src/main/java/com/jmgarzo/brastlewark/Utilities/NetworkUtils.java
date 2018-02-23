package com.jmgarzo.brastlewark.Utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jmgarzo.brastlewark.model.Inhabitant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "https://raw.githubusercontent.com/rrafols/mobile_test/master/";

    private static final String DATA_PATH = "data.json";


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static ArrayList<Inhabitant> getInhabitants(Context context) {
        String response = "";
        ArrayList<Inhabitant> inhabitantsList = null;

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(DATA_PATH)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            response = getResponseFromHttpUrl(url);

            inhabitantsList = JsonUtils.getInhabitantsFromJson(context, response);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return inhabitantsList;
    }


}
