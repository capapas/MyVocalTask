package fr.esgi.my_vocal_task;

import java.util.ArrayList;

import fr.esgi.record_me.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FilesAdapter extends ArrayAdapter<String> {
	private ArrayList<String> data;
	AlertDialog message;

	public FilesAdapter(Context context, int textViewResourceId,
			ArrayList<String> data) {
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
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		String name = data.get(position);
		if (data != null) {

			viewHolder.filename.setText(name);

		}
		return v;
	}

	static class ViewHolder {
		TextView filename;
	}

}
