package cn.hipuding.deliveroo.response;

public enum ResponseCodeEnum {
    OK("success", 0), ERROR("error", 1), UNKNOW("unknow", 2),
    PWD_ERROR("user password is not correct",3),
    PARAMETER_ERROR("parameter is not corrent",4),
    ITEM_NOT_EXIST("target is not exist",5),
    ACCESS_DENY("access deny",6);
    // 成员变量
    private String desc;
    private int code;
    // 构造方法
    private ResponseCodeEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
