package com.mrlv.api.vo;

import com.mrlv.api.entity.SysPerm;

import java.util.ArrayList;
import java.util.List;

public class PermTreeVo {

    private String id;

    private String val;

    private String label;

    private String parentVal;

    private List<PermTreeVo> children = new ArrayList<>();

    public PermTreeVo() {
        super();
    }

    public PermTreeVo(SysPerm sysPerm) {
        this.id = sysPerm.getId();
        this.val = sysPerm.getVal();
        this.label = sysPerm.getName();
        this.parentVal = sysPerm.getParentVal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentVal() {
        return parentVal;
    }

    public void setParentVal(String parentVal) {
        this.parentVal = parentVal;
    }

    public List<PermTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<PermTreeVo> children) {
        this.children = children;
    }
}
