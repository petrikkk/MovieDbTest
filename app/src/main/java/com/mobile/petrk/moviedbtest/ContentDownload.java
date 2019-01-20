package com.mobile.petrk.moviedbtest;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;

import java.net.URL;

public class ContentDownload {


    private String fileName;
    private String UrlAddress;

    public ContentDownload() {

    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public String getURL() {
        return UrlAddress;
    }

    public void setURL(String Address) {
        this.UrlAddress = Address;
    }
    public void DownloadFiles(String urlAddress, String fileName){

        try {
            URL u = new URL(urlAddress);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/Download/" + fileName));
            //System.out.println("Cesta k ulozisti: "+Environment.getExternalStorageDirectory());
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ReadFile (String path) {
        String fileName = path;
        File file = new File(fileName);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                   //System.out.println(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}





