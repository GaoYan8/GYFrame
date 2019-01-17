package com.gy.gyframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gy.gylibrary.image.ImageLoaderUtils;
import com.gy.gylibrary.lpopupmenu.MenuItem;
import com.gy.gylibrary.lpopupmenu.TopRightMenu;
import com.gy.gylibrary.view.LARBAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //确认退出app
    private LARBAlertDialog exitDialog;

    private TextView mMenuIV;
    private TopRightMenu mRightTopMenu;

    @BindView(R.id.iv_img)
    ImageView iv_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMenuIV = findViewById(R.id.mMenuIV);
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
                }).setTitleTextColor(0,0,R.color.colorAccent);


    }

}
