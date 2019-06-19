package me.splm.app.core.component.ui;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;
    public static void show(Context context,String msg){
        if(toast!=null){
            toast.cancel();
        }
        toast=Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    /*public static void showSN(Activity activity, String message){
        View view=activity.getWindow().getDecorView();
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("关闭", null).show();
    }*/
}
