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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 * @author Ready4S
 * @since 27 cze 2014 16:34:29
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private int curBlankPos;

    private List<Bitmap> bList;

    private List<Integer> positions;

    private View.OnTouchListener mListener;

    public ImageAdapter(Context c, View.OnTouchListener uListener, List<Bitmap> bmaps) {
        mListener = uListener;
        mContext = c;
        bList = bmaps;
        positions = new ArrayList<Integer>();
        curBlankPos = 0;
        for (int i = 0; i < bList.size(); i++) {
            positions.add(i);
        }

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

        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setOnTouchListener(mListener);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        imageView.setImageBitmap(bList.get(position));
        return imageView;
    }

    public void switchPos(int pos, int pos2) {

        Collections.swap(bList, pos, pos2);
        Collections.swap(positions, pos, pos2);
        notifyDataSetChanged();
    }

    public int getBlank() {
        return curBlankPos;
    }

    public void setBlank(int newb) {
        curBlankPos = newb;
    }

    public boolean isOrdered() {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i) != i) {
                return false;
            }
        }
        return true;
    }

    List<Integer> getPosList() {
        return positions;
    }

    public void positonPieces(List<Integer> newpositions) {
        for (int i = 0; i < bList.size(); i++) {
            if (newpositions.get(i) == 0)
                setBlank(i);
            if (newpositions.get(i) != positions.get(i)) {
                int curpos = positions.indexOf(newpositions.get(i));
                switchPos(curpos, i);

            }
        }

    }
}
