package fr.esgi.my_vocal_task;

import java.io.File;

import fr.esgi.my_vocal_task.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class ShowOneNote extends Activity {
	private String path_note;
	private File note;
	private MediaPlayer media = null;

	// private Home my_note=new Home(media,path_note);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_one_note);
		Bundle param = this.getIntent().getExtras();
		this.path_note = param.getParcelable("note");
		this.note = new File(this.path_note);
		
		String noteName = Utils.noteName(this.note);
		String lastModificationDate = Utils.getLastModificationDate(this.note);
		
		((EditText) findViewById(R.id.noteName)).setText(noteName);
		((TextView) findViewById(R.id.modificationDate)).setText(lastModificationDate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media__player, menu);
		return true;
	}

	public void play() {

		// my_note.PlayNote(path_note);
	}

	public void stop() {
		// my_note.StopNote();

	}

	public void pause() {

	}
	
	public void remove_note(){
		try{
			this.note.delete();
			Intent intent = new Intent(this, Home.class);
			startActivity(intent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
}
