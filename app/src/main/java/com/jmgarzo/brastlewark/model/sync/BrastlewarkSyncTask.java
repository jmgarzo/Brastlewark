package com.jmgarzo.brastlewark.model.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.jmgarzo.brastlewark.Utilities.DbUtils;
import com.jmgarzo.brastlewark.Utilities.JsonUtils;
import com.jmgarzo.brastlewark.Utilities.NetworkUtils;
import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.Profession;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class BrastlewarkSyncTask {

    private static final String LOG_TAG = BrastlewarkSyncTask.class.getSimpleName();


    synchronized public static void syncInhabitants(Context context) {
        try {
            ArrayList<Inhabitant> inhabitantsList = NetworkUtils.getInhabitants(context);
            ArrayList<Profession> professionsList = new ArrayList<>();


            if (inhabitantsList != null && inhabitantsList.size() > 0) {
                ContentValues[] inhabitantsCV = new ContentValues[inhabitantsList.size()];
                //Get Inhabitant contentValues to load in Database
                for (int i = 0; i < inhabitantsList.size(); i++) {
                    Inhabitant inhabitant = inhabitantsList.get(i);
                    inhabitantsCV[i] = inhabitant.getContentValues();
                    //Get profession data to load in Database
                    for (int j = 0; j < inhabitant.getListProfession().size(); j++) {
                        if (!professionsList.contains(inhabitant.getListProfession().get(j))) {
                            professionsList.add(inhabitant.getListProfession().get(j));
                        }
                    }
                }

                int inhabitantAdded = addInhabitants(context, inhabitantsCV);
                Log.v(LOG_TAG, inhabitantAdded + " inhabitants added ");

                int professionAdded = addProfessions(context, professionsList);
                Log.v(LOG_TAG, professionAdded + " professions added ");

                int inhabitantProfessionAdded = addInhabitantProfession(context, inhabitantsList);
                Log.v(LOG_TAG, inhabitantProfessionAdded + " Inhabitant-ProfessionAdded added ");



            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    synchronized public static void deleteDb(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        int inhabitantProfessionDeleted = contentResolver.delete(BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,
                null,
                null);
        Log.d(LOG_TAG, "Inhabitant-Professions Deleted: " + inhabitantProfessionDeleted);

        int professionDeleted = contentResolver.delete(BrastlewarkContract.ProfessionsEntry.CONTENT_URI,
                null,
                null);
        Log.d(LOG_TAG, "Professions Deleted: " + professionDeleted);

        int inhabitantDeleted = contentResolver.delete(BrastlewarkContract.InhabitantsEntry.CONTENT_URI,
                null,
                null);
        Log.d(LOG_TAG, "Inhabitant Deleted: " + inhabitantDeleted);


    }

    private static int addInhabitants(Context context, ContentValues[] inhabitantsCV) {
        int result = 0;

        ContentResolver contentResolver = context.getContentResolver();

        result = contentResolver.bulkInsert(BrastlewarkContract.InhabitantsEntry.CONTENT_URI,
                inhabitantsCV);
        return result;
    }

    private static int addProfessions(Context context, ArrayList<Profession> professionsList) {
        int result = 0;
        ContentValues[] professionCV = new ContentValues[professionsList.size()];

        for (int k = 0; k < professionsList.size(); k++) {
            professionCV[k] = professionsList.get(k).getContentValues();
        }

        ContentResolver contentResolver = context.getContentResolver();
        result = contentResolver.bulkInsert(BrastlewarkContract.ProfessionsEntry.CONTENT_URI, professionCV);
        return result;
    }

    private static int addInhabitantProfession(Context context, ArrayList<Inhabitant> inhabitantsList) {
        int result = 0;

        ArrayList<ContentValues> totalContentValuesList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Profession> professionList = new ArrayList<>();

        HashMap<String, Integer> professionsHashMap = new HashMap<>();


        //Select all Professions in Database
        Cursor cursor = contentResolver.query(BrastlewarkContract.ProfessionsEntry.CONTENT_URI,
                DbUtils.PROFESSION_COLUMNS,
                null,
                null,
                null);

        //Load all Professions in a hashmap
        if (cursor != null && cursor.moveToFirst()) {
            do {
                professionsHashMap.put(cursor.getString(DbUtils.COL_PROFESSION_NAME), cursor.getInt(DbUtils.COL_PROFESSION_ID));
            } while (cursor.moveToNext());

        }


        //Get all relations between inhabitant and profession
        for (Inhabitant inhabitant : inhabitantsList) {
            int inhabitantId = inhabitant.getId();

            for (Profession pro : inhabitant.getListProfession()) {
                int professionId = professionsHashMap.get(pro.getName());

                ContentValues cv = new ContentValues();
                cv.put(BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID, inhabitantId);
                cv.put(BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID, professionId);
                totalContentValuesList.add(cv);

            }

        }
        result = contentResolver.bulkInsert(BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,
                totalContentValuesList.toArray(new ContentValues[totalContentValuesList.size()]));

        return result;
    }

}
