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
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 *@author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 *@author Ready4S
 *@since 27 cze 2014 16:32:43
 */
/**
 * @author Denis Migol
 */

public class PuzzleActivity extends Activity {

    private ArrayList<Bitmap> bPieces;

    private View.OnTouchListener mListener;

    ExtGridView egridview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlelayout);
        Log.i("TESTOWY", "Puzzle activity started");
        int desiredCols = 5;
        int desiredRows = 5;

        Bitmap whole = null;

        Bundle b = getIntent().getExtras();

        if (b != null) {
            desiredCols = b.getInt("Columns");
            desiredRows = b.getInt("Rows");
            String path = b.getString("Path");
            whole = BitmapFactory.decodeFile(path);
            Log.i("TESTOWY", "bundle nie NULL" + path);
        } else {
            // whole = BitmapFactory.decodeResource(getResources(),
            // R.drawable.sa);
            Log.i("TESTOWY", "bundle NULL");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        bPieces = new ArrayList<Bitmap>();

        egridview = (ExtGridView)findViewById(R.id.gridview);
        egridview.setNumColumns(desiredCols);
        egridview.setColumnWidth(width / desiredCols);

        // Bitmap whole = BitmapFactory.decodeResource(getResources(),
        // R.drawable.sa);
        createPiecesArray(whole, (float)width / whole.getWidth(), desiredCols, desiredRows);

        mListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    int i = egridview.getPositionForView(v);
                    egridview.switchPos(i);
                    return true;
                }
                return false;

            }
        };

        egridview.setAdapter(new ImageAdapter(this, mListener, bPieces));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.newgame) {
            Intent intent = new Intent(getApplicationContext(), NewGameOptionsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    void createPiecesArray(Bitmap whole, float k, int cols, int rows) {

        int outWidth = (int)(k * whole.getWidth());
        int outHeight = (int)(k * whole.getHeight());

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(whole, outWidth, outHeight, false);

        int chopWidth = outWidth / cols;
        int chopHeight = outHeight / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bPieces.add(Bitmap.createBitmap(scaledBitmap, j * chopWidth, i * chopHeight,
                        chopWidth, chopHeight));
            }
        }

        Bitmap blackBmp = Bitmap.createBitmap(chopWidth, chopHeight, Bitmap.Config.ARGB_8888);
        blackBmp.eraseColor(Color.BLACK);
        bPieces.set(0, blackBmp);

    }
}
