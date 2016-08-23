package Tasks;

import android.os.AsyncTask;

import java.util.ArrayList;

import DataBase.HistorialInspGenDB;

/**
 * Created by dvillanueva on 23/08/2016.
 */
public class GetHistorialInspGenTask extends AsyncTask<String, String, ArrayList<HistorialInspGenDB>> {
    ArrayList<HistorialInspGenDB> result;

    @Override
    protected ArrayList<HistorialInspGenDB> doInBackground(String... strings) {
        return null;
    }
}
