package fr.esgi.record_me;

import java.io.File;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;

public class Media_Player extends Activity {
	private String path_note="";
	private MediaPlayer media=null;
	AlertDialog message;
   private Home my_note=new Home();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media__player);
		my_note.Get_Param( path_note);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media__player, menu);
		return true;
	}
public void play(){
	// Log.e("moi","Directory: hello\n");
	 message=new AlertDialog.Builder(this).create();
	 message.setMessage("Play");
	 message.show();
		//File test= new File(path_note);
		//if(test.exists()){
		//media = MediaPlayer.create(this, Uri.parse(path_note));
		//media.setScreenOnWhilePlaying(true);
		//media.start();
		
		//}
	
}
public void stop(){

	if(media!=null){
		media.stop();
		media.release();
		
	}
	
}
public void pause(){
	
	
}

}
