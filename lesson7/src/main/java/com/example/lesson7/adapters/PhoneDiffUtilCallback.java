package com.example.lesson7.adapters;

import android.support.v7.util.DiffUtil;

import com.example.lesson7.entities.Phone;

import java.util.List;

public class PhoneDiffUtilCallback extends DiffUtil.Callback {

    private List<Phone> oldList;
    private List<Phone> newList;

    public PhoneDiffUtilCallback(List<Phone> oldList, List<Phone> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getCompany().equals(newList.get(newItemPosition).getCompany()) &&
                (oldList.get(oldItemPosition).getImage() == newList.get(newItemPosition).getImage());
    }
}
