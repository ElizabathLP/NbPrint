package top.elizabath.nbprint;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import top.elizabath.nbprint.Tools.AppConstants;
import top.elizabath.nbprint.Tools.SlideFragment;
import top.elizabath.nbprint.Tools.SpUtils;

public class AppIntroActivity extends AppIntro {
    View mView;
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(SlideFragment.newInstance(R.layout.intro1));
        addSlide(SlideFragment.newInstance(R.layout.intro2));
        addSlide(SlideFragment.newInstance(R.layout.intro3));
        addSlide(SlideFragment.newInstance(R.layout.intro4));
        setSeparatorColor(getResources().getColor(R.color.colorAccent));
        setVibrateIntensity(30);
        setFadeAnimation();
        showStatusBar(false);

//        Button button=getView().findViewById(R.id.btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Log.d("Tag","点击了Button");
//            }
//        });
        setSkipText("跳过");
        setDoneText("完成");
    }

    @Override
    public void onSkipPressed() {
        //当执行跳过动作时触发
        Intent intent = new Intent(AppIntroActivity.this, Main_activity.class);
        startActivity(intent);
        SpUtils.putBoolean(AppIntroActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onNextPressed() {
        //当执行下一步动作时触发
    }
    public View getView() {
        return mView;
    }

    @Override
    public void onDonePressed() {
        //当执行完成动作时触发
        Intent intent = new Intent(AppIntroActivity.this, Main_activity.class);
        startActivity(intent);
        SpUtils.putBoolean(AppIntroActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    public void onSlideChanged() {
        //当执行变化动作时触发
    }

}
