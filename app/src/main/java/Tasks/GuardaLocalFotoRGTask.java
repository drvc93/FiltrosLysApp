package Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import Util.Constans;

/**
 * Creado por dvillanueva el  05/02/2018 (FiltrosLysApp).
 */

public class GuardaLocalFotoRGTask extends AsyncTask<byte[],String,String> {
    String filename;
    String result = "";
    byte[] byteImage;

    public GuardaLocalFotoRGTask(String path, byte[] byteImage) {

        this.filename = path;
        this.byteImage = byteImage;
    }

    @Override
    protected String doInBackground(byte[]... bytes) {
        File direct = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_RG);

        File photo = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_RG + filename);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_RG + filename);
        if (photo.exists()) {
            photo.delete();
        }
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + Constans.Carpeta_foto_RG);
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
