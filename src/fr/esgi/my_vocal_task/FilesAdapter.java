package fr.esgi.my_vocal_task;

import java.io.File;
import java.text.SimpleDateFormat;
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
import fr.esgi.my_vocal_task.R;

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

			viewHolder.filename.setText(file.getName().substring(0, file.getName().lastIndexOf('.')));
			
			Date dateModified = new Date(file.lastModified());
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
	        String dateText = df2.format(dateModified);
	        
	        long fileSizeByte = file.length();
	        double fileSize = 0.00;
	        String textFileSize = "0o";
	        if(fileSizeByte < 1024) {
	        	textFileSize = fileSizeByte+"o";
	        } else if (fileSizeByte < (1024*1024)) {
				fileSize = fileSizeByte/1024;
				textFileSize = fileSize+"Ko";
			} else if (fileSizeByte < (1024*1024*1024)) {
				fileSize = fileSizeByte/(1024*1024);
				textFileSize = fileSize+"Mo";
			}
	        fileSize = fileSizeByte/(1024*1024);
	        //textFileSize = fileSize < 1 ? (fileSize*1024)+"Ko" : fileSize+"Mo";
			
			viewHolder.filedate.setText("Modifi� le : " + dateText);
			viewHolder.filesize.setText(textFileSize);
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
