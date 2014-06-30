/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.Random;

/**
 * @author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 * @author Ready4S
 * @since 27 cze 2014 16:35:33
 */

public class ExtGridView extends GridView {

    boolean switchPos(int fView) {
        int curBlankPos = ((ImageAdapter)getAdapter()).getBlank();

        if ((fView - 1 == curBlankPos && (fView % getNumColumns()) != 0)
                || (fView + 1 == curBlankPos && (fView + 1) % getNumColumns() != 0)
                || (fView - getNumColumns() == curBlankPos)
                || (fView + getNumColumns() == curBlankPos)) {
            ((ImageAdapter)getAdapter()).switchPos(fView, curBlankPos);
            ((ImageAdapter)getAdapter()).setBlank(fView);
            return true;
        }
        return false;
    }

    public ExtGridView(Context context) {
        super(context);
    }

    public ExtGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shufflePuzzles(int steps, int desiredcols) {
        Random generator = new Random();
        int upBound = getAdapter().getCount();

        for (int i = 0; i < steps; i++) {
            int firstpiece = ((ImageAdapter)getAdapter()).getBlank();
            int side = generator.nextInt(4);
            switch (side) {
                case 0:
                    if (firstpiece - 1 >= 0 && (firstpiece % desiredcols) != 0) {
                        ((ImageAdapter)getAdapter()).setBlank(firstpiece - 1);
                        ((ImageAdapter)getAdapter()).switchPos(firstpiece, firstpiece - 1);
                    }
                    break;
                case 1:
                    if (firstpiece + 1 < upBound && (firstpiece + 1) % desiredcols != 0) {
                        ((ImageAdapter)getAdapter()).setBlank(firstpiece + 1);
                        ((ImageAdapter)getAdapter()).switchPos(firstpiece, firstpiece + 1);
                    }
                    break;
                case 2:
                    if (firstpiece - desiredcols >= 0) {
                        ((ImageAdapter)getAdapter()).setBlank(firstpiece - desiredcols);
                        ((ImageAdapter)getAdapter())
                                .switchPos(firstpiece, firstpiece - desiredcols);
                    }
                    break;
                case 3:
                    if (firstpiece + desiredcols < upBound) {
                        ((ImageAdapter)getAdapter()).setBlank(firstpiece + desiredcols);
                        ((ImageAdapter)getAdapter())
                                .switchPos(firstpiece, firstpiece + desiredcols);
                    }
                    break;

            }

        }

    }

    public boolean isOrdered() {
        return ((ImageAdapter)getAdapter()).isOrdered();
    }

}
