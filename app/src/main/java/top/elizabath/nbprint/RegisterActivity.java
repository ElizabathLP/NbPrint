package top.elizabath.nbprint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import top.elizabath.nbprint.Tools.ProgressButton;
import top.elizabath.nbprint.Tools.SMSTools;
import top.elizabath.nbprint.Tools.Tos;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TimerTask tt;
    private Timer tm;
    private int TIME = 60;//倒计时60s这里应该多设置些因为mob后台需要60s,我们前端会有差异的建议设置90，100或者120
    public String country="86";//这是中国区号，如果需要其他国家列表，可以使用getSupportedCountries();获得国家区号
    private String phone;
    private static final int CODE_REPEAT = 1; //重新发送

    private ProgressButton register_btn;//btn_sure验证验证码
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private Button Verification_code_num;//btn_check发送验证码
    private String Rg_username,Rg_password,Rg_repassword,Rg_telephonenum,Verification_code;
    TextInputLayout Rg_telephonenumWrapper,Rg_usernameWrapper,Rg_passwordWrapper,Rg_repasswordWrapper,Verification_codeWrapper;


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }


    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                Verification_code_num.setEnabled(true);
                register_btn.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                Verification_code_num.setText("重新发送验证码");
            }else {
                Verification_code_num.setText(TIME + "重新发送验证码");
            }
        }
    };
    //回调
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                    toast("验证成功");
                    doRegister(Rg_username, Rg_password);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                    toast("获取验证码成功");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                ((Throwable)data).printStackTrace();
                String str = data.toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        register_btn.stopAnim(new ProgressButton.OnStopAnim() {
                            @Override
                            public void Stop() {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }, 1000L);
                toast(str);
            }
        }
    };
    //吐司的一个小方法
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //配置app你的 SMSSDK.initSDK(this, "您的appkey", "您的appsecret");
//        SMSSDK.initSDK(this, "23fb17747f480", "dcef3940aaf9959d465eca53e38baf25");
        SMSSDK.registerEventHandler(eh); //注册短信回调（记得销毁，避免泄露内存）
        initView();

    }
    private void initView() {

        /********/
        register_btn = (ProgressButton) findViewById(R.id.register_btn);
        register_btn.setBgColor(Color.parseColor("#009688"));
        register_btn.setTextColor(Color.WHITE);
        register_btn.setProColor(Color.WHITE);
        register_btn.setButtonText("提交");
        /*********/
        final TextInputLayout Rg_usernameWrapper = (TextInputLayout) findViewById(R.id.Rg_usernameWrapper);
        final TextInputLayout Rg_passwordWrapper = (TextInputLayout) findViewById(R.id.Rg_passwordWrapper);
        final TextInputLayout Rg_repasswordWrapper = (TextInputLayout) findViewById(R.id.Rg_repasswordWrapper);
        final TextInputLayout Verification_codeWrapper = (TextInputLayout) findViewById(R.id.Verification_codeWrapper);
        final TextInputLayout Rg_telephonenumWrapper = (TextInputLayout) findViewById(R.id.Rg_telephonenumWrapper);
        Verification_code_num = findViewById(R.id.Verification_code_num);//获取验证码Button
        Rg_usernameWrapper.setHint("用户名");
        Rg_passwordWrapper.setHint("请输入密码");
        Rg_repasswordWrapper.setHint("请再次输入密码");
        Rg_telephonenumWrapper.setHint("请输入手机号");
        Verification_codeWrapper.setHint("验证码");
        Verification_code_num.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Verification_code_num:
                Rg_telephonenumWrapper = (TextInputLayout) findViewById(R.id.Rg_telephonenumWrapper);
                phone = Rg_telephonenumWrapper.getEditText().getText().toString().trim().replaceAll("/s","");
                if (!TextUtils.isEmpty(phone)) {
                    //定义需要匹配的正则表达式的规则
                    String REGEX_MOBILE_SIMPLE =  "[1][358]\\d{9}";
                    //把正则表达式的规则编译成模板
                    Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                    //把需要匹配的字符给模板匹配，获得匹配器
                    Matcher matcher = pattern.matcher(phone);
                    // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                    if (matcher.find()) {//匹配手机号是否存在
                        alterWarning();
                    } else {
                        toast("手机号格式错误");
                    }
                } else {
                    toast("请先输入手机号");
                }
                break;
            case R.id.register_btn:
                //获得用户输入的验证码
                hideKeyboard();
                TextInputLayout Rg_usernameWrapper = (TextInputLayout) findViewById(R.id.Rg_usernameWrapper);
                TextInputLayout Rg_passwordWrapper = (TextInputLayout) findViewById(R.id.Rg_passwordWrapper);
                TextInputLayout Rg_repasswordWrapper = (TextInputLayout) findViewById(R.id.Rg_repasswordWrapper);
                TextInputLayout Verification_codeWrapper = (TextInputLayout) findViewById(R.id.Verification_codeWrapper);
                TextInputLayout Rg_telephonenumWrapper = (TextInputLayout) findViewById(R.id.Rg_telephonenumWrapper);
                Rg_username = Rg_usernameWrapper.getEditText().getText().toString();
                Rg_password = Rg_passwordWrapper.getEditText().getText().toString();
                Rg_repassword = Rg_repasswordWrapper.getEditText().getText().toString();
                Rg_telephonenum = Rg_telephonenumWrapper.getEditText().getText().toString();
                Verification_code = Verification_codeWrapper.getEditText().getText().toString().replaceAll("/s","");
                if (!validateEmail(Rg_username)) {
                    Rg_usernameWrapper.setError("输入的电子邮件地址无效!");
                } else if (!validatePassword(Rg_password) || !validatePassword(Rg_repassword)) {
                    Rg_passwordWrapper.setError("输入的密码无效!");
                } else if (!Rg_password.equals(Rg_repassword)) {
                    Rg_repasswordWrapper.setError("两次输入的密码不一致!");
                } else if (Rg_telephonenum.length() < 11) {
                    Rg_telephonenumWrapper.setError("电话号码输入错误!");
                } else if (TextUtils.isEmpty(Verification_code)) {//判断验证码是否为空
                    //如果用户输入的内容为空，提醒用户
                    toast("请输入验证码后再提交");
                }else{
                    SMSSDK.submitVerificationCode( country,  phone,  Verification_code);
                    //验证
                }
                break;
        }
    }
    //弹窗确认下发
    private void alterWarning() {
        // 2. 通过sdk发送短信验证
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("我们将要发送到" + phone + "验证"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                // 2. 通过sdk发送短信验证（请求获取短信验证码，在监听（eh）中返回）
                SMSSDK.getVerificationCode(country, phone);
                //做倒计时操作
                Toast.makeText(RegisterActivity.this, "已发送" + which, Toast.LENGTH_SHORT).show();
                Verification_code_num.setEnabled(false);
                register_btn.setEnabled(true);
                tm = new Timer();
                tt = new TimerTask() {
                    @Override
                    public void run() {
                        hd.sendEmptyMessage(TIME--);
                    }
                };
                tm.schedule(tt,0,1000);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this, "已取消" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
    /*登录逻辑*/
    public void doRegister(String Rg_username, String Rg_password) {
        // TODO: login procedure; not within the scope of this tutorial.

        /*加一句话：如果所有条件判断成功，则运行下方流程，否则吐司失败，目前设计阶段暂时不加*/
//
//                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();

    }
    //销毁短信注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销回调接口registerEventHandler必须和unregisterEventHandler配套使用，否则可能造成内存泄漏。
        SMSSDK.unregisterEventHandler(eh);

    }
}