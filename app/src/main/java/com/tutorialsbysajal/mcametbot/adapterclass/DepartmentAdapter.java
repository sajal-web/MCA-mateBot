package com.tutorialsbysajal.mcametbot.adapterclass;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;

import com.tutorialsbysajal.mcametbot.activity.DepartmentList;
import com.tutorialsbysajal.mcametbot.activity.MainActivity;

public class DepartmentAdapter extends BaseAdapter {
    DepartmentList departmentList;
    String[] department_list;
    Animation animation;

    public DepartmentAdapter(DepartmentList departmentList, String[] department_list) {
        this.departmentList = departmentList;
        this.department_list = department_list;
    }
    public  static int getrendom(int max){
            return (int) (Math.random()*max);
    }

    @Override
    public int getCount() {
        return department_list.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
