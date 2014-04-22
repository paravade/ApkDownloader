package com.jackpf.apkdownloader;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.jackpf.apkdownloader.Entity.App;

public class Downloader
{
    /**
     * Context
     */
    private Context context;
    
    /**
     * Constructor
     * 
     * @param context
     */
    public Downloader(Context context)
    {
        this.context = context;
    }
    
    /**
     * Download an app
     * 
     * @param app
     */
    @SuppressLint("NewApi")
    public void download(App app)
    {
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        
        Request request = new DownloadManager.Request(Uri.parse(app.getDownloadPath()))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(true)
            .setTitle(app.getAppId())
            .setDescription("App downloading...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, app.getAppId() + ".apk")
            .addRequestHeader("Cookie", "MarketDA=" + app.getMarketDA()) // + ";ANDROIDSECURE=" + authToken)
        ;
        
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        
        dm.enqueue(request);
    }
}