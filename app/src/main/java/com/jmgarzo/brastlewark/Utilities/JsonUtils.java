package com.jmgarzo.brastlewark.Utilities;

import android.content.Context;
import android.util.Log;

import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.Profession;

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


    public static ArrayList<Inhabitant> getInhabitantsFromJson(Context context, String jsonStr) {


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


                JSONArray professionJsonArray = inhabitantJson.getJSONArray(INHABITANT_PROFESSIONS);
                inhabitant.setListProfession(getProfessionsFromJson(professionJsonArray));

                JSONArray friendsJsonArray = inhabitantJson.getJSONArray(INHABITANT_FRIENDS);
                inhabitant.setListFriends(getFriendFromJson(friendsJsonArray));

                inhabitantsList.add(inhabitant);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return inhabitantsList;
    }

    public static ArrayList<Profession> getProfessionsFromJson(JSONArray professionJsonArray) {
        ArrayList<Profession> professionsList = new ArrayList<>();

        try {
            for (int i = 0; i < professionJsonArray.length(); i++) {

                Profession profession = new Profession();
                profession.setName(professionJsonArray.getString(i));

                professionsList.add(profession);


            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }
        return professionsList;
    }

    public static ArrayList<String> getFriendFromJson(JSONArray friendsJsonArray){
        ArrayList<String> friendsNamesList = new ArrayList<>();
        for (int i = 0; i < friendsJsonArray.length(); i++) {
            try {
                friendsNamesList.add(friendsJsonArray.getString(i));
            } catch (JSONException e) {
                Log.e(LOG_TAG,e.toString());
            }
        }
        return friendsNamesList;
    }

    public static ArrayList<Integer> getIdFriendFromJson(Context context,JSONArray friendsJsonArray) {
        ArrayList<String> friendsNamesList = new ArrayList<>();
        ArrayList<Integer> friendIdList = new ArrayList<>();
        try {
            for (int i = 0; i < friendsJsonArray.length(); i++) {
                friendsNamesList.add(friendsJsonArray.getString(i));

            }

            friendIdList = DbUtils.getIdFriends(context,friendsNamesList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }


        return friendIdList;
    }


}
