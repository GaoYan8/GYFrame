package com.gy.gyframe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //确认退出app
    private LARBAlertDialog exitDialog;

    @BindView(R.id.mMenuIV)
    TextView mMenuIV;
    private TopRightMenu mRightTopMenu;

    @BindView(R.id.iv_img)
    ImageView iv_img;

    @BindView(R.id.bt_quanxian)
    Button bt_quanxian;


    @OnClick({
            R.id.bt_quanxian
    })
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_quanxian:

                requestPermission(Permission.CAMERA);
                break;
        }
    }


    protected void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


}
