/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import puzzle.game.ImageDownloadTask.ImageLoaderListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 * @author Ready4S
 * @since 27 cze 2014 16:32:43
 */

public class PuzzleActivity extends Activity {

    private ArrayList<Bitmap> bPieces;

    private View.OnTouchListener mListener;

    private static ExtGridView egridview;

    private static Bitmap whole = null;

    private static int desiredCols = 5;

    private static int desiredRows = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlelayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final Bundle instancestae = savedInstanceState;

        bPieces = new ArrayList<Bitmap>();
        egridview = (ExtGridView)findViewById(R.id.gridview);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            desiredCols = b.getInt("Columns");
            desiredRows = b.getInt("Rows");
            String path = b.getString("Path");
            egridview.setNumColumns(desiredCols);
            egridview.setColumnWidth(width / desiredCols);
            if (b.getString("TYPE").equals("FILE")) {
                whole = loadfromFile(path);
                createPiecesArray(whole, (float)width / whole.getWidth(), desiredCols, desiredRows);
                setListener();
                egridview.setAdapter(new ImageAdapter(this, mListener, bPieces));
                if (savedInstanceState != null)
                    ((ImageAdapter)egridview.getAdapter()).positonPieces(savedInstanceState
                            .getIntegerArrayList("positions"));
                else
                    egridview.shufflePuzzles(desiredCols * desiredCols * desiredCols, desiredCols);

                whole = null;

            } else if (b.getString("TYPE").equals("URL")) {
                ImageDownloadTask mDownloadTask = new ImageDownloadTask(new ImageLoaderListener() {

                    @Override
                    public void onImageDownloaded(Bitmap bmp) {
                        whole = bmp;
                        createPiecesArray(whole, (float)width / whole.getWidth(), desiredCols,
                                desiredRows);
                        setListener();
                        egridview.setAdapter(new ImageAdapter(getApplicationContext(), mListener,
                                bPieces));
                        if (instancestae != null) {
                            ((ImageAdapter)egridview.getAdapter()).positonPieces(instancestae
                                    .getIntegerArrayList("positions"));
                        } else
                            egridview.shufflePuzzles(desiredCols * desiredCols * desiredCols,
                                    desiredCols);
                        whole = null;

                    }
                });
                mDownloadTask.execute(path);
            }

        } else {
            finish();
        }

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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        List<Integer> list = ((ImageAdapter)egridview.getAdapter()).getPosList();
        savedInstanceState.putIntegerArrayList("positions", (ArrayList<Integer>)list);
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

    void gameFinished() {
        Toast.makeText(getApplicationContext(), "Congratulations!!!", Toast.LENGTH_SHORT).show();

    }

    private Bitmap loadfromFile(String filename) {
        return BitmapFactory.decodeFile(filename);

    }

    private void setListener() {
        mListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    int i = egridview.getPositionForView(v);
                    egridview.switchPos(i);
                    if (egridview.isOrdered())
                        gameFinished();
                    return true;
                }
                return false;

            }
        };
    }

}
