package com.example.myapplication.qq;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Levelone implements MultiItemEntity {
    public String friendName;
    public int friendSculpture;

    public Levelone(String friendName, int friendSculpture) {
        this.friendName = friendName;
        this.friendSculpture = friendSculpture;
    }

    @Override
    public int getItemType() {
        return Adapter.TYPE_LEVEL_1;
    }
}
