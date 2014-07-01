/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 * @author Ready4S
 * @since 30 cze 2014 13:14:22
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Void> {

    /**
     * 
     */
    Bitmap image;

    ImageLoaderListener listener;

    Activity activity;

    public interface ImageLoaderListener {

        void onImageDownloaded(Bitmap bmp);

    }

    public ImageDownloadTask(Activity callingact, ImageLoaderListener mlistener) {
        listener = mlistener;
        activity = callingact;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            image = BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (image == null && activity != null) {
            super.onPostExecute(result);
            Toast.makeText(activity, "Problem with the URL", Toast.LENGTH_LONG).show();
            SystemClock.sleep(1000);
            Intent it = new Intent(activity.getApplicationContext(), NewGameOptionsActivity.class);
            activity.startActivity(it);
            activity.finish();

        } else if (listener != null) {
            listener.onImageDownloaded(image);
            super.onPostExecute(result);
        }
    }
}
