package com.example.rishabh.mediaplayer3.Activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabh.mediaplayer3.R;

import java.util.concurrent.TimeUnit;

public class SongActivity extends AppCompatActivity {


    public static MediaPlayer mp;
    public static final String TAG = "hello";
    String path;
    Button rewind, pause, play, forward;
    TextView start, finish;
    SeekBar seekbar;
    int starttime;
    Handler myHandler = new Handler();
    int finaltime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        rewind = (Button) findViewById(R.id.rewind);
        pause = (Button) findViewById(R.id.pause);
        play = (Button) findViewById(R.id.play);
        forward = (Button) findViewById(R.id.forward);
        start = (TextView) findViewById(R.id.start);
        finish = (TextView) findViewById(R.id.finish);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setClickable(true);

        path = getIntent().getStringExtra("data");
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            final String u = path;
            mp.setDataSource(this, Uri.parse(u));
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }


        //BUTTONS

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();
                pause.setEnabled(true);
                play.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentpos = mp.getCurrentPosition();
                mp.seekTo(currentpos + 10000);
                Toast.makeText(SongActivity.this, "10 sec forward", Toast.LENGTH_SHORT).show();

            }
        });
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentpos1 = mp.getCurrentPosition();
                mp.seekTo(currentpos1 - 10000);
                Toast.makeText(SongActivity.this, "10 sec rewinded", Toast.LENGTH_SHORT).show();

            }
        });



        SongActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mp != null){
                    int current = mp.getCurrentPosition()/1000;
                    seekbar.setProgress(current);
                }
                myHandler.postDelayed(this,1000);
            }
        });



        // SEEKBAR
        myHandler.postDelayed(updateSeekBarTime, 100);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbar.setMax(mp.getDuration() / 1000);
                start.setText(progress + "/" + seekbar.getMax());

                if(fromUser)
                {
                    mp.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            finaltime = mp.getDuration();
            starttime = mp.getCurrentPosition();


            // for starttime and finaltimex
            double timeRemaining =starttime;
            start.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            double timetotal = finaltime;
            finish.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timetotal), TimeUnit.MILLISECONDS.toSeconds((long) timetotal) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timetotal))));
            myHandler.postDelayed(this, 100);
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

