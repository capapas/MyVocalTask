package fr.esgi.my_vocal_task;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ShowOneNote extends Activity implements OnCompletionListener,
		OnSeekBarChangeListener {
	private String path_note;
	private File note;
	private MediaPlayer media = null;
	private SeekBar seekBar;
	private TextView totalDuration;
	private TextView currentDuration;
	private Handler mHandler = new Handler();
	private Button btnPlay;

	// private Home my_note=new Home(media,path_note);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_one_note);

		Bundle param = getIntent().getExtras();
		if (param != null) {
			this.path_note = param.getString("NOTE");
		}

		Log.e("pathNote", "pathNote " + this.path_note);

		this.note = new File(this.path_note);
		this.media = Utils.getMediaPlayer(this.note);

		this.seekBar = (SeekBar) findViewById(R.id.seekBar1);
		this.totalDuration = (TextView) findViewById(R.id.duration);
		this.currentDuration = (TextView) findViewById(R.id.currentTime);
		this.btnPlay = (Button) findViewById(R.id.play);

		this.seekBar.setOnSeekBarChangeListener(this); // Important
		this.media.setOnCompletionListener(this);

		String noteName = Utils.noteName(this.note);
		String lastModificationDate = Utils.getLastModificationDate(this.note);

		((EditText) findViewById(R.id.noteName)).setText(noteName);
		((TextView) findViewById(R.id.modificationDate))
				.setText(lastModificationDate);

		btnPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (media.isPlaying()) {
					if (media != null) {
						media.pause();
						// Changing button image to play button
						btnPlay.setBackgroundResource(R.drawable.play);
					}
				} else {
					// Resume song
					if (media != null) {
						media.start();
						// Changing button image to pause button
						btnPlay.setBackgroundResource(R.drawable.pause_);
					}
				}
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.mHandler.removeCallbacks(this.mUpdateTimeTask);
		this.media.pause();
		this.media.stop();
		this.media.release();
		this.finish();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		this.finish();
	}

	// Cette fonction appelle les traitements associées au menu en haut
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_list:
			go_to_list_view();
			Log.e("actionSelected", "Voir la liste des notes");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void go_to_list_view() {

		Intent intent = new Intent(this, Home.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media__player, menu);
		return true;
	}

	public void playSong() {
		// Play song
		try {
			this.media.reset();

			this.media.prepare();
			this.media.start();

			// Changing Button Image to pause image
			this.btnPlay.setBackgroundResource(R.drawable.pause_);

			// set Progress bar values
			this.seekBar.setProgress(0);
			this.seekBar.setMax(100);

			// Updating progress bar
			this.updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		// my_note.StopNote();

	}

	public void pause() {
		this.media.pause();
	}

	public void remove_note(View v) {
		try {
			this.media.reset();
			this.note.delete();
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update_note(View v) {
		EditText newName = (EditText) findViewById(R.id.noteName);

		Log.e("newName", note.getParent() + '/' + newName.getText().toString());

		this.note.renameTo(new File(note.getParent() + '/'
				+ newName.getText().toString() + ".mp4"));
		this.media.reset();
		Intent intent = new Intent(this, Home.class);
		startActivity(intent);
	}

	public void updateProgressBar() {
		this.mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = media.getDuration();
		int currentPosition = Utils.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		media.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		this.media.pause();
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDurationLong = media.getDuration();
			long currentDurationLong = media.getCurrentPosition();

			// Displaying Total Duration time
			totalDuration.setText(""
					+ Utils.milliSecondsToTimer(totalDurationLong));
			// Displaying time completed playing
			currentDuration.setText(""
					+ Utils.milliSecondsToTimer(currentDurationLong));

			// Updating progress bar
			int progress = (int) (Utils.getProgressPercentage(
					currentDurationLong, totalDurationLong));
			// Log.d("Progress", ""+progress);
			seekBar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 100);
		}
	};
}
