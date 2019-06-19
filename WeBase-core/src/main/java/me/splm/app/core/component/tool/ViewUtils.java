package me.splm.app.core.component.tool;

import android.graphics.Rect;
import android.view.View;

public class ViewUtils {
    public static boolean isCover(View view){
        boolean isCover=false;
        Rect rect=new Rect();
        isCover=view.getGlobalVisibleRect(rect);
        if (isCover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !isCover;
            }
        }
        return true;
    }
}
