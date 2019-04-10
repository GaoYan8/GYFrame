package com.gy.gyframe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.gyframe.qrcode.QRCodeActivity;
import com.gy.gyframe.rationale.InstallRationale;
import com.gy.gyframe.rationale.OverlayRationale;
import com.gy.gyframe.rationale.RuntimeRationale;
import com.gy.gylibrary.badgeview.Badge;
import com.gy.gylibrary.badgeview.QBadgeView;
import com.gy.gylibrary.image.ImageLoaderUtils;
import com.gy.gylibrary.lpopupmenu.MenuItem;
import com.gy.gylibrary.lpopupmenu.TopRightMenu;
import com.gy.gylibrary.permission.Action;
import com.gy.gylibrary.permission.AndPermission;
import com.gy.gylibrary.permission.Permission;
import com.gy.gylibrary.permission.Rationale;
import com.gy.gylibrary.permission.RequestExecutor;
import com.gy.gylibrary.permission.Setting;
import com.gy.gylibrary.view.LARBAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gy.gyframe.Main1Activity.FLAG_GUIDE_DICTIONARY_BOUNCE_EFFACT;

public class MainActivity extends AppCompatActivity {
    private List a;

    //确认退出app
    private LARBAlertDialog exitDialog;

    @BindView(R.id.mMenuIV)
    TextView mMenuIV;
    private TopRightMenu mRightTopMenu;

    @BindView(R.id.iv_img)
    ImageView iv_img;

    @BindView(R.id.bt_quanxian)
    Button bt_quanxian;

    @BindView(R.id.bt_qrCode)
    Button bt_qrCode;

    @BindView(R.id.bt_agentweb)
    Button bt_agentweb;


    private QBadgeView qBadgeView;


    @OnClick({
            R.id.bt_quanxian,
            R.id.bt_qrCode,
            R.id.bt_agentweb
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_quanxian:

                a.size();

                //requestPermission(Permission.CAMERA);
                break;
            case R.id.bt_qrCode:
                startActivity(new Intent(this, QRCodeActivity.class));
                break;

            case R.id.bt_agentweb:
                //startActivity(new Intent(this, CommonActivity.class).putExtra(CommonActivity.TYPE_KEY, FLAG_GUIDE_DICTIONARY_BOUNCE_EFFACT));


                startActivity(new Intent(this, Main1Activity.class));
                break;
        }
    }


    protected void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mRightTopMenu.showAsDropDown(mMenuIV, -380, 40);
                exitDialog.show();
            }
        });


        ImageLoaderUtils.getInstance().displayUserHead(this,
                "http://a.hiphotos.baidu.com/image/pic/item/9f510fb30f2442a7c9f10326dc43ad4bd01302b1.jpg",
                iv_img);


        AC ac = new AC();
        ac.setI(1);
        ac.setName("ac");

        AC ac1 = new AC();
        ac1.setI(2);
        ac1.setName("ac1");

        AC ac2 = new AC();
        ac2.setI(2);
        ac2.setName("ac2");
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(0, "2016年10月", ac));
        menuItems.add(new MenuItem(0, "2016年19月", ac1));
        menuItems.add(new MenuItem(0, "2016年18月", ac2));

        if (mRightTopMenu == null) {
            mRightTopMenu = new TopRightMenu.Builder(MainActivity.this)
//                            .windowHeight(480)     //当菜单数量大于3个时，为wrap_content,反之取默认高度320
//                        .windowWidth(320)      //默认宽度wrap_content
                    .dimBackground(true)           //背景变暗，默认为true
                    .needAnimationStyle(true)   //显示动画，默认为true
                    .animationStyle(R.style.RTM_ANIM_STYLE)  //默认为R.style.RTM_ANIM_STYLE
                    .addMenuList(menuItems)
                    .addMenuItem(new MenuItem(0, "2016年1"))

                    .setGravity(Gravity.CENTER)
                    .setShowDividingLine(true)
                    /*.onMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                        @Override
                        public void onMenuItemClick(View view, int position) {
                            MenuItem aaa = menuItems.get(position);

                            Toast.makeText(MainActivity.this, aaa.getObj() + "   点击菜单:" + position, Toast.LENGTH_SHORT).show();
                        }
                    })*/.build();
        }


        exitDialog = new LARBAlertDialog(this).builder().setMsg("您确认要退出当前账号吗?")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).setTitleTextColor(0, 0, R.color.colorAccent);


        qBadgeView = new QBadgeView(this);
        qBadgeView.bindTarget(bt_quanxian);
        qBadgeView.setBadgeText("");
        qBadgeView.setGravityOffset(0, 12, true);
        qBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

            }
        });

    }


    /**
     * Request permissions.
     */
    private void requestPermission(final String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, final RequestExecutor executor) {
                        List<String> permissionNames = Permission.transformText(context, permissions);
                        String message = context.getString(R.string.message_permission_rationale, TextUtils.join("\n", permissionNames));

                        new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setTitle(R.string.title_dialog)
                                .setMessage(message)
                                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        executor.execute();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        executor.cancel();
                                    }
                                })
                                .show();
                    }
                })
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        toast(R.string.successfully);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        toast(R.string.failure);
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            showSettingDialog(MainActivity.this, permissions);
                        }
                    }
                })
                .start();
    }


    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new android.support.v7.app.AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        Toast.makeText(MainActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    /**
     * Request to read and write external storage permissions.
     */
    private void requestPermissionForInstallPackage() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        new WriteApkTask(MainActivity.this, new Runnable() {
                            @Override
                            public void run() {
                                installPackage();
                            }
                        }).execute();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        toast(R.string.message_install_failed);
                    }
                })
                .start();
    }

    /**
     * Install package.
     */
    private void installPackage() {
        AndPermission.with(this)
                .install()
                .file(new File(Environment.getExternalStorageDirectory(), "android.apk"))
                .rationale(new InstallRationale())
                .onGranted(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        // Installing.
                    }
                })
                .onDenied(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        // The user refused to install.
                    }
                })
                .start();
    }

    private void requestPermissionForAlertWindow() {
        AndPermission.with(this)
                .overlay()
                .rationale(new OverlayRationale())
                .onGranted(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        showAlertWindow();
                    }
                })
                .onDenied(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        toast(R.string.message_overlay_failed);
                    }
                })
                .start();
    }

    private void showAlertWindow() {

        Intent backHome = new Intent(Intent.ACTION_MAIN);
        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backHome.addCategory(Intent.CATEGORY_HOME);
        startActivity(backHome);
    }
}
