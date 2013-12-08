package fr.esgi.my_vocal_task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.media.MediaPlayer;
import android.util.Log;

public class Utils {

	static MediaPlayer mediaPlayer = new MediaPlayer();

	public static long getFileDuration(File file) {
		try {
			mediaPlayer.setDataSource(file.getAbsolutePath());
			mediaPlayer.prepare();
			// Log.e("filePath", file.getAbsolutePath() + " --> " +
			// mediaPlayer.getDuration()+"");

			long fileDuration = mediaPlayer.getDuration();
			mediaPlayer.reset();
			return fileDuration;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatFileDuration(long durationMili) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		// Edit: setting the UTC time zone
		TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);

		Date date = new Date(durationMili);

		return sdf.format(date);
	}

}
