package com.jmgarzo.brastlewark.model.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.jmgarzo.brastlewark.Utilities.JsonUtils;
import com.jmgarzo.brastlewark.Utilities.NetworkUtils;
import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.Profession;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class BrastlewarkSyncTask {

    private static final String LOG_TAG = BrastlewarkSyncTask.class.getSimpleName();



    synchronized public static void syncInhabitants(Context context) {
        try {
            ArrayList<Inhabitant> inhabitantsList = NetworkUtils.getInhabitants(context);



            if (inhabitantsList != null && inhabitantsList.size() > 0) {
                ContentValues[] contentValues = new ContentValues[inhabitantsList.size()];
                for (int i = 0; i < inhabitantsList.size(); i++) {
                    Inhabitant inhabitant = inhabitantsList.get(i);
                    contentValues[i] = inhabitant.getContentValues();
                }

                ContentResolver contentResolver = context.getContentResolver();

//                contentResolver.delete(BrastlewarkContract.ProfessionsEntry.CONTENT_URI,null,null);
//                contentResolver.delete(BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,null,null);
//                contentResolver.delete(BrastlewarkContract.InhabitantsEntry.CONTENT_URI,null,null);



                contentResolver.bulkInsert(BrastlewarkContract.InhabitantsEntry.CONTENT_URI,
                        contentValues);
                //We can't update all trailer and review here because the API have a limitation.
                //syncTrailersAndReviews(context);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG,e.toString());
        }
    }

    synchronized public static void addProfessions(Context context, JSONArray professionsJsonArray) {
        try {

            ArrayList<Profession> professionsList = JsonUtils.getProfessionsFromJson(professionsJsonArray);


            if (professionsList != null && professionsList.size() > 0) {
                ContentValues[] contentValues = new ContentValues[professionsList.size()];
                for (int i = 0; i < professionsList.size(); i++) {
                    Profession profession = professionsList.get(i);
                    contentValues[i] = profession.getContentValues();
                }

                ContentResolver contentResolver = context.getContentResolver();


                contentResolver.bulkInsert(BrastlewarkContract.ProfessionsEntry.CONTENT_URI,
                        contentValues);
                //We can't update all trailer and review here because the API have a limitation.
                //syncTrailersAndReviews(context);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG,e.toString());
        }
    }
}
