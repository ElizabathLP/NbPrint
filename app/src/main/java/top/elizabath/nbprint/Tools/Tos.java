package top.elizabath.nbprint.Tools;

import android.content.Context;
import android.widget.Toast;

/**
 * ated by Administrator on 2017/10/16/016.
 */

public class Tos {

    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}