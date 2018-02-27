package com.filtroslys.filtroslysapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import Util.Constans;

public class VisorWebDescargas extends AppCompatActivity {

    WebView  webPortal  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_web_descargas);
       // WebView myWebView = (WebView) findViewById(R.id.webDescargas);
       // WebSettings webSettings = myWebView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
         webPortal = (WebView) findViewById(R.id.webDescargas);
        webPortal.getSettings().setJavaScriptEnabled(true);
        webPortal.getSettings().setSaveFormData(true);
        webPortal.getSettings().setBuiltInZoomControls(true);
        webPortal.setWebViewClient(new WebViewClient());
        webPortal.setWebViewClient(new WebViewClient());
        webPortal.loadUrl("http://"+ Constans.IpDescarga+"/movilys/");
        webPortal.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

       /* webPortal.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Movilys.apk");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();

            }
        });*/

    }
}
