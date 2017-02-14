package com.ls.modelpersistingdemo.model;

import com.ls.modelpersistingdemo.model.vo.OrganizationData;

import java.util.ArrayList;
import java.util.List;


public class DataCache {
    private static DataCache instance;
    private List<OrganizationData> data = new ArrayList<>();


    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }

        return instance;
    }

    public void setData(List<OrganizationData> data) {
        this.data = data;
    }

    public List<OrganizationData> getData() {
        return this.data;
    }
}
