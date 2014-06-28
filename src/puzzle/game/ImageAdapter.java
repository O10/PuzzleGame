/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

/**
 *@author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 *@author Ready4S
 *@since 27 cze 2014 16:34:29
 */
/**
 * @author Denis Migol
 */

public class ImageAdapter extends BaseAdapter {

    // private static int width;

    // private static int height;

    private Context mContext;

    private List<Bitmap> bList;

    private View.OnTouchListener mListener;

    public ImageAdapter(Context c, View.OnTouchListener uListener, List<Bitmap> bmaps) {
        mListener = uListener;
        mContext = c;
        this.bList = bmaps;

    }

    @Override
    public int getCount() {
        return bList.size();
    }

    @Override
    public Object getItem(int position) {
        return bList.get(position);
    }

    // Will get called to provide the ID that
    // is passed to OnItemClickListener.onItemClick()
    @Override
    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView)convertView;
        // Log.i("TESTOWY", "getView call");

        // if convertView's not recycled, initialize some attributes
        if (imageView == null) {
            imageView = new ImageView(mContext);
            // imageView.setLayoutParams(new GridView.LayoutParams(width,
            // height));
            imageView.setOnTouchListener(mListener);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        imageView.setImageBitmap(bList.get(position));
        // imageView.setImageResource(mThumbIds.get(position));
        return imageView;
    }

    void switchPos(int pos, int pos2) {

        Collections.swap(bList, pos, pos2);
        notifyDataSetChanged();
    }
}
