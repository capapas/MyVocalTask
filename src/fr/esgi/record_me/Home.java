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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity implements OnItemClickListener  {
	private File file;
	private ArrayList<String> liste_;
	AlertDialog message;
    final String state = Environment.getExternalStorageState();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	       liste_ =new ArrayList<String>();
		   getAllFilesOfDir(Environment.getExternalStorageDirectory());
		   FilesAdapter fa = new FilesAdapter(this, R.layout.my_files, liste_);
		   ((ListView)findViewById(R.id.listView1)).setAdapter(fa);
		   ((ListView)findViewById(R.id.listView1)).setOnItemClickListener(this);
		  
	  

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
		TextView path=(TextView) arg1.findViewById(R.id.txt_filename);
		String name_file=Environment.getExternalStorageDirectory()+"/"+path.getText().toString();
		// public static MediaPlayer create (Context context, int resid)
		MediaPlayer media; //=//new  MediaPlayer();
		// public static MediaPlayer create (Context context, Uri uri)
		media = MediaPlayer.create(this, Uri.parse(name_file));
		media.setScreenOnWhilePlaying(true);
		media.start();
		
    //	message=new AlertDialog.Builder(this).create();
	//	message.setTitle("File");
	//	message.setMessage(name_file+"");
	//	message.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	private void getAllFilesOfDir(File directory) {
	//    Log.e("Record","Directory: " + directory.getAbsolutePath() + "\n");
    
	    final File[] files = directory.listFiles();

	    if ( files != null ) {
	        for ( File file : files ) {
	            if ( file != null ) {
	                if ( file.isDirectory() ) {  // it is a folder...
	                    getAllFilesOfDir(file);
	                }
	                else {  // it is a file...
	                	if(file.getAbsolutePath().endsWith(".mp3")){
	                	//Log.e("Record", "File: " + file.getName() + "\n");
	                   // Log.e("Record", "File: " + file.getAbsolutePath() + "\n");
	                    liste_.add(file.getName());
	                
	                    
	                	}
	                    
	                }
	            }
	        }
	    }
	}
	


}
