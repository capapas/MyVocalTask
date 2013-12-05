package fr.esgi.record_me;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Media_Player extends Activity {
	private String path_note;
	private MediaPlayer media=null;
	
private Home my_note=new Home(media,path_note);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media__player);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media__player, menu);
		return true;
	}
public void play(){
	
	my_note.PlayNote(path_note);
}
public void stop(){
	my_note.StopNote();
	
}
public void pause(){
	
	
}
}
