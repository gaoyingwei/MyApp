package com.example.myapplication.two;

import androidx.annotation.IntDef;

@IntDef({ItemType.BIG_SORT, ItemType.SMALL_SORT})
public @interface ItemType {
    int BIG_SORT = 0;
    int SMALL_SORT = 1;
}