package com.mobile.petrk.moviedbtest;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import pojo.Result;
import support.ContentDownload;
import support.LoadLists;



public class MainActivity extends AppCompatActivity {
    private List<Result> newList;
    private String file_url = "https://api.themoviedb.org/3/movie/changes?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&page=1";
    private String file_base = "https://api.themoviedb.org/3/movie/";
    private String file_end = "?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&language=en-US";
    private MyAdapter adapter;
    final ContentDownload download = new ContentDownload();
    private int moviesSelected = 10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateListView(moviesSelected);

    }
    //zjisteni id filmu podle pozice v listu
        public String GetIdFromPosition(int position){
            int i = 0;
            String id = "0";
            for (Result r:newList) {
                if(position==i)
                    id=r.getId().toString();
                i++;
            }
            System.out.println(id);
            return id;
        }

    public void CreateListView (int movies){
        setContentView(R.layout.activity_main);
        LoadLists loadLists = new LoadLists();
        newList = loadLists.getResultList(movies, file_url, file_base, file_end);
        final ListView list = findViewById(R.id.listView);
        adapter = new MyAdapter(this, newList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
                myIntent.putExtra("id", GetIdFromPosition(position));
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
    //zadany pocet filmu z texedit, refresh listu
    public void onClickBtn(View v)
    {
        EditText editText = (EditText) findViewById(R.id.numberPicker);
        //editText.setText("10");
        try {
            moviesSelected = Integer.parseInt(editText.getText().toString());
        } catch ( Exception e ){
            e.printStackTrace();
        }
        if (moviesSelected > 50)
            moviesSelected = 50;
        if (moviesSelected < 0)
            moviesSelected = 1;
        CreateListView(moviesSelected);

    }
}



















