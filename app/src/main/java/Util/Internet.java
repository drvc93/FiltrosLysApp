package Util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

public class Internet{

	static boolean bVal=false;
	static Context contextFin;
	public static boolean ConexionWifi(Context contexto){
		ConnectivityManager connectivity = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (info != null) {
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ConexionRedMovil(Context contexto){
		ConnectivityManager connectivity = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (info != null) {
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean DatosEnLinea(Context contexto){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Socket socket;
		try{

			socket = new Socket();
			//socket.connect(new InetSocketAddress("100.100.100.57", 8080), 2000);
			socket.connect(new InetSocketAddress("8.8.8.8", 80), 2000);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean DatosEnLineaPing(Context contexto){
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
			int exitValue = process.waitFor();
			return (exitValue==0)?true:false;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean ConexionInternet(Context contexto) {
		if(ConexionRedMovil(contexto) || ConexionWifi(contexto)){
			if (DatosEnLineaPing(contexto)) {
				return true;
			} else {
				if (DatosEnLinea(contexto)) {
					return true;
				} else {
					return false;
				}
			}
		}else{return false;}
	}

}