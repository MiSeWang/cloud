package com.mrlv.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限实体类
 */
@TableName("sys_perm")
public class SysPerm extends Model<SysPerm> {
    //设置id为自行输入
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;          //id
    private String val;         //权限值，shiro的权限限定表达式
    private String parentVal;   //父节点权限值
    private String name;        //权限名称
    private Integer type;       //权限类型：1.菜单 2.按钮 3.接口 4.特殊
    private Integer leaf;       //是否是叶子节点
    private Date createDate;    //创建时间
    private Date updateDate;    //更新时间
    private String remarks;     //说明
    private Integer delFlag;    //删除标记：1为已删除

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

    public String getParentVal() {
        return parentVal;
    }

    public void setParentVal(String parentVal) {
        this.parentVal = parentVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return val;
    }
}
