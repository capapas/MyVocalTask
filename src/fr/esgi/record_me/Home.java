package fr.esgi.record_me;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity implements OnItemClickListener {
	private File file;
	private String path_note="";
	private ArrayList<String> liste_;
	AlertDialog message;
	final String state = Environment.getExternalStorageState();
	private static final String AUDIO_RECORDER_FOLDER = "MonDictaphone";

 
// TODO Constructeur d'inisialisation
    
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		liste_ = new ArrayList<String>();
		getAllFilesOfDir(Environment.getExternalStorageDirectory());
		FilesAdapter fa = new FilesAdapter(this, R.layout.my_files, liste_);
		((ListView) findViewById(R.id.listView1)).setAdapter(fa);
		((ListView) findViewById(R.id.listView1)).setOnItemClickListener(this);

	}

 public void Get_Param(String Ma_note){
	
    Ma_note=this.path_note;
    
    }  

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		TextView path = (TextView) arg1.findViewById(R.id.txt_filename);
		 path_note = Environment.getExternalStorageDirectory() + "/"+AUDIO_RECORDER_FOLDER+"/"+path.getText().toString();
		// public static MediaPlayer create (Context context, int resid)
		// StopNote();
	 //    PlayNote(path_note);
		  to_media_player();
		// message=new AlertDialog.Builder(this).create();
		// message.setTitle("File");
		// message.setMessage(name_file+"");
		// message.show();
	}
	 private void to_media_player(){
		    Intent i = new Intent(this, Media_Player.class);
			startActivity(i);
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	private void getAllFilesOfDir(File directory) {
		// Log.e("Record","Directory: " + directory.getAbsolutePath() + "\n");
        File f=new File(directory+"/"+AUDIO_RECORDER_FOLDER);
		final File[] files = f.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file != null) {
					if (file.isDirectory()) { // it is a folder...
						getAllFilesOfDir(file);
					} else { // it is a file...
						if (file.getAbsolutePath().endsWith(".mp3") || file.getAbsolutePath().endsWith(".MP3") || file.getAbsolutePath().endsWith(".mp4") || file.getAbsolutePath().endsWith(".MP4")) {
							// Log.e("Record", "File: " + file.getName() +
							// "\n");
							// Log.e("Record", "File: " + file.getAbsolutePath()
							// + "\n");
							liste_.add(file.getName());

						}

					}
				}
			}
		}
	}

}
