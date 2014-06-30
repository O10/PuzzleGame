/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 *@author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 *@author Ready4S
 *@since 27 cze 2014 16:37:14
 */
/**
 * @author Denis Migol
 */
public class NewGameOptionsActivity extends Activity {

    final int RESULT_LOAD_IMAGE = 11;

    EditText rowsNumber;

    EditText colsNumber;

    EditText urlPath;

    String picturePath;

    TableLayout tableLayout;

    /**
     * 
     */
    public NewGameOptionsActivity() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionslayout);
        picturePath = null;

        Button chooseImageB = (Button)findViewById(R.id.chooseimageb);
        Button startB = (Button)findViewById(R.id.startbutton);
        tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        urlPath = (EditText)findViewById(R.id.urladd);

        rowsNumber = (EditText)findViewById(R.id.rowsvalue);
        colsNumber = (EditText)findViewById(R.id.columnsvalue);

        chooseImageB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        startB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((picturePath == null && urlPath.getText().length() == 0)
                        || rowsNumber.getText().toString().matches("")
                        || colsNumber.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Fill all the fields first",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                Bundle b = new Bundle();

                b.putInt("Rows", Integer.parseInt((rowsNumber.getText().toString())));
                b.putInt("Columns", Integer.parseInt((colsNumber.getText().toString())));
                if (urlPath.getText().length() != 0) {
                    b.putString("Path", urlPath.getText().toString());
                    b.putString("TYPE", "URL");
                } else {
                    b.putString("TYPE", "FILE");
                    b.putString("Path", picturePath);
                }

                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {
                MediaStore.Images.Media.DATA
            };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null,
                    null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            cursor.close();

            Bitmap tempBitmap = BitmapFactory.decodeFile(picturePath);
            // previewImage.setImageBitmap(tempBitmap);

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(getApplicationContext());
            // textView.setLayoutParams(new
            // TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
            // TableRow.LayoutParams.WRAP_CONTENT));

            textView.setText("Preview");

            textView.setTextAppearance(getApplicationContext(),
                    android.R.style.TextAppearance_Large);
            textView.setGravity(Gravity.CENTER);

            tableRow.addView(textView);

            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageBitmap(Bitmap.createScaledBitmap(tempBitmap, 100, 500, false));

            tableRow.addView(imageView);

            tableLayout.addView(tableRow);

        }
    }
}
