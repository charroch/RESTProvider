
package novoda.rest.example.actor;

import android.content.Intent;
import android.net.Uri;
import android.test.InstrumentationTestCase;

public class Launcher extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        Runtime.getRuntime().exec("setprop log.tag.httpservice VERBOSE");
        Runtime.getRuntime().exec("setprop log.tag.httpservice-tester VERBOSE");
        Runtime.getRuntime().exec("setprop log.tag.SQLiteProvider VERBOSE");
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        Runtime.getRuntime().exec("setprop log.tag.httpservice INFO");
        Runtime.getRuntime().exec("setprop log.tag.httpservice-tester INFO");
        Runtime.getRuntime().exec("setprop log.tag.SQLiteProvider INFO");
        super.tearDown();
    }

    public void testTwitterTrend() throws Exception {
        Intent intent = new Intent("GET", Uri.parse("http://api.twitter.com/1/trends.json"));
        intent.setClass(getInstrumentation().getContext(),
                com.novoda.lib.httpservice.HttpService.class);
        assertNotNull(getInstrumentation().getContext().startService(intent));
        Thread.sleep(10000);
    }
}
