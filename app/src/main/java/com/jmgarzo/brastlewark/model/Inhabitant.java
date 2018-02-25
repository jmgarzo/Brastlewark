package com.jmgarzo.brastlewark.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.jmgarzo.brastlewark.Utilities.DbUtils;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import java.util.List;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class Inhabitant implements Parcelable {

    private int id;
    private String name;
    private String thumbnail;
    private int age;
    private double weight;
    private double height;
    private String hair_color;
    private List<Profession> listProfession;
    private List<String> listFriends;

    private final static String LOG_TAG = Inhabitant.class.getSimpleName();

    public Inhabitant(){}

    /**
     * Construct a new Inahbitant from a cursor's first row.
     *
     * @param cursor
     */
    public Inhabitant(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 1) {
                Log.d(LOG_TAG, "Cursor have more than one rows");
            }
            cursorToInhabitant(cursor);
        }
    }

    /**
     * Inhabitant's constructor from a cursor and a position
     * @param cursor
     * @param position
     */
    public Inhabitant(Cursor cursor, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            cursorToInhabitant(cursor);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public List<Profession> getListProfession() {
        return listProfession;
    }

    public void setListProfession(List<Profession> listProfession) {
        this.listProfession = listProfession;
    }

    public List<String> getListFriends() {
        return listFriends;
    }

    public void setListFriends(List<String> listFriends) {
        this.listFriends = listFriends;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BrastlewarkContract.InhabitantsEntry._ID,getId());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.NAME,getName());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.THUMBNAIL,getThumbnail());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.AGE,getAge());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.WEIGHT,getWeight());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.HEIGHT,getHeight());
        contentValues.put(BrastlewarkContract.InhabitantsEntry.HAIR_COLOR,getHair_color());

        return contentValues;
    }


    private void cursorToInhabitant(Cursor cursor) {
        id = cursor.getInt(DbUtils.COL_INHABITANT_ID);
        name = cursor.getString(DbUtils.COL_INHABITANT_NAME);
        thumbnail = cursor.getString(DbUtils.COL_INHABITANT_THUMBNAIL);
        age = cursor.getInt(DbUtils.COL_INHABITANT_AGE);
        weight = cursor.getDouble(DbUtils.COL_INHABITANT_WEIGHT);
        height = cursor.getDouble(DbUtils.COL_INHABITANT_HEIGHT);
        hair_color = cursor.getString(DbUtils.COL_INHABITANT_HAIR_COLOR);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumbnail);
        dest.writeInt(this.age);
        dest.writeDouble(this.weight);
        dest.writeDouble(this.height);
        dest.writeString(this.hair_color);
        dest.writeTypedList(this.listProfession);
    }



    protected Inhabitant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.thumbnail = in.readString();
        this.age = in.readInt();
        this.weight = in.readDouble();
        this.height = in.readDouble();
        this.hair_color = in.readString();
        this.listProfession = in.createTypedArrayList(Profession.CREATOR);
    }

    public static final Creator<Inhabitant> CREATOR = new Creator<Inhabitant>() {
        @Override
        public Inhabitant createFromParcel(Parcel source) {
            return new Inhabitant(source);
        }

        @Override
        public Inhabitant[] newArray(int size) {
            return new Inhabitant[size];
        }
    };
}
