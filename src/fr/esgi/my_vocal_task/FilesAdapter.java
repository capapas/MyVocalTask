package fr.esgi.my_vocal_task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.esgi.record_me.R;

public class FilesAdapter extends ArrayAdapter<File> {
	private ArrayList<File> data;
	AlertDialog message;

	public FilesAdapter(Context context, int textViewResourceId,
			ArrayList<File> data) {
		super(context, textViewResourceId, data);
		this.data = data;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder viewHolder;

		if (v == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.my_files, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.filename = (TextView) v.findViewById(R.id.txt_filename);
			viewHolder.filedate = (TextView) v.findViewById(R.id.txt_date);
			viewHolder.filesize = (TextView) v.findViewById(R.id.txt_size);
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		File file = data.get(position);
		if (data != null) {

			viewHolder.filename.setText(file.getName());
			float fileSize = (file.length()/(1024*1024));
//			Date dateModified = new Date(file.lastModified());
//			DateFormat dateFormat = new DateFormat();
			viewHolder.filedate.setText("Modifié le : " + file.lastModified()+"");
			viewHolder.filesize.setText(fileSize+"Mb");
		}
		return v;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	static class ViewHolder {
		TextView filename;
		TextView filedate;
		TextView filesize;
	}

}
