package my;

import android.util.Log;


public class MyLog {
    private static boolean DEBUG = true;

    public static void i(String tag, Object o) {
        if (DEBUG) {
            Log.i(tag, o.toString());
        }
    }
}
