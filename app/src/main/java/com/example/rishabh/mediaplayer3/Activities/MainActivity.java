package com.example.rishabh.mediaplayer3.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rishabh.mediaplayer3.Adapter.MusicAdapter;
import com.example.rishabh.mediaplayer3.Interface.OnItemClickListener;
import com.example.rishabh.mediaplayer3.POJO.music;
import com.example.rishabh.mediaplayer3.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String path;
    private static final int my_permission_request = 1;
    RecyclerView rvmusic;
    String currentTitle;
    String currentArtist;
    MusicAdapter musicAdapter;
    ArrayList<music> mus=new ArrayList<>();
    int count=0;

    public static final String TAG="main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvmusic = (RecyclerView)findViewById(R.id.rvmusic);


        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},my_permission_request);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},my_permission_request);
            }

        }
        else{
            dostuff();
        }
    }

    public void dostuff(){

        rvmusic.setLayoutManager(new LinearLayoutManager(this));
        musicAdapter = new MusicAdapter(mus,this);
        rvmusic.setAdapter(musicAdapter);
        musicAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Integer pos) {
                Intent i= new Intent(MainActivity.this,SongActivity.class);

                i.putExtra("data",mus.get(pos).getPath());

                startActivity(i);
                if(count==0)
                {
                    count = 1;

                }
                else
                {
                    SongActivity.mp.release();
                }

            }
        });

    }


    public void getMusic(){
//        ContentResolver contentResolver = getContentResolver();
//        Uri songurl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor songCursor = contentResolver.query(songurl,null,null,null,null);
//        if(songCursor != null && songCursor.moveToFirst()){
//            int songtitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int songartist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
//
//
//            do{
//                currentTitle = songCursor.getString(songtitle);
//                currentArtist = songCursor.getString(songartist);
//                path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//                music m=new music(currentTitle,currentArtist,path);
//
//                mus.add(m);
//
//                count++;
//                // Log.d(TAG, "getMusic: "+count);
//
//            }
//            while(songCursor.moveToNext());
//        }

        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = managedQuery(allsongsuri, null, selection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    currentTitle = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    int song_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));

                    path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                                        int album_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    currentArtist = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    int artist_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                    mus.add(new music(currentTitle,currentArtist,path));

                } while (cursor.moveToNext());

            }
        }
    }




    // PERMISSION METHOD
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){
            case my_permission_request:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();

                        dostuff();
                    }
                    else{
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMusic();
    }
}