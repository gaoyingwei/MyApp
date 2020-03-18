package com.example.myapplication.two;

import java.util.List;

public class SortBean {
    public int bigSortId;
    public String bigSortName;

    public List<ListBean> list;

    public static class ListBean {
        public int smallSortId;
        public String smallSortName;
    }

    public boolean isSelected;
}
