package com.gy.gylibrary.lpopupmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gy.gylibrary.R;

import java.util.List;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/12/13
 * @Describe
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private int mGravity;
    private List<MenuItem> mMenuItemList;
    private TopRightMenu mRightTopMenu;
    private TopRightMenu.OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mContext;

    public void setOnMenuItemClickListener(TopRightMenu.OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public MenuAdapter(Context context, int gravity, TopRightMenu rightTopMenu, List<MenuItem> menuItemList) {
        mContext = context;
        mGravity = gravity;
        mRightTopMenu = rightTopMenu;
        mMenuItemList = menuItemList;
    }

    public void setData(List<MenuItem> menuItemList) {
        mMenuItemList = menuItemList;
        notifyDataSetChanged();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.rt_menu_item, null);
        view.setGravity(mGravity);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
        MenuItem menuItem = mMenuItemList.get(position);
        holder.mTextTV.setText(menuItem.getText());
        if (menuItem.getBadgeCount() > 99) {
            holder.mBadgeTV.setVisibility(View.VISIBLE);
            holder.mBadgeTV.setText("99+");
            holder.mBadgeTV.setBackgroundResource(R.drawable.badge_red);
            ViewGroup.LayoutParams params = holder.mBadgeTV.getLayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = dip2Px(20);
        } else if (menuItem.getBadgeCount() > 0) {
            holder.mBadgeTV.setVisibility(View.VISIBLE);
            holder.mBadgeTV.setText(menuItem.getBadgeCount() + "");
        } else {
            holder.mBadgeTV.setVisibility(View.GONE);
        }


        if (menuItem.isShowIcon()) {
            int iconRes = menuItem.getIcon();
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(iconRes < 0 ? 0 : iconRes);
        } else {
            holder.mIcon.setVisibility(View.GONE);
        }

        if (position == 0) {
            holder.mContainer.setBackground(addStateDrawable(mContext, -1, R.drawable.popup_top_pressed));
        } else if (position == mMenuItemList.size() - 1) {
            holder.mContainer.setBackground(addStateDrawable(mContext, -1, R.drawable.popup_bottom_pressed));
        } else {
            holder.mContainer.setBackground(addStateDrawable(mContext, -1, R.drawable.popup_middle_pressed));
        }
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnMenuItemClickListener != null) {
                    mRightTopMenu.dismiss();
                    mOnMenuItemClickListener.onMenuItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }

    private StateListDrawable addStateDrawable(Context context, int normalId, int pressedId) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = normalId == -1 ? null : context.getResources().getDrawable(normalId);
        Drawable pressed = pressedId == -1 ? null : context.getResources().getDrawable(pressedId);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private View mContainer;
        private TextView mTextTV;
        private ImageView mIcon;
        private TextView mBadgeTV;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView;
            mBadgeTV = itemView.findViewById(R.id.rt_menu_item_badge);
            mIcon = itemView.findViewById(R.id.rt_menu_item_icon);
            mTextTV = itemView.findViewById(R.id.rt_menu_item_text);
        }
    }

    /*
     * converts dip to px
     */
    public int dip2Px(float dip) {
        return (int) (dip * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }
}