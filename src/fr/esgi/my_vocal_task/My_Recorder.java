package fr.esgi.my_vocal_task;

import java.io.File;
import java.io.IOException;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.esgi.my_vocal_task.R;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
import android.media.MediaRecorder;

public class My_Recorder extends Activity {

	private Chronometer time_;
	Button start_record;
	Button redirige;
	Button save_record;
	AlertDialog message;
	String date_sys;
	Boolean enable = false;
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "MonDictaphone";
	private int currentFormat = 0;
	private MediaRecorder recorder = null;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
			MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
			AUDIO_RECORDER_FILE_EXT_3GP };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_recorder);

		time_ = (Chronometer) findViewById(R.id.time_);
		start_record = (Button) findViewById(R.id.btn_start);
		save_record = (Button) findViewById(R.id.btn_save);
		redirige = (Button) findViewById(R.id.redirige);
		start_record.setOnClickListener(action_start);
		save_record.setOnClickListener(action_stop);
		message = new AlertDialog.Builder(this).create();

	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);
		date_sys = new SimpleDateFormat("ddMMyyyy").format(Calendar
				.getInstance().getTime());
		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + date_sys + file_exts[currentFormat]);
	}

	private void startRecording() {
		redirige.setEnabled(false);
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	View.OnClickListener action_start = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			if (enable == false) {
				if (recorder == null) {
					startRecording();

				}
				time_.setOnChronometerTickListener(new OnChronometerTickListener() {

					@Override
					public void onChronometerTick(Chronometer chronometer) {

					}
				});

				start_record.setBackgroundResource(R.drawable.stop);
				time_.start();
				enable = true;
			} else {
				save_record.setEnabled(true);
				time_.stop();
				start_record.setBackgroundResource(R.drawable.start);
				enable = false;
			}
		}
	};

	private void stopRecording() {
		if (null != recorder) {

			recorder.stop();
			time_.stop();
			recorder.reset();
			recorder.release();
			redirige.setEnabled(true);
			time_.setBase(SystemClock.elapsedRealtime());
			recorder = null;
		}
	}

	View.OnClickListener action_stop = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			stopRecording();
			Save_My_Note();
			start_record.setBackgroundResource(R.drawable.start);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void Save_My_Note() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("MonDictaphone");
		alert.setMessage("Saisissez un nom pour votre note!");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (!input.getText().toString().matches("^[-a-zA-Z0-9._@]+")) {
					Save_My_Note();
				}
				String filepath = Environment.getExternalStorageDirectory()
						.getPath() + "/" + AUDIO_RECORDER_FOLDER;
				File out = null;
				if (input.getText().toString().trim().equals("")) {
					out = new File(filepath + "/" + System.currentTimeMillis()
							+ file_exts[currentFormat]);
				} else {

					out = new File(filepath + "/" + input.getText()
							+ file_exts[currentFormat]);

				}

				if (out.exists()) {
					out = new File(filepath + "/" + input.getText() + "-"
							+ System.currentTimeMillis()
							+ file_exts[currentFormat]);
				}
				File file = new File(filepath + "/" + date_sys
						+ file_exts[currentFormat]);

				if (file.exists()) {

					file.renameTo(out);
					save_record.setEnabled(false);
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					File out_cancel = null;
					String filepath = Environment.getExternalStorageDirectory()
							.getPath() + "/" + AUDIO_RECORDER_FOLDER;
					File file = new File(filepath + "/" + date_sys
							+ file_exts[currentFormat]);

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						out_cancel = new File(filepath + "/"
								+ System.currentTimeMillis()
								+ file_exts[currentFormat]);

						if (file.exists()) {

							file.renameTo(out_cancel);
							save_record.setEnabled(false);
						}
					}
				});

		alert.show();

	}

	public void redirect(View v) {
		Intent i = new Intent(this, Home.class);
		startActivity(i);

	}

}
