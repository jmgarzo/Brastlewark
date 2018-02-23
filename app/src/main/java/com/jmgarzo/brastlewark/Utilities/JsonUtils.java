package com.jmgarzo.brastlewark.Utilities;

import android.content.Context;
import android.util.Log;

import com.jmgarzo.brastlewark.model.Inhabitant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static final String BRASTLEWARK = "Brastlewark";

    //INHABITANT FIELDS
    public static final String INHABITANT_ID = "id";
    public static final String INHABITANT_NAME = "name";
    public static final String INHABITANT_THUMBNAIL = "thumbnail";
    public static final String INHABITANT_AGE = "age";
    public static final String INHABITANT_WEIGHT = "weight";
    public static final String INHABITANT_HEIGHT = "height";
    public static final String INHABITANT_HAIR_COLOR = "hair_color";
    public static final String INHABITANT_PROFESSIONS = "professions";
    public static final String INHABITANT_FRIENDS = "friends";





    public static ArrayList<Inhabitant> getInhabitantsFromJson(Context context , String jsonStr) {


        ArrayList<Inhabitant> inhabitantsList = null;

        JSONObject rootJson = null;
        try {
            rootJson = new JSONObject(jsonStr);

            JSONArray brastlewarkArray = rootJson.getJSONArray(BRASTLEWARK);

            inhabitantsList = new ArrayList<>();
            for (int i = 0; i < brastlewarkArray.length(); i++) {
                JSONObject inhabitantJson = brastlewarkArray.getJSONObject(i);

                Inhabitant inhabitant = new Inhabitant();

                inhabitant.setId(inhabitantJson.getInt(INHABITANT_ID));
                inhabitant.setName(inhabitantJson.getString(INHABITANT_NAME));
                inhabitant.setThumbnail(inhabitantJson.getString(INHABITANT_THUMBNAIL));
                inhabitant.setAge(inhabitantJson.getInt(INHABITANT_AGE));
                inhabitant.setWeight(inhabitantJson.getDouble(INHABITANT_WEIGHT));
                inhabitant.setHeight(inhabitantJson.getDouble(INHABITANT_HEIGHT));
                inhabitant.setHair_color(inhabitantJson.getString(INHABITANT_HAIR_COLOR));
                //TODO: inhabitant.setProfessions()
                //TODO: inhabitant.setFriends

                inhabitantsList.add(inhabitant);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return inhabitantsList;
    }
}
