package com.ls.modelpersistingdemo.model.vo.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.ls.modelpersistingdemo.model.vo.MemberData;


public class MemberVO {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @DatabaseField(columnName = "full_name")
    public String fullName;

    @DatabaseField(columnName = "position")
    public String position;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public OrganizationVO organization;


    public MemberVO() {
    }

    public MemberVO(MemberData memberData, OrganizationVO organizationVO) {
        this.fullName = memberData.fullName;
        this.position = memberData.position;
        this.organization = organizationVO;
    }

    public MemberData convertToMemberData() {
        MemberData memberData = new MemberData();

        memberData.fullName = this.fullName;
        memberData.position = this.position;

        return memberData;
    }
}

