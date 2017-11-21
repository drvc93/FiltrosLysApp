package Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Util.Constans;

/**
 * Created by dvillanueva on 25/08/2016.
 */
public class GuardarImgLocalTask extends AsyncTask<byte[], String, String> {

    String filename;
    String result = "";
    byte[] byteImage;

    public GuardarImgLocalTask(String path, byte[] byteImage) {

        this.filename = path;
        this.byteImage = byteImage;
    }

    @Override
    protected String doInBackground(byte[]... bytes) {
        File direct = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto);

        File photo = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto + filename);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto + filename);
        if (photo.exists()) {
            photo.delete();
        }
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto);
            wallpaperDirectory.mkdirs();
        }


        try {
            byte[] varb = bytes[0];
            varb = Base64.decode(varb, Base64.DEFAULT);
            Bitmap btmp = BitmapFactory.decodeByteArray(varb, 0, varb.length);
            ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
            btmp.compress(Bitmap.CompressFormat.PNG,  100, byteOutPut);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(byteOutPut.toByteArray());
            fo.flush();
            fo.close();

            result = "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("result save image local ==>", result);
        return result;
    }
}
