package com.gy.gylibrary.lpopupmenu;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/12/13
 * @Describe
 */
public class MenuItem<T> {
    private int icon;
    private boolean showIcon;
    private String text;
    private int badgeCount;

    private T obj;

    public MenuItem(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public MenuItem(int icon, boolean showIcon, String text) {
        this.icon = icon;
        this.showIcon = showIcon;
        this.text = text;
    }

    public MenuItem(int icon, String text, int badgeCount) {
        this.icon = icon;
        this.text = text;
        this.badgeCount = badgeCount;
    }

    public MenuItem(int icon, boolean showIcon, String text, int badgeCount) {
        this.icon = icon;
        this.showIcon = showIcon;
        this.text = text;
        this.badgeCount = badgeCount;
    }


    public MenuItem(int icon, String text, T obj) {
        this(icon, text);
        this.obj = obj;
    }

    public MenuItem(int icon, boolean showIcon, String text, T obj) {
       this(icon, showIcon, text);
        this.obj = obj;
    }

    public MenuItem(int icon, String text, int badgeCount, T obj) {
        this(icon, text, badgeCount);
        this.obj = obj;
    }

    public MenuItem(int icon, boolean showIcon, String text, int badgeCount, T obj) {
        this(icon, showIcon, text, badgeCount);
        this.obj = obj;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isShowIcon() {
        return showIcon;
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
