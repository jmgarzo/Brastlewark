package com.jmgarzo.brastlewark.model.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jmgarzo on 2/22/2018.
 */

public class BrastlewarkContract {

    public static final String CONTENT_AUTHORITY = "com.jmgarzo.brastlewark";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INHABITANTS = "inhabitants";
    public static final String PATH_PROFESSIONS = "professions";
    public static final String PATH_INHABITANT_PROFESSION = "inhabitant_profession";
    public static final String PATH_INHABITANT_FRIEND = "inhabitant_friend";


    public static final class InhabitantsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INHABITANTS).build();

        public static final String TABLE_NAME = "inhabitants";
        public static final String INAHABITANT_TABLE_ALIAS = " INA";
        public static final String FRIEND_TABLE_ALIAS = " FRI";
        public static final String _ID = "_id";

        public static final String NAME = "name";
        public static final String THUMBNAIL = "thumbnail";
        public static final String AGE = "age";
        public static final String WEIGHT = "weight";
        public static final String HEIGHT = "height";
        public static final String HAIR_COLOR = "hair_color";


        public static Uri buildFriendsAndInhabitants(String inhabitantId) {
            return CONTENT_URI.buildUpon().appendPath(inhabitantId).build();
        }


        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }


    public static final class ProfessionsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFESSIONS).build();

        public static final String TABLE_NAME = "professions";
        public static final String _ID = "_id";

        public static final String NAME = "name";

        public static Uri buildInhabitantAndProfessions(String inhabitantId) {
            return CONTENT_URI.buildUpon().appendPath(inhabitantId).build();
        }


        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    }

    public static final class InhabitantProfessionEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INHABITANT_PROFESSION).build();

        public static final String TABLE_NAME = "inhabitant_profession";
        public static final String INHABITANT_ID = "inhabitant_id";
        public static final String PROFESSION_ID = "profession_id";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static String getInhabitantIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }


    public static final class InhabitantFriendEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INHABITANT_FRIEND).build();


        public static final String TABLE_NAME = "inhabitant_friend";
        public static final String TABLE_ALIAS = " INAFRI";
        public static final String INHABITANT_ID = "inhabitant_id";
        public static final String FRIEND_ID = "friend_id";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static String getInhabitantIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }


}
