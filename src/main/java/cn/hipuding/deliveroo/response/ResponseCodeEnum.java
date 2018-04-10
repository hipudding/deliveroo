package cn.hipuding.deliveroo.response;

public enum ResponseCodeEnum {
    OK("success", 0), ERROR("error", 1), UNKNOW("unknow", 2),
    PWDERROR("user password is not correct",3);
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
