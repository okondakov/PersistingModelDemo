package com.ls.modelpersistingdemo.model.vo.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;

import java.util.List;


public class OrganizationVO {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @DatabaseField(columnName = "name")
    public String name;

    @DatabaseField(columnName = "year_of_foundation")
    public int yearOfFoundation;


    public OrganizationVO() {
    }

    public OrganizationVO(OrganizationData organizationData) {
        this.name = organizationData.name;
        this.yearOfFoundation = organizationData.yearOfFoundation;
    }

    public OrganizationData convertToOrganizationData(List<MemberVO> membersVO) {
        OrganizationData organizationData = new OrganizationData();

        organizationData.name = this.name;
        organizationData.yearOfFoundation = this.yearOfFoundation;

        for (MemberVO memberVO : membersVO) {
            organizationData.members.add(memberVO.convertToMemberData());
        }

        return organizationData;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof OrganizationVO)) {
            return false;
        }

        return (this.id == ((OrganizationVO)o).id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}