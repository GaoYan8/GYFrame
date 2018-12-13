package com.gy.gyframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gy.gylibrary.lpopupmenu.MenuItem;
import com.gy.gylibrary.lpopupmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mMenuIV;
    private TopRightMenu mRightTopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuIV = findViewById(R.id.mMenuIV);
        mMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRightTopMenu.showAsDropDown(mMenuIV, 0, 40);
            }
        });

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(0, "2016年11月"));
        menuItems.add(new MenuItem(0, "2016年11月"));
        menuItems.add(new MenuItem(0, "2016年11月"));

        if (mRightTopMenu == null) {
            mRightTopMenu = new TopRightMenu.Builder(MainActivity.this)
//                            .windowHeight(480)     //当菜单数量大于3个时，为wrap_content,反之取默认高度320
//                        .windowWidth(320)      //默认宽度wrap_content
                    .dimBackground(true)           //背景变暗，默认为true
                    .needAnimationStyle(true)   //显示动画，默认为true
                    .animationStyle(R.style.RTM_ANIM_STYLE)  //默认为R.style.RTM_ANIM_STYLE
                    .addMenuList(menuItems)
                    .addMenuItem(new MenuItem(0, "2016年10月"))
                    .setGravity(Gravity.CENTER)
                    .setShowDividingLine(true)
                    .onMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                        @Override
                        public void onMenuItemClick(View view, int position) {
                            Toast.makeText(MainActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                        }
                    }).build();
        }

    }

}
