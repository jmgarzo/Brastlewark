package com.jmgarzo.brastlewark.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

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
}
