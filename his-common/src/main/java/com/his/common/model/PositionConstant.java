package com.his.common.model;

/**
 *
 * 组织机构角色类型区分
 */
public enum PositionConstant {

    POSITION_ORG_TYPE(1,"组织机构"),
    POSITION_ROLE_TYPE(2,"组织机构"),
    OPERATION_ORG(1, "机构"),
    OPERATION_POS(2, "部门"),
    OPERATION_GRP(3, "工作组"),
    OPERATION_ORG_ROLE(1, "机构角色"),
    OPERATION_POS_ROLE(2, "部门角色"),
    OPERATION_GRP_ROLE(3, "工作组角色");

    private Integer type;

    private String description;

    PositionConstant(){

    }
    PositionConstant(Integer type, String description){
        this.type = type;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}