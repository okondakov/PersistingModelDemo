package com.ls.modelpersistingdemo.model.vo.firebase;

import com.ls.modelpersistingdemo.model.vo.MemberData;


public class MemberVO {

    public String fullName;
    public String position;

    public MemberVO() {
    }

    public MemberVO(MemberData memberData){
        this.fullName = memberData.fullName;
        this.position = memberData.position;
    }
}

