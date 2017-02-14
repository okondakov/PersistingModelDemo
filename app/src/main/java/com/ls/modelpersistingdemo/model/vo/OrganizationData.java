package com.ls.modelpersistingdemo.model.vo;

import java.util.ArrayList;
import java.util.List;


public class OrganizationData {
    public String name;
    public int yearOfFoundation;
    public List<MemberData> members = new ArrayList<>();

    public OrganizationData() {
    }

    public OrganizationData(final String name, final int yearOfFoundation){
        this.name = name;
        this.yearOfFoundation = yearOfFoundation;
    }

}
