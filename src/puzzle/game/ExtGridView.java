/**
 * Copyright (C) 2014 Android Commons
 * 
 * http://www.androidcommons.com/
 */

package puzzle.game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *@author Aleksander Wojcik Ready4S aleksander.k.wojcik@gmail.com
 *@author Ready4S
 *@since 27 cze 2014 16:35:33
 */
/**
 * @author Denis Migol
 */

public class ExtGridView extends GridView {
    private int curBlankPos;

    boolean switchPos(int fView) {
        if (fView - 1 == curBlankPos && (fView % getNumColumns()) != 0) {
            ((ImageAdapter)getAdapter()).switchPos(fView, curBlankPos);
            curBlankPos = fView;
            return true;
        }

        if (fView + 1 == curBlankPos && (fView + 1) % getNumColumns() != 0) {
            ((ImageAdapter)getAdapter()).switchPos(fView, curBlankPos);
            curBlankPos = fView;
            return true;
        }

        if (fView - getNumColumns() == curBlankPos) {
            ((ImageAdapter)getAdapter()).switchPos(fView, curBlankPos);
            curBlankPos = fView;
            return true;
        }
        if (fView + getNumColumns() == curBlankPos) {
            ((ImageAdapter)getAdapter()).switchPos(fView, curBlankPos);
            curBlankPos = fView;
            return true;
        }
        return false;
    }

    public ExtGridView(Context context) {
        super(context);
        curBlankPos = 0;
        // TODO Auto-generated constructor stub
    }

    public ExtGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        curBlankPos = 0;
        // TODO Auto-generated constructor stub
    }

    public ExtGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        curBlankPos = 0;
        // TODO Auto-generated constructor stub
    }

}
