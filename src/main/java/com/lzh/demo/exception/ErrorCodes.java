package com.lzh.demo.exception;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodes {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(1001,"系统错误: %s"),
    INVALID_INPUT_PARAMS(1002,"参数错误：%s"),
    NO_SUCH_ENTITY(1003, "没有找到对象：%s"),
    NO_SUCH_ENTITY_ERROR(1004, "%s"),
    NETWORK_ERROR(1005, "网络错误,%s"),
    PARAMETER_REQUIRED_WHAT(10006,"缺少参数: %s"),
    SYSTEM_CONFIG_ERROR(1007,"系统配置错误：%s"),
    INVALID_TOKEN(1008,"TOKEN异常"),



    UPLOAD_FILE_FAIL(2001, "上传失败"),
    UPLOAD_FILE_FAIL_REASON(2002, "上传失败:%s"),
    ACCOUNT_CAPTCHA_TOO_MANY(20220,"验证码发送过于频繁,请于24小时后重试！"),
    ACCOUNT_CAPTCHA_DENIED(20221,"请在%s秒后重新获取"),
    IP_CAPTCHA_TOO_MANY(20222,"IP限制,请2小时后重试！"),
    IP_EXCEED_LIMIT(20223,"IP超过限制,codeKey=%s"),
    ACCOUNT_USER_FORMAT_WRONG(20299,"账户名格式错误"),
    ACCOUNT_CAPTCHA_VALIDATE_TO_MANY(20205,"验证码验证过于频繁，请1分钟后重试！"),
    ACCOUNT_CAPTCHA_ERROR(20204,"验证码错误,还有%s次验证机会"),
    ERROR_SMS(20206,"短信发送失败:%s"),
    ACCOUNT_IMG_CAPTCHA_ERROR(20203,"验证码错误"),
    CODEKEY_AND_CAPCHA_ERROR(20225,"图片验证码错误"),

    STATUS_NOT_EDIT(3001, "该状态不允许修改"),
    STATUS_MUST_IN_TEST_OR_ONLINE_OR_OFFLINE(3002, "该状态不允许发布全网"),
    STATUS_MUST_BE_ONLINE_CAN_OFFLINE(3003, "必须是全网发布状态才能下线。"),
    STATUS_MUST_BE_ONLINE_CAN_ADJUST_PRIORITY(3004, "必须是全网发布状态才能编辑优先级。"),
    STATUS_NOT_DELETE(3005, "该状态不允许删除"),
    STATUS_NOT_DELETE_BY_SERIALS(3006, "已产生用户领取流水，不允许删除"),
    STATUS_NOT_TO_AUDIT(3007, "该状态不允许提交审核"),
    STATUS_NOT_OFFLINE(3008, "该状态不允许下线"),
    STATUS_NOT_AUDIT(3009, "该状态不允许审核"),

    NOT_HAS_NEED_UNLOCK_SUBSIDY(4001, "该订单没有需要解锁的津贴"),
    NOT_HAS_COULD_RECEIVE_NUM(4002, "该津贴的可领取数量已达上限"),
    NOT_HAS_SUBSIDY_BALANCE(4003, "用户津贴余额不足：无法锁定"),
    THIS_ORDERNO_HAS_USED(4004, "该订单号已经使用过了"),
    THIS_ORDERNO_HAS_LOCKED(4005, "该订单号已经被锁定了"),

    QUERY_THIRD_INT_ERROR(5001, "请第三方接口失败：%s"),

    ORDER_IS_NOT_PAY(6001, "订单未支付：%s"),
    ORDER_QUERY_FAIL(6002, "订单查询失败,请重新尝试"),
    PAY_ERROR_PARAMS(6003, "组装支付参数出错"),
    PAY_ORDER_AMOUNT_CHECK_ERROR(6004, "支付金额校验错误"),

    INVALID_ACTIVATE_MOBILE(7002,"该手机号已经激活3台设备，不能再激活"),
    INVALID_ORDER_NUMBER(7003,"合同单号%s无效"),
    INVALID_ORDER_COMPLETE(7004,"合同单号%s已经全部出库并激活"),
    BARCODE_ACTIVITED(7007,"该设备已经激活"),
    ERROR_DATAS(7008,"获取数据失败"),
    NO_TV_IN_ORDER(7009,"此机型已全部激活或不在购销合同中，请确认后重试"),
    MODEL_IS_ALL_ACTIVATED(7010,"此机型在购销合同中已全部激活，不能再激活"),
    NO_MODEL_IN_CONTACT(7011,"购销单中没有此机型不能激活"),
    BARCODE_IS_FIFTEN(7011,"barcode必须大于15位"),


    NO_PASSPORT_UNSIGN_STATUS_ERROR(8001,"协议未签约"),

    API_INTERFACE_SHUTDOWN(9001,"接口已经停止服务"),
    API_INTERFACE_SHUTDOWN_CUSTOMER_INFO(9002,"%s"),

    AUTH_PRODUCT_STATUS_ERROR(10001, "订单状态异常:%s"),
    AUTH_PRODUCT_BUSINESSTYPE_ERROR(10002, "没有该业务线的权限"),

    AUTOMATIC_PROTOCOL_UNSIGN_STATUS_ERROR(11003,"自动续费协议未签约"),

    CARD_TYPE_HAS_EXIT(12001,"卡密类型已经存在"),
    CARD_IMPORT_CARDNO_EXIT(12002,"卡密导入失败,卡号已经存在"),
    CARD_IMPORT_PASSWORD_EXIT(12003,"卡密导入失败,卡密已经存在"),
    CARD_IMPORT_ERROR(12004,"卡密导入失败"),
    PARAM_FORMAT_ERROR(12005,"参数格式不对"),
    CARD_BUSINESSID_EXIT(12006,"卡密业务id已经存在"),
    CARD_EXCEL_FILED_ERROR(12007,"数据错误：%s"),

    BUSS_DEVICE_ERROR(120007,"上传失败"),
    BUSS_DEVICE_CHONGFU_ERROR(120008,"excel中设备Id重复:"),
    BUSS_DATABASE_CHONGFU_ERROR(120009,"设备ID已存在:"),
    JSONPROCESS_ERROR(1009,"解析异常:%s"),
    THIRD_REFRESH_TOKEN_ERROR(20564,"%s"),
    AUTH_TOKEN_EXPIRED(20121,"access_token 已经失效");

    Integer code;
    private String msg;
    ErrorCodes(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return this.msg;
    }

    public static ErrorCodes fromString(String code){
        return KEYS_MAP.get(Integer.valueOf(code));
    }

    private static Map<Integer, ErrorCodes> KEYS_MAP =new HashMap<>();
    static{
        for(ErrorCodes e: ErrorCodes.values()){
            KEYS_MAP.put(e.code, e);
        }
    }
}
