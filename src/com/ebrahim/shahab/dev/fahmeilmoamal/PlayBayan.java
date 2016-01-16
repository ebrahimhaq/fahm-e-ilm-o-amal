package com.ebrahim.shahab.dev.fahmeilmoamal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;



import com.ebrahimhaq.dev.fahmeilmoamal.R;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayBayan extends Activity {

	private MediaPlayer mediaPlayer;
	public TextView songName, duration;
	private double timeElapsed = 0, finalTime = 0;
	private int forwardTime = 2000, backwardTime = 2000;
	private Handler durationHandler = new Handler();
	private SeekBar seekbar;
	private String filePath;
	private String fileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.player);
		

		 // getting intent data
       Intent in = getIntent();
       
       // Get JSON values from previous intent
      
       fileName = in.getStringExtra("type");
        filePath = in.getStringExtra("file_path");

		//initialize views
		initializeViews();
	}
	
	public void initializeViews(){
		songName = (TextView) findViewById(R.id.songName);
		
		Uri myUri1 = Uri.parse(filePath);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(getApplicationContext(), myUri1);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	mediaPlayer = MediaPlayer.create(this, R.raw.sample_song);
		finalTime = mediaPlayer.getDuration();
		duration = (TextView) findViewById(R.id.songDuration);
		seekbar = (SeekBar) findViewById(R.id.seekBar);
		songName.setText(fileName);
		
		seekbar.setMax((int) finalTime);
		seekbar.setClickable(false);
	}

	// play mp3 song
	public void play(View view) {
		mediaPlayer.start();
		timeElapsed = mediaPlayer.getCurrentPosition();
		seekbar.setProgress((int) timeElapsed);
		durationHandler.postDelayed(updateSeekBarTime, 100);
	}

	//handler to change seekBarTime
	private Runnable updateSeekBarTime = new Runnable() {
		public void run() {
			//get current position
			timeElapsed = mediaPlayer.getCurrentPosition();
			//set seekbar progress
			seekbar.setProgress((int) timeElapsed);
			//set time remaing
			double timeRemaining = finalTime - timeElapsed;
			duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
			
			//repeat yourself that again in 100 miliseconds
			durationHandler.postDelayed(this, 100);
		}
	};

	// pause mp3 song
	public void pause(View view) {
		mediaPlayer.pause();
	}

	// go forward at forwardTime seconds
	public void forward(View view) {
		//check if we can go forward at forwardTime seconds before song endes
		if ((timeElapsed + forwardTime) <= finalTime) {
			timeElapsed = timeElapsed + forwardTime;

			//seek to the exact second of the track
			mediaPlayer.seekTo((int) timeElapsed);
		}
	}

	// go backwards at backwardTime seconds
	public void rewind(View view) {
		//check if we can go back at backwardTime seconds after song starts
		if ((timeElapsed - backwardTime) > 0) {
			timeElapsed = timeElapsed - backwardTime;
			
			//seek to the exact second of the track
			mediaPlayer.seekTo((int) timeElapsed);
		}
	}

}