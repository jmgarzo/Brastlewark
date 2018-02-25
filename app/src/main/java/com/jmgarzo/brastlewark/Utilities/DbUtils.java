package com.jmgarzo.brastlewark.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jmgarzo on 24/02/18.
 */

public class DbUtils {

    public static final String[] PROFESSION_COLUMNS = {
            BrastlewarkContract.ProfessionsEntry._ID,
            BrastlewarkContract.ProfessionsEntry.NAME
    };

    public static final int COL_PROFESSION_ID = 0;
    public static final int COL_PROFESSION_NAME = 1;

    private String thumbnail;
    private int age;
    private double weight;
    private double height;
    private String hair_color;
    public static final String[] INHABITANT_COLUMNS = {
            BrastlewarkContract.InhabitantsEntry._ID,
            BrastlewarkContract.InhabitantsEntry.NAME,
            BrastlewarkContract.InhabitantsEntry.THUMBNAIL,
            BrastlewarkContract.InhabitantsEntry.AGE,
            BrastlewarkContract.InhabitantsEntry.WEIGHT,
            BrastlewarkContract.InhabitantsEntry.HEIGHT,
            BrastlewarkContract.InhabitantsEntry.HAIR_COLOR

    };

    public static final int COL_INHABITANT_ID = 0;
    public static final int COL_INHABITANT_NAME = 1;
    public static final int COL_INHABITANT_THUMBNAIL = 2;
    public static final int COL_INHABITANT_AGE = 3;
    public static final int COL_INHABITANT_WEIGHT = 4;
    public static final int COL_INHABITANT_HEIGHT = 5;
    public static final int COL_INHABITANT_HAIR_COLOR = 6;


    public static final String[] INHABITANT_PROFESSION_COLUMNS = {
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry._ID,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.NAME,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.THUMBNAIL,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.AGE,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.WEIGHT,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.HEIGHT,
            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry.HAIR_COLOR,
            " Group_Concat " + "( " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME + "." + BrastlewarkContract.ProfessionsEntry.NAME+ ")"
//            select inhabitants._id,inhabitants.name,inhabitants.thumbnail, Group_Concat(professions.name)
//            from inhabitants INNER JOIN professions  INNER JOIN inhabitant_profession
//            on inhabitants._id = inhabitant_profession.inhabitant_id
//            and inhabitant_profession.profession_id = professions._id
//            where inhabitants._id = 2
//            group by inhabitants._id + "." + BrastlewarkContract.ProfessionsEntry.NAME
    };

    public static final int COL_INHABITANT_PROFESSION_ID = 0;
    public static final int COL_INHABITANT_PROFESSION_INHABITANT_NAME = 1;
    public static final int COL_INHABITANT_PROFESSION_THUMBNAIL = 2;
    public static final int COL_INHABITANT_PROFESSION_AGE = 3;
    public static final int COL_INHABITANT_PROFESSION_WEIGHT = 4;
    public static final int COL_INHABITANT_PROFESSION_HEIGHT = 5;
    public static final int COL_INHABITANT_PROFESSION_HAIR_COLOR = 6;
    public static final int COL_INHABITANT_PROFESSION_PROFESSION_NAMES = 7;




    public static ArrayList<Integer> getIdFriends(Context context, ArrayList<String> friendsNameList) {

        ArrayList<Integer> idFriendsList = new ArrayList<>();
        HashMap<String, Integer> inhabitantsHashMap = new HashMap<>();

        ContentResolver contentResolver = context.getContentResolver();


        Cursor cursor = contentResolver.query(BrastlewarkContract.InhabitantsEntry.CONTENT_URI,
                INHABITANT_COLUMNS,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                inhabitantsHashMap.put(cursor.getString(DbUtils.COL_INHABITANT_NAME), cursor.getInt(COL_INHABITANT_ID));
            } while (cursor.moveToNext());
        }

        for (String friend : friendsNameList) {
            idFriendsList.add(inhabitantsHashMap.get(friend));
        }

        return idFriendsList;

    }

}
