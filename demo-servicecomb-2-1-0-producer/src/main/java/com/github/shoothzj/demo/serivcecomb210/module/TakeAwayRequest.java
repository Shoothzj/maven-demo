package com.github.shoothzj.demo.serivcecomb210.module;

/**
 * @author hezhangjian
 */
public class TakeAwayRequest {

    private String name;

    private MemberEnum memberEnum;

    public TakeAwayRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemberEnum getMemberEnum() {
        return memberEnum;
    }

    public void setMemberEnum(MemberEnum memberEnum) {
        this.memberEnum = memberEnum;
    }
}
