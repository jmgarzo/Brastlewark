package com.jmgarzo.brastlewark;

import android.content.ContentValues;
import android.database.Cursor;

import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jmgarzo on 2/26/2018.
 */

public class TestUtils {

    public static final int BULK_INSERT_HABITANTS_TO_INSERT = 10;

    static ContentValues createTestInhabitantContentValues() {
        ContentValues testInhabitantsValues = new ContentValues();

        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry._ID,0);
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.NAME,"Tobus Quickwhistle");
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.THUMBNAIL,"http://www.publicdomainpictures.net/pictures/10000/nahled/thinking-monkey-11282237747K8xB.jpg");
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.AGE,306);
        //TODO: There is a problem with double values precision https://code.google.com/p/android/issues/detail?id=22219
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.WEIGHT,39.065);
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.HEIGHT,107.758);
        testInhabitantsValues.put(BrastlewarkContract.InhabitantsEntry.HAIR_COLOR,"Pink");

        return testInhabitantsValues;
    }

    static void validateThenCloseCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertNotNull("This cursor is null. Did you make sure to register your ContentProvider in the manifest?",
                valueCursor);
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);

            /* Test to see if the column is contained within the cursor */
            String columnNotFoundError = "Column '" + columnName + "' not found. " + error;
            assertFalse(columnNotFoundError, index == -1);

            /* Test to see if the expected value equals the actual value (from the Cursor) */
            String expectedValue = entry.getValue().toString();
            String actualValue = valueCursor.getString(index);


            String valuesDontMatchError = "Actual value '" + actualValue
                    + "' did not match the expected value '" + expectedValue + "'. "
                    + error;

            assertEquals(valuesDontMatchError,
                    expectedValue,
                    actualValue);
        }
    }

}
