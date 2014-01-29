package fr.esgi.my_vocal_task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.esgi.my_vocal_task.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends Activity implements OnItemClickListener {
	private String path_note = "";
	private ArrayList<File> liste_;
	FilesAdapter fa;
	AlertDialog message;
	final String state = Environment.getExternalStorageState();
	private static final String AUDIO_RECORDER_FOLDER = "MonDictaphone";
	private boolean order_date_asc = false;
	private boolean order_size_asc = false;
	private boolean order_duration_asc = false;
	private boolean order_name_asc = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		liste_ = new ArrayList<File>();
		getAllFilesOfDir(Environment.getExternalStorageDirectory());
		fa = new FilesAdapter(this, R.layout.my_files, liste_);
		((ListView) findViewById(R.id.listView1)).setAdapter(fa);
		((ListView) findViewById(R.id.listView1)).setOnItemClickListener(this);
	}

	// Cette fonction appelle les traitements associées au menu en haut
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add:
			go_to_my_recorder();
			Log.e("actionSelected", "Ajouter une note");
			return true;
		case R.id.action_sort_date:
			sort_by_date();
			Log.e("itemSelected", "Trier par date");
			return true;
		case R.id.action_sort_size:
			sort_by_size();
			Log.e("itemSelected", "Trier par taille");
			return true;
		case R.id.action_sort_duration:
			sort_by_duration();
			Log.e("itemSelected", "Trier par durée");
			return true;
		case R.id.action_sort_name:
			sort_by_name();
			Log.e("itemSelected", "Trier par nom");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void Get_Param(String Ma_note) {

		Ma_note = this.path_note;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		TextView path = (TextView) arg1.findViewById(R.id.txt_filename);
		path_note = Environment.getExternalStorageDirectory() + "/"
				+ AUDIO_RECORDER_FOLDER + "/" + path.getText().toString();
		to_show_one_note(path_note);
	}

	private void to_show_one_note(String file) {

		Intent intent = new Intent(this, ShowOneNote.class);
		intent.putExtra("NOTE", file + ".mp4");
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	private void go_to_my_recorder() {

		Intent intent = new Intent(this, My_Recorder.class);
		startActivity(intent);
	}

	private void getAllFilesOfDir(File directory) {
		// Log.e("Record","Directory: " + directory.getAbsolutePath() + "\n");
		File f = new File(directory + "/" + AUDIO_RECORDER_FOLDER);
		final File[] files = f.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file != null) {
					if (file.isDirectory()) { // it is a folder...
						getAllFilesOfDir(file);
					} else { // it is a file...
						if (file.getAbsolutePath().endsWith(".mp3")
								|| file.getAbsolutePath().endsWith(".MP3")
								|| file.getAbsolutePath().endsWith(".mp4")
								|| file.getAbsolutePath().endsWith(".MP4")) {
							// Log.e("Record", "File: " + file.getName() +
							// "\n");
							// Log.e("Record", "File: " + file.getAbsolutePath()
							// + "\n");
							Log.e("Record", "File_date: " + file.lastModified()
									+ "\n");

							liste_.add(file);
						}
					}
				}
			}
		}
	}

	private void sort_by_date() {
		Collections.sort(liste_, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if (order_date_asc) {
					return (int) (lhs.lastModified() - rhs.lastModified());
				} else {
					return (int) (rhs.lastModified() - lhs.lastModified());
				}
			}
		});
		order_date_asc = !order_date_asc;
		fa.notifyDataSetChanged();
	}

	private void sort_by_size() {
		Collections.sort(liste_, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if (order_size_asc) {
					return (int) (lhs.length() - rhs.length());
				} else {
					return (int) (rhs.length() - lhs.length());
				}
			}
		});
		order_size_asc = !order_size_asc;
		fa.notifyDataSetChanged();
	}

	private void sort_by_duration() {
		Collections.sort(liste_, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if (order_duration_asc) {
					return (int) (Utils.getFileDuration(lhs) - Utils
							.getFileDuration(rhs));
				} else {
					return (int) (Utils.getFileDuration(rhs) - Utils
							.getFileDuration(lhs));
				}
			}
		});
		order_duration_asc = !order_duration_asc;
		fa.notifyDataSetChanged();
	}

	private void sort_by_name() {
		Collections.sort(liste_, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if (order_name_asc) {
					return (lhs.getName().compareTo(rhs.getName()));
				} else {
					return rhs.getName().compareTo(lhs.getName());
				}
			}
		});
		order_name_asc = !order_name_asc;
		fa.notifyDataSetChanged();
	}
}
