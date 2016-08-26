package Util;

import android.app.Application;
import android.content.Context;

/**
 * Created by dvillanueva on 26/08/2016.
 */
public class AppContext extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public static Context GetContextApp() {
        return AppContext.context;
    }
}
