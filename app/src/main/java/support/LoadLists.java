package support;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import pojo.MovieDetailsPOJO;
import pojo.MovieListPOJO;
import pojo.Result;

public class LoadLists {

    private MovieDetailsPOJO movieDetailsPOJO;
    private List<Result> newList;

    public List<Result> getResultList (int listSize, String file_url, String file_base, String file_end) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //stazeni "changes" json objektu do externiho uloziste /sdcard/Downloads
        //probiha synchronne
        ContentDownload download = new ContentDownload();
        download.DownloadFiles(file_url, "serverlist.json");
        download.ReadFile(Environment.getExternalStorageDirectory() + "/Download/serverlist.json");
        Gson gson = new Gson();

        //naplneni pojo Result a pridani cesty k obrazku, ktery se bude zobrazovat ve ListView
        try {
            MovieListPOJO movieListPOJO = gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/Download/serverlist.json"), MovieListPOJO.class);
            List<Result> results = movieListPOJO.getResults();
            List<Result> shrinkedList = results.subList(0, listSize);
            for (Result r : shrinkedList) {
                String res = r.getId().toString();
                download.DownloadFiles(file_base + res + file_end, res);
                try {
                    MovieDetailsPOJO movieDetailsPOJO = gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/Download/" + r.getId()), MovieDetailsPOJO.class);
                    if (movieDetailsPOJO.getPosterPath() == null)
                        r.setImgURL("not available");
                    else
                        r.setImgURL(movieDetailsPOJO.getPosterPath());
                    if (movieDetailsPOJO.getOriginalTitle() != null)
                        r.setMovieName(movieDetailsPOJO.getOriginalTitle());
                    else
                        r.setMovieName("not available");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                newList = shrinkedList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newList;
    }
    //nacteni jednoho pojo MovieDetailsPOJO - vsechny informace o filmu
    public MovieDetailsPOJO getDetailsList (int id) {
        Gson gson = new Gson();
        try {
            movieDetailsPOJO =  gson.fromJson(new FileReader(Environment.getExternalStorageDirectory() + "/Download/" + id), MovieDetailsPOJO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDetailsPOJO;
    }
}
