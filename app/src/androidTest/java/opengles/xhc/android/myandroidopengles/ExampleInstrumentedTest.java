package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.e("xhc" , " printf ");

//        Looper.prepare();
//        Toast.makeText(appContext , "test " , Toast.LENGTH_LONG).show();
        assertEquals("opengles.xhc.android.myandroidopengles", appContext.getPackageName());
    }
}
