package com.jmgarzo.brastlewark.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    private List<String> listProfession;

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

    public List<String> getListProfession() {
        return listProfession;
    }

    public void setListProfession(List<String> listProfession) {
        this.listProfession = listProfession;
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
        dest.writeStringList(this.listProfession);
    }

    public Inhabitant() {
    }

    protected Inhabitant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.thumbnail = in.readString();
        this.age = in.readInt();
        this.weight = in.readDouble();
        this.height = in.readDouble();
        this.hair_color = in.readString();
        this.listProfession = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Inhabitant> CREATOR = new Parcelable.Creator<Inhabitant>() {
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
