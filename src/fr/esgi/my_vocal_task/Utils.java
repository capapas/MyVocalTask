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

	public static MediaPlayer  getMediaPlayer(File file) {
		try {
			mediaPlayer.setDataSource(file.getAbsolutePath());
			mediaPlayer.prepare();
			return mediaPlayer;
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	
	public static String noteName(File file) {
		return file.getName().substring(0,
				file.getName().lastIndexOf('.'));
	}
	
	public static String getLastModificationDate(File file) {
		Date dateModified = new Date(file.lastModified());
		SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		return df2.format(dateModified);
	}
	
	public static String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";
 
        // Convert total duration into time
           int hours = (int)( milliseconds / (1000*60*60));
           int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
           int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
           // Add hours if there
           if(hours > 0){
               finalTimerString = hours + ":";
           }
 
           // Prepending 0 to seconds if it is one digit
           if(seconds < 10){
               secondsString = "0" + seconds;
           }else{
               secondsString = "" + seconds;}
 
           finalTimerString = finalTimerString + minutes + ":" + secondsString;
 
        // return timer string
        return finalTimerString;
    }

	public static int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;
 
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
 
        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;
 
        // return percentage
        return percentage.intValue();
    }
	
	public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);
 
        // return current duration in milliseconds
        return currentDuration * 1000;
    }
}
