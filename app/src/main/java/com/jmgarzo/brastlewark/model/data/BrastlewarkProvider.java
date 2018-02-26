package com.jmgarzo.brastlewark.model.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class BrastlewarkProvider extends ContentProvider {

    private final String LOG_TAG = BrastlewarkProvider.class.getSimpleName();

    static final int INHABITANT = 100;
    static final int FRIENDS_BY_INHABITANT_ID = 101;

    static final int PROFESSION = 200;
    static final int PROFESSION_WITH_INHABITANT_ID = 201;

    static final int INHABITANT_PROFESSION = 300;

    static final int INHABITANT_FRIEND = 400;
    static final int INHABITANT_FRIEND_WITH_INHABITANT_ID = 401;


    private BrastlewarkDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public Cursor getInhabitantProfession(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return sProfessionsByInhabitantBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry._ID,
                null,
                sortOrder
        );
    }

    private static final SQLiteQueryBuilder sProfessionsByInhabitantBuilder;

    static {
        sProfessionsByInhabitantBuilder = new SQLiteQueryBuilder();
        sProfessionsByInhabitantBuilder.setTables(
                BrastlewarkContract.InhabitantsEntry.TABLE_NAME +
                        " INNER JOIN " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME +
                        " INNER JOIN " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME +
                        " ON " +
                        BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantsEntry._ID +
                        " = " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID +
                        " AND " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID +
                        " = " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME + "." + BrastlewarkContract.ProfessionsEntry._ID);
    }

    private static final SQLiteQueryBuilder sProfessionsByInhabitantIdBuilder;

    static {
        sProfessionsByInhabitantIdBuilder = new SQLiteQueryBuilder();
        sProfessionsByInhabitantIdBuilder.setTables(
                BrastlewarkContract.ProfessionsEntry.TABLE_NAME +
                        " INNER JOIN " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME +
                        " ON " +
                        BrastlewarkContract.ProfessionsEntry.TABLE_NAME + "." + BrastlewarkContract.ProfessionsEntry._ID +
                        " = " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME + "." + BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID);
    }

//    private static final SQLiteQueryBuilder sFriendsByInhabitantIdBuilder;
//
//    static {
//        sFriendsByInhabitantIdBuilder = new SQLiteQueryBuilder();
//        sFriendsByInhabitantIdBuilder.setTables(
//                BrastlewarkContract.InhabitantsEntry.TABLE_NAME  +BrastlewarkContract.InhabitantsEntry.INHABITANT_TABLE_ALIAS +
//                        " INNER JOIN " + BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME + BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS +
//                        " INNER JOIN " + BrastlewarkContract.InhabitantsEntry.TABLE_NAME + BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS +
//                        " ON " +
//                        BrastlewarkContract.InhabitantsEntry.INHABITANT_TABLE_ALIAS + "." + BrastlewarkContract.InhabitantsEntry._ID +
//                        " = " + BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS + "." + BrastlewarkContract.InhabitantFriendEntry.INHABITANT_ID +
//                        " AND " + BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS + "." + BrastlewarkContract.InhabitantFriendEntry.FRIEND_ID +
//                        " = " + BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS +"." + BrastlewarkContract.InhabitantsEntry._ID
//        );
//    }


//    select  FRI.*,group_concat(PRO.NAME)
//
//    from inhabitants INA
//    inner join inhabitant_friend INAFRI
//    on INA._id = INAFRI.inhabitant_id
//    inner join inhabitants FRI
//    on INAFRI.friend_id = FRI._id
//    left join inhabitant_profession INAPRO
//    on FRI._id = (INAPRO.Inhabitant_ID)
//    left join professions PRO
//    on INAPRO.PROFESSION_ID = PRO._ID
//
//    where INA._id = 0
//    group by FRI.NAME
    private static final SQLiteQueryBuilder sFriendsByInhabitantIdBuilder;

    static {
        sFriendsByInhabitantIdBuilder = new SQLiteQueryBuilder();
        sFriendsByInhabitantIdBuilder.setTables(
                BrastlewarkContract.InhabitantsEntry.TABLE_NAME  + BrastlewarkContract.InhabitantsEntry.INHABITANT_TABLE_ALIAS +
                        " INNER JOIN " + BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME + BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS +
                         " ON " + BrastlewarkContract.InhabitantsEntry.INHABITANT_TABLE_ALIAS + "." + BrastlewarkContract.InhabitantsEntry._ID
                        +" = "+ BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS +"."+ BrastlewarkContract.InhabitantFriendEntry.INHABITANT_ID +
                        " INNER JOIN " + BrastlewarkContract.InhabitantsEntry.TABLE_NAME + BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS +
                        " ON " + BrastlewarkContract.InhabitantFriendEntry.TABLE_ALIAS + "." + BrastlewarkContract.InhabitantFriendEntry.FRIEND_ID +
                        " = " + BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS + "." + BrastlewarkContract.InhabitantsEntry._ID +
                        " LEFT JOIN " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME + BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_PROFESSION_TABLE_ALIAS +
                        " ON " + BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS+"."+ BrastlewarkContract.InhabitantsEntry._ID +
                        " = " + BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_PROFESSION_TABLE_ALIAS+"."+ BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID +
                        " LEFT JOIN " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME + BrastlewarkContract.ProfessionsEntry.PROFESSION_TABLE_ALIAS +
                        " ON " + BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_PROFESSION_TABLE_ALIAS+"."+ BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID +
                        " = " + BrastlewarkContract.ProfessionsEntry.PROFESSION_TABLE_ALIAS + "."+ BrastlewarkContract.ProfessionsEntry._ID);


    }



    private Cursor getFriendsByInhabitantId(
            Uri uri, String[] projection, String sortOrder) {
        String selection = BrastlewarkContract.InhabitantsEntry.INHABITANT_TABLE_ALIAS + "." + BrastlewarkContract.InhabitantsEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{BrastlewarkContract.InhabitantsEntry.getInhabitantIdFromUri(uri)};
        return sFriendsByInhabitantIdBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                BrastlewarkContract.InhabitantsEntry.FRIEND_TABLE_ALIAS+ "."+ BrastlewarkContract.InhabitantsEntry.NAME,
                null,
                sortOrder
        );
    }

    private Cursor getProfessionByInhabitantId(
            Uri uri, String[] projection, String sortOrder) {
        String selection = BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID + " = ? ";
        String[] selectionArgs = new String[]{BrastlewarkContract.InhabitantProfessionEntry.getInhabitantIdFromUri(uri)};
        return sProfessionsByInhabitantIdBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_INHABITANTS, INHABITANT);
        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_INHABITANTS + "/*", FRIENDS_BY_INHABITANT_ID);

        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_PROFESSIONS, PROFESSION);
        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_PROFESSIONS + "/*", PROFESSION_WITH_INHABITANT_ID);

        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_INHABITANT_PROFESSION, INHABITANT_PROFESSION);

        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_INHABITANT_FRIEND, INHABITANT_FRIEND);
        matcher.addURI(BrastlewarkContract.CONTENT_AUTHORITY, BrastlewarkContract.PATH_INHABITANT_FRIEND + "/*", INHABITANT_FRIEND_WITH_INHABITANT_ID);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BrastlewarkDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor;

        switch (sUriMatcher.match(uri)) {
            case INHABITANT: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        BrastlewarkContract.InhabitantsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FRIENDS_BY_INHABITANT_ID: {

                returnCursor = getFriendsByInhabitantId(uri, projection, sortOrder);
                break;
            }

            case PROFESSION: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        BrastlewarkContract.ProfessionsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case PROFESSION_WITH_INHABITANT_ID: {
                returnCursor = getProfessionByInhabitantId(uri, projection, sortOrder);
                break;
            }

            case INHABITANT_PROFESSION: {

                returnCursor = getInhabitantProfession(uri, projection, selection, selectionArgs, sortOrder);


                break;
            }

            case INHABITANT_FRIEND: {
                returnCursor = mOpenHelper.getReadableDatabase().query(
                        BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case INHABITANT:
                return BrastlewarkContract.InhabitantsEntry.CONTENT_DIR_TYPE;
            case FRIENDS_BY_INHABITANT_ID:
                return BrastlewarkContract.InhabitantsEntry.CONTENT_ITEM_TYPE;
            case PROFESSION:
                return BrastlewarkContract.ProfessionsEntry.CONTENT_DIR_TYPE;
            case INHABITANT_PROFESSION:
                return BrastlewarkContract.InhabitantProfessionEntry.CONTENT_DIR_TYPE;
            case INHABITANT_FRIEND:
                return BrastlewarkContract.InhabitantProfessionEntry.CONTENT_DIR_TYPE;
        }
        return null;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri = null;

        switch (sUriMatcher.match(uri)) {
            case INHABITANT: {
                long id = db.insert(BrastlewarkContract.InhabitantsEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(BrastlewarkContract.InhabitantsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case PROFESSION: {
                long id = db.insert(BrastlewarkContract.ProfessionsEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(BrastlewarkContract.ProfessionsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case INHABITANT_PROFESSION: {
                try {
                    long id = db.insertOrThrow(BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME,
                            null,
                            values);

                    if (id > 0) {
                        returnUri = ContentUris.withAppendedId(BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into: " + uri);
                    }
                } catch (SQLiteException e) {
                    Log.e(LOG_TAG, e.toString());
                }
                break;
            }

            case INHABITANT_FRIEND: {
                try {
                    long id = db.insertOrThrow(BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME,
                            null,
                            values);

                    if (id > 0) {
                        returnUri = ContentUris.withAppendedId(BrastlewarkContract.InhabitantFriendEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into: " + uri);
                    }
                } catch (SQLiteException e) {
                    Log.e(LOG_TAG, e.toString());
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numDeleted = 0;

        switch (sUriMatcher.match(uri)) {
            case INHABITANT: {
                numDeleted = db.delete(
                        BrastlewarkContract.InhabitantsEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case PROFESSION: {
                numDeleted = db.delete(
                        BrastlewarkContract.ProfessionsEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }

            case INHABITANT_PROFESSION: {
                numDeleted = db.delete(
                        BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }

            case INHABITANT_FRIEND: {
                numDeleted = db.delete(
                        BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return numDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        int numUpdates = 0;

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case INHABITANT: {
                numUpdates = db.update(BrastlewarkContract.InhabitantsEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case PROFESSION: {
                numUpdates = db.update(BrastlewarkContract.ProfessionsEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            case INHABITANT_PROFESSION: {
                numUpdates = db.update(BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case INHABITANT_FRIEND: {
                numUpdates = db.update(BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numUpdates;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numInserted = 0;

        switch (sUriMatcher.match(uri)) {
            case INHABITANT: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = db.insert(BrastlewarkContract.InhabitantsEntry.TABLE_NAME, null, value);
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
                break;
            }

            case PROFESSION: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }

                        long id = 0;
                        id = db.insertOrThrow(BrastlewarkContract.ProfessionsEntry.TABLE_NAME, null, value);

                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteConstraintException e) {
                    //Just for debug
                    Log.d(LOG_TAG, e.toString());
                } catch (SQLiteException e) {
                    Log.e(LOG_TAG, e.toString());
                } finally {
                    db.endTransaction();
                }
                break;
            }

            case INHABITANT_PROFESSION: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = -1;
                        try {
                            id = db.insertOrThrow(BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME, null, value);
                        } catch (SQLiteException e) {
                            Log.d(LOG_TAG, e.toString());
                        }
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
                break;
            }

            case INHABITANT_FRIEND: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long id = -1;
                        try {
                            id = db.insertOrThrow(BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME, null, value);
                        } catch (SQLiteException e) {
                            Log.d(LOG_TAG, e.toString());
                        }
                        if (id != -1) {
                            numInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
                break;
            }
            default:
                return super.bulkInsert(uri, values);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numInserted;
    }
}
