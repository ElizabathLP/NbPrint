package top.elizabath.nbprint.Tools;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import top.elizabath.nbprint.RegisterActivity;

/**
 * Created by Elizabath on 2018/1/29.
 */

public class SMSTools {
    private static Context contexts;
    public static void sendcode(Context context, String country, String phone){
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        contexts=context;
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {

                    Looper.prepare();
                    Log.d("testmsg", "发送成功");// TODO 处理成功得到验证码的结果
                    Tos.showToast(contexts,"验证码发送成功，请查收！");
                    Looper.loop();
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                } else {
                    Looper.prepare();
//                    Log.d("testmsg", "发送失败");// TODO 处理错误的结果
                    Tos.showToast(contexts,"验证码发送失败，请检查您的网络设置！");
                    Looper.loop();
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

}
