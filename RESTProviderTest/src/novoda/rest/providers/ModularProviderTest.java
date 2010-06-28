package novoda.rest.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import novoda.rest.providers.ModularProvider;
import novoda.rest.test.providers.ProviderTestCase3;

public class ModularProviderTest extends ProviderTestCase3<ModularProvider> {

    public ModularProviderTest(Class<ModularProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }
    
    public ModularProviderTest() {
        super(ModularProvider.class, "novoda.rest.test.twitter");
    }
    
    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }
    
    public void testJustTrue() throws Exception {
        assertTrue(true);
    }
    
    public void testCreationOfTable() throws Exception {
    	ContentValues values = new ContentValues();
    	values.put("TEST", "TEST");
    	getMockContentResolver().insert(Uri.parse("content://novoda.rest.test.twitter/test"), values);
    	Cursor cur = getProvider().dbHelper.getReadableDatabase().rawQuery("SELECT * FROM test;", null);
    	assertTrue(cur.moveToFirst());
    	assertEquals(cur.getString(0), "TEST");
    }
}
