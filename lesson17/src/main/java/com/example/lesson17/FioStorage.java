package com.example.lesson17;

import java.util.ArrayList;
import java.util.List;

public class FioStorage {

    private static final FioStorage instance = new FioStorage();
    private List<String> data;

    public static FioStorage getInstance() {
        return instance;
    }

    private FioStorage() {
    }

    public List<String> getData() {
        return data == null ? new ArrayList<String>() : data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
