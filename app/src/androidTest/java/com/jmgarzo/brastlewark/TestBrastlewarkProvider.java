package com.jmgarzo.brastlewark;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;
import com.jmgarzo.brastlewark.model.data.BrastlewarkDbHelper;
import com.jmgarzo.brastlewark.model.data.BrastlewarkProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by jmgarzo on 2/26/2018.
 */

@RunWith(AndroidJUnit4.class)
public class TestBrastlewarkProvider {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {

        deleteAllRecordsFromInhabitantTables();
    }

    @Test
    public void testProviderRegistry() {

        String packageName = mContext.getPackageName();
        String BrastlewarkClassName = BrastlewarkProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, BrastlewarkClassName);

        try {

            PackageManager pm = mContext.getPackageManager();

            /* The ProviderInfo will contain the authority, which is what we want to test */
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = BrastlewarkContract.CONTENT_AUTHORITY;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: BrastlewarkProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: BrastlewarkProvider not registered at " + mContext.getPackageName();
            fail(providerNotRegisteredAtAll);
        }


    }

    @Test
    public void testBasicMovieQuery(){
        BrastlewarkDbHelper dbHelper = new BrastlewarkDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testInhabitantValues = TestUtils.createTestInhabitantContentValues();

        long movieRowId = db.insert(BrastlewarkContract.InhabitantsEntry.TABLE_NAME,null,testInhabitantValues);

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed,movieRowId != -1);

        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                BrastlewarkContract.InhabitantsEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtils.validateThenCloseCursor("testBasicMovieQuery",
                movieCursor,
                testInhabitantValues);
    }

    private void deleteAllRecordsFromInhabitantTables() {
        BrastlewarkDbHelper helper = new BrastlewarkDbHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME, null, null);
        database.delete(BrastlewarkContract.InhabitantFriendEntry.TABLE_NAME, null, null);
        database.delete(BrastlewarkContract.InhabitantsEntry.TABLE_NAME, null, null);
        database.delete(BrastlewarkContract.ProfessionsEntry.TABLE_NAME, null, null);

        database.close();
    }

}
