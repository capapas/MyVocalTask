package fr.esgi.record_me;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.media.MediaRecorder;

public class MainActivity extends Activity {
	private int cmpt = 0;
	private Chronometer time_;
	private SeekBar progress_;
	Button start_record;
	// Button redirige;
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
		setContentView(R.layout.activity_main);
		progress_ = (SeekBar) findViewById(R.id.progress_);
		time_ = (Chronometer) findViewById(R.id.time_);
		start_record = (Button) findViewById(R.id.btn_start);
		save_record = (Button) findViewById(R.id.btn_save);

		start_record.setOnClickListener(action_start);
		save_record.setOnClickListener(action_stop);
		message = new AlertDialog.Builder(this).create();

	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(MainActivity.this, "Error: " + what + ", " + extra,
					Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(MainActivity.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					.show();
		}
	};

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);
		 date_sys = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + date_sys+ file_exts[currentFormat]);
	}

	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

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

			// TODO Auto-generated method stub
			if (enable == false) {
				// TODO evenement TICK du chronometre
				startRecording();
				time_.setOnChronometerTickListener(new OnChronometerTickListener() {

					@Override
					public void onChronometerTick(Chronometer chronometer) {
						// TODO Auto-generated method stub
					
						String chronoText = time_.getText().toString();
						String array[] = chronoText.split(":");
						cmpt = cmpt + Integer.parseInt(array[1]);
						progress_.setProgress(cmpt / 60);
					
					}
				});
			
				start_record.setBackgroundResource(R.drawable.stop);
				time_.start();
				enable = true;
			} else {
				save_record.setEnabled(true);
				
				time_.stop();
				///stopRecording();
				start_record.setBackgroundResource(R.drawable.start);
				enable = false;
			}
			// start_record.setEnabled(false);
			// startRecording();
			// message.setTitle("start");
			// message.setMessage("Button start record.");
			// message.show();
			// recorder.setAudioEncoder(MediaRecorder.AudioSource.MIC);

		}
	};

	private void stopRecording() {
		if (null != recorder) {
			
			recorder.stop();
			time_.stop();
			recorder.reset();
			recorder.release();
			progress_.setProgress(0);
			cmpt = 0;
			time_.setBase(SystemClock.elapsedRealtime());
			recorder = null;
		}
	}

	View.OnClickListener action_stop = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	
			stopRecording();
			Save_My_Note();
			start_record.setBackgroundResource(R.drawable.start);
			

			// start_record.setEnabled(true);
			

			// message.setTitle("stop");
			// message.setMessage("Button stop record.");
			// message.show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
private void Save_My_Note(){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("MonDictaphone");
		alert.setMessage("Saisissez un nom pour votre note!");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);
		
		
		alert
		.setPositiveButton("Save",new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
//				System.currentTimeMillis()
				if(!input.getText().toString().matches("^[-a-zA-Z0-9._]+")){
					Save_My_Note();
				}
		   	String filepath = Environment.getExternalStorageDirectory().getPath()+"/"+AUDIO_RECORDER_FOLDER;
			   File out=null;
			   if(input.getText().toString().trim().equals("")){
				   out=new File(filepath+"/"+System.currentTimeMillis()+file_exts[currentFormat]);
			   }
			   else{
				   
					 out=new File(filepath+"/"+input.getText()+file_exts[currentFormat]); 
				
			   }

			   if(out.exists()){
				   out=new File(filepath+"/"+input.getText()+"-"+System.currentTimeMillis()+file_exts[currentFormat]);
			   }
				File file = new File(filepath+"/"+date_sys+file_exts[currentFormat]);
				
				if(file.exists()){
					
					file.renameTo(out);
					save_record.setEnabled(true);
				}
			}
		});
	
		alert.show(); 
         
		
          }	
private boolean checkString(String s) {

      return s.matches("\\w*[.](java|cpp|class)");

  }

	// TODO Redirige

	public void red(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Home.class);
		startActivity(i);
		// start_record.setEnabled(true);
		// stopRecording();

		// message.setTitle("stop");
		// message.setMessage("Button stop record.");
		// message.show();
	}

}
