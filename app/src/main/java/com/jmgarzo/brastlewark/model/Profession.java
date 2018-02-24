package com.jmgarzo.brastlewark.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.jmgarzo.brastlewark.Utilities.DbUtils;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class Profession implements Parcelable {

    private int id;
    private String name;

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


    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BrastlewarkContract.ProfessionsEntry.NAME, getName());
        return contentValues;
    }

    public void cursorToProfession(Cursor cursor) {

        id = cursor.getInt(DbUtils.COL_PROFESSION_ID);
        name = cursor.getString(DbUtils.COL_PROFESSION_NAME);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public Profession() {
    }

    protected Profession(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Profession> CREATOR = new Parcelable.Creator<Profession>() {
        @Override
        public Profession createFromParcel(Parcel source) {
            return new Profession(source);
        }

        @Override
        public Profession[] newArray(int size) {
            return new Profession[size];
        }
    };

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj == null || obj.getClass() != getClass()) {
            result = false;
        } else {
            Profession profession = (Profession) obj;
            if (this.name.equals(profession.getName())) {
                result = true;
            }
        }
        return result;
    }
}
