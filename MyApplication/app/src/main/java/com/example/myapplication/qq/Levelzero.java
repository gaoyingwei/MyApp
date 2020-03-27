package com.example.myapplication.qq;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Levelzero  extends AbstractExpandableItem<Levelone> implements MultiItemEntity {
    public String friendGroup;

    public Levelzero(String friendName) {
        this.friendGroup = friendName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return Adapter.TYPE_LEVEL_0;
    }
}
