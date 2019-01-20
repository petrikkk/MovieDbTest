package com.mobile.petrk.moviedbtest;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {
    RetrofitCreator rc = new RetrofitCreator();
    private int helper;
    final RetrofitInterface dataAPI = rc.getRetrofit().create(RetrofitInterface.class);
    private List<Result> newList;
    private int[] idList;
    int i = 0;
    private MovieListPOJO movieListPOJO;
    private String file_url = "https://api.themoviedb.org/3/movie/changes?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&page=1";
    private String file_base = "https://api.themoviedb.org/3/movie/";
    private String file_end = "?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&language=en-US";
    private ListView listView;
    private MyAdapter adapter;


    //private List <Result> results;

    //File file = new File(this.getFilesDir(), "movieList.xml");
    @Override
    public void onCreate(Bundle savedInstanceState) {

        //final TextView textView = (TextView) findViewById(R.id.textview);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ContentDownload download = new ContentDownload();
        download.DownloadFiles(file_url, "serverlist.json");
        download.ReadFile(Environment.getExternalStorageDirectory()+"/Download/serverlist.json");
        System.out.println("Download OK");

        Gson gson = new Gson();

        try {
            System.out.println("Starting GSON:");
            MovieListPOJO movieListPOJO = gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/Download/serverlist.json"), MovieListPOJO.class);
            System.out.println(movieListPOJO.getTotalResults().toString());
            System.out.println("Start vypisu z Listu:");
            List<Result> results = movieListPOJO.getResults();

            List<Result> shrinkedList = results.subList(0,10);

            for (Result r:shrinkedList) {
                //System.out.println(r.getId());
                String res = r.getId().toString();
                //System.out.println("Sestavena adresa: " + file_base + res + file_end + " filename: " + res);
                download.DownloadFiles(file_base + res + file_end, res);
                try {
                    //System.out.println("Starting GSON - naplneni movieDetailsPOJO:");
                    MovieDetailsPOJO movieDetailsPOJO = gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/Download/" + r.getId()), MovieDetailsPOJO.class);
                    //System.out.println("Starting GSON - prirazeni plakatu movieDetailsPOJO:");
                    if (movieDetailsPOJO.getPosterPath()==null)
                        r.setImgURL("not available");
                    else
                        r.setImgURL(movieDetailsPOJO.getPosterPath());


                    //System.out.println("Starting GSON - prirazeni jmena filmu movieDetailsPOJO:");
                    if (movieDetailsPOJO.getOriginalTitle()!=null)
                        r.setMovieName(movieDetailsPOJO.getOriginalTitle());
                    else
                        r.setMovieName("not available");

                    //System.out.println("VYPIS ADRESA OBRAZKU, JMENO FILMU:");
                   // System.out.println(r.getImgURL()+ " " +r.getMovieName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                newList=shrinkedList;
            }
           // System.out.println("****Exiting ALL:");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ListView list = findViewById(R.id.listView);
        adapter = new MyAdapter(this, newList);

  /*      for (Result r:newList) {

            System.out.println("TEST OUTPUT: "+ r.getImgURL()+"  "+r.getMovieName()+"  "+r.getId()+"  "+r.getAdult());
        }
      */
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "intent");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        //MyAdapter customAdapter = new MyAdapter(this, newList);
        //list.setAdapter(customAdapter);

    }
}



       /* final ContentDownload download = new ContentDownload();

           new Thread(new Runnable() {
            public void run() {
                currentThread().setName("prvniVlakno");
                download.DownloadFiles(file_url, "serverlist.json");
            }

        }).start();



       System.out.println(Environment.getExternalStorageDirectory()+"/serverlist.json");
       download.ReadFile(Environment.getExternalStorageDirectory()+"/serverlist.json");
       System.out.println("Download OK");
       Gson gson = new Gson();

       try {
           System.out.println("Starting GSON:");
            MovieListPOJO movieListPOJO = gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/serverlist.json"), MovieListPOJO.class);
            System.out.println(movieListPOJO.getTotalResults().toString());
            System.out.println("Start vypisu z Listu:");
            List<Result> results = movieListPOJO.getResults();
            for (Result r:results) {
               System.out.println(r.getId());

           }
           System.out.println("Exiting GSON:");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("FINAL TEST ");
      //**********************************************************************************************************************************************************
        try {
            for (final Result r:result) {
            new Thread(new Runnable() {
                public void run() {
                    currentThread().setName("druheVlkno");
                    String res = r.getId().toString();
                    System.out.println("Sestavena adresa: " + file_base + res + file_end + " filename: " + res);
                    download.DownloadFiles(file_base + res + file_end, res);
                }
            }).start();
            Gson gson1 = new Gson();

            try {
                System.out.println("Starting GSON - naplneni movieDetailsPOJO:");
                MovieDetailsPOJO movieDetailsPOJO = gson1.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/" + r.getId()), MovieDetailsPOJO.class);
                System.out.println("Starting GSON - prirazeni plakatu movieDetailsPOJO:");
                r.setImgURL(movieDetailsPOJO.getPosterPath());
                System.out.println("Starting GSON - prirazeni jmena filmu movieDetailsPOJO:");
                r.setMovieName(movieDetailsPOJO.getOriginalTitle());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}*/














