package top.elizabath.nbprint;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import top.elizabath.nbprint.Base.Base_Activity;
import top.elizabath.nbprint.Tools.AppConstants;
import top.elizabath.nbprint.Tools.SpUtils;
import top.elizabath.nbprint.Tools.Tos;

public class Main_activity extends Base_Activity {
//    DrawerLayout mDrawerLayout;
//
//    //ViewGroup mDrawerLayout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//
//    /**/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                //将侧边栏顶Init_View部延伸至status bar
//                mDrawerLayout = findViewById(R.id.drawer_layout);
//                mDrawerLayout.setFitsSystemWindows(true);
//                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
//                mDrawerLayout.setClipToPadding(false);
//            }
//        }
//        /**/
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
////         如果是第一次启动，则先进入功能引导页
//        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
//        Log.d("TAG", "onCreate: " + isFirstOpen);
//        if (!isFirstOpen) {
//            Intent intent = new Intent(this, AppIntroActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
////         如果是第一次启动，则先进入功能引导页
//
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = iteAppCompatActivity implements NavigationView.OnNavigationItemSelectedListenerm.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_change) {
//            startActivity(new Intent(Main2Activity.this, LoginActivity.class));
//        } else if (id == R.id.nav_send) {
//            startActivity(new Intent(Main2Activity.this, LoginActivity.class));
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    public void Init_View(){
        setContentView(R.layout.activity_main);
        drawer = $(R.id.drawer_layout);
        toolbar = $(R.id.toolbar);
        //         如果是第一次启动，则先进入功能引导页
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        if (!isFirstOpen) {
            Intent intent = new Intent(this, AppIntroActivity.class);
            startActivity(intent);
            finish();
            return;
        }
//         如果是第一次启动，则先进入功能引导页
    }



    @Override
    protected void Menu_(int id) {
        if (id == R.id.main_act) {
            Tos.showToast(Main_activity.this,"首页"); // Handle the camera action
        } else if (id == R.id.startpr_act) {
            Tos.showToast(Main_activity.this,"开始打印");
        } else if (id == R.id.ordmag_act) {
            Tos.showToast(Main_activity.this,"订单管理");
        } else if (id == R.id.setting_act) {
            Tos.showToast(Main_activity.this,"设置页面");
        } else if (id == R.id.share_act) {
            Tos.showToast(Main_activity.this,"分享界面");
        } else if (id == R.id.chagusr_act) {
            startActivity(new Intent(Main_activity.this, LoginActivity.class));
        } else if (id == R.id.exit_act) {
            startActivity(new Intent(Main_activity.this, LoginActivity.class));
        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
    }

}
