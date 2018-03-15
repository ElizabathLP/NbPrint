package top.elizabath.nbprint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.elizabath.nbprint.Tools.ProgressButton;
import top.elizabath.nbprint.Tools.Tos;

public class LoginActivity extends AppCompatActivity {

    private ProgressButton btn_login,btn_register;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private ImageButton qq_login, wechat_login, weibo_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /****对应的两个个性化按钮的个性设置****/
        btn_login=(ProgressButton)findViewById(R.id.btn_login);
        btn_login.setBgColor(Color.parseColor("#00695C"));
        btn_login.setTextColor(Color.WHITE);
        btn_login.setProColor(Color.WHITE);
        btn_login.setButtonText("登陆");
        btn_register=(ProgressButton)findViewById(R.id.btn_register);
        btn_register.setBgColor(Color.parseColor("#00695C"));
        btn_register.setTextColor(Color.WHITE);
        btn_register.setProColor(Color.WHITE);
        btn_register.setButtonText("注册");
        /*********/
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("用户名");
        passwordWrapper.setHint("密码");
//        btn=findViewById(R.id.btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // STUB
                hideKeyboard();
                String username = usernameWrapper.getEditText().getText().toString();
                String password = passwordWrapper.getEditText().getText().toString();
                if (!validateEmail(username)) {
                    usernameWrapper.setError("不是有效的电子邮件地址!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("不是有效的密码!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin(username,password);
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register.startAnim();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_register.stopAnim(new ProgressButton.OnStopAnim() {
                            @Override
                            public void Stop() {
                                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                            }
                        });
                    }
                },1000L);


            }
        });
        /**************第三方登录按钮*****************/
        qq_login = findViewById(R.id.qq_login);
        weibo_login = findViewById(R.id.weibo_login);
        wechat_login = findViewById(R.id.wechat_login);

        qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tos.showToast(LoginActivity.this, "QQ登录");
                Tos.showToast(LoginActivity.this, "功能还未开发");
            }
        });
        wechat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tos.showToast(LoginActivity.this, "wechat登录");
                Tos.showToast(LoginActivity.this, "功能还未开发");
            }
        });
        weibo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tos.showToast(LoginActivity.this, "weibo登录");
                Tos.showToast(LoginActivity.this, "功能还未开发");
            }
        });

        /******************************/
    }
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
    public void doLogin(String username,String password) {
        btn_login.startAnim();
        Message m=mHandler.obtainMessage();
        mHandler.sendMessageDelayed(m,1500);
        Toast.makeText(getApplicationContext(), "OK! I'm performing login.\n"+"your username is:"+username+'\n'+"your passwd is:"+password+'\n', Toast.LENGTH_SHORT).show();
        // TODO: login procedure; not within the scope of this tutorial.
    }
    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            btn_login.stopAnim(new ProgressButton.OnStopAnim() {
                @Override
                public void Stop() {
                    Intent i=new Intent();
                    i.setClass(LoginActivity.this,Main_activity.class);
                    startActivity(i);
                }
            });

        }
    };
}
