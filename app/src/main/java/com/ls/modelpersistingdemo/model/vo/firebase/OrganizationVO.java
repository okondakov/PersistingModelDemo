package com.ls.modelpersistingdemo.model.vo.firebase;

import com.ls.modelpersistingdemo.model.vo.MemberData;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;

import java.util.ArrayList;
import java.util.List;


public class OrganizationVO {
    public String name;
    public int yearOfFoundation;

    public List<MemberVO> members = new ArrayList<>();

    public OrganizationVO() {
    }

    public OrganizationVO(OrganizationData organizationData) {
        this.name = organizationData.name;
        this.yearOfFoundation = organizationData.yearOfFoundation;

        for (MemberData memberData : organizationData.members) {
            members.add(new MemberVO(memberData));
        }
    }

    public OrganizationData convertToOrganizationData() {
        OrganizationData organizationData = new OrganizationData();

        organizationData.name = this.name;
        organizationData.yearOfFoundation = this.yearOfFoundation;

        for (MemberVO memberVo : members) {
            MemberData memberData = new MemberData();
            memberData.fullName = memberVo.fullName;
            memberData.position = memberVo.position;
            organizationData.members.add(memberData);
        }

        return organizationData;
    }
}
