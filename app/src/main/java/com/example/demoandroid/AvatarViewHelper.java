package com.example.demoandroid;

import android.content.Context;

public class AvatarViewHelper {
    //头像等级
    public static final int LEVEL_1 = 1;    //对应的width,height为24dp
    public static final int LEVEL_2 = 2;    //对应的width,height为36dp
    public static final int LEVEL_3 = 3;    //对应的width,height为40dp
    public static final int LEVEL_4 = 4;    //对应的width,height为48dp
    public static final int LEVEL_5 = 5;    //对应的width,height为56dp

    //头像显示等级
    private int level = -1;
    private String name;
    private String email;
    private int defaultIcon;    //默认头像 , drawable
    private String networkIcon; //网络头像 , url

    private AvatarView avatarView;

    private AvatarViewHelper(AvatarView avatarView) {
        this.avatarView = avatarView;
    }

    public static AvatarViewHelper with(AvatarView avatarView) {
        return new AvatarViewHelper(avatarView);
    }

    public AvatarViewHelper nameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public AvatarViewHelper defaultIcon(int defaultIcon) {
        this.defaultIcon = defaultIcon;
        return this;
    }

    public AvatarViewHelper networkIcon(String url) {
        this.networkIcon = url;
        return this;
    }

    public AvatarViewHelper level(int level) {
        this.level = level;
        return this;
    }

    public AvatarViewHelper show() {
        if (level == -1) {
            throw new IllegalArgumentException("AvatarViewHelper not called level method");
        }

        realShow();

        return this;
    }

    private void realShow() {
        if (networkIcon != null) {
            displayNetworkImageIcon();
        } else if (!isEmpty(name)) {
            displayName();
        }else if(!isEmpty(email)){
            displayEmail();
        }else {
            displayDefaultImageIcon();
        }
    }

    //网络头像
    private void displayNetworkImageIcon() {

    }

    private void displayName() {

    }

    private void displayEmail() {

    }

    private void displayDefaultImageIcon() {

    }

    private void displayImageWithName(String name){

    }

    private void displayEnglish(String text){

    }

    private boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }

    private float sp2px(Context context,float sp){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}


















