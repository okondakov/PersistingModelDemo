package com.ls.modelpersistingdemo.model;

import com.ls.modelpersistingdemo.model.vo.OrganizationData;

import java.util.List;


public interface IDataStorage {
    void save(List<OrganizationData> list);

    void load(DataLoadListener listener);

    interface DataLoadListener {
        void onDataLoaded(List<OrganizationData> data);
    }
}

