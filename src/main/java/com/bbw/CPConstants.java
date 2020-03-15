package com.bbw;

/**
 * 系统常数字典
 *
 * @author BBW
 */
public class CPConstants {
    public static final String UTF8="utf-8";
    public static final String GBK="gbk";
    public static final String PACKAGE_SOURCE_BEAN = "com.bbw.cmis.front.bean.cc";
    public static final String PACKAGE_TARGET_BEAN = "com.bbw.cmis.front.bean.cmis";
    public static final String PACKAGE_BASIC_BEAN = "com.bbw.cmis.front.bean";
    public static final String PACKAGE_TASK = "com.bbw.cmis.front.task.impl";
    public static final String OPENDAY = "CMIS_OPENDAY";
    public static final String LASTER_OPENDAY = "CMIS_LASTER_OPENDAY";
    public static final String CMIS_STATE = "CMIS_STATE";
    public static final String CUS_MANAGER_CACHE = "CUS_MANAGER_CACHE::%s";
    public static String TASK_NAME_KEY="taskKeyName";
    public static final String COMMON_TRUE = "1";
    //本金
    public static final String AMOUNT_TYPE_PRINCIPAL ="1";
    //利息
    public static final String AMOUNT_TYPE_INTEREST = "2";

    //罚息
    public static final String AMOUNT_TYPE_OVERDUE = "3";
    //复息
    public static final String AMOUNT_TYPE_COMPOUND = "4";
    //应收标志本金
    public static final String YSBZ_OF_PRINCIPAL ="CL";
    //应收标志利息
    public static final String YSBZ_OF_INTEREST = "ABFIOQ";

    //罚息标志
    public static final String FXLY_OF_OVERDUE = "1256";
    //复息标志
    public static final String FXLY_OF_COMPOUND = "3478";

    //额度类型-本金
    public static final String AMOUNT_OF_PRINCIPAL ="1";
    //额度类型-利息
    public static final String AMOUNT_OF_INTEREST = "2";

    //额度类型-罚息
    public static final String AMOUNT_OF_OVERDUE = "3";
    //额度类型-复息
    public static final String AMOUNT_OF_COMPOUND = "4";
    /**
     * 失效/未完成状态 未授权
     */
    public static final String COMMON_FALSE = "0";
    /**
     * 生效/完成状态 已授权
     */
    public static final String COMMON_VALID = "1";
    /**
     * 失效/未完成状态 未授权
     */
    public static final String COMMON_INVALID = "0";

    /**
     * 已查询
     */
    public static final String COMMON_ONTHER = "2";

    /**
     * 数据形态 000 没有需要转换的数据
     */
    public static final String DATA_STATE_NULL = "000";
    /**
     * 数据形态 100 待进行数据转换
     */
    public static final String DATA_STATE_INIT = "100";
    /**
     * 数据形态 200 已在CMIS创建数据
     */
    public static final String DATA_STATE_BUILD = "200";

    /**
     * 数据形态 400 在CMIS创建数据失败
     */
    public static final String DATA_STATE_ERROR = "400";
    /**
     * 数据形态 500 信贷登记成功
     */
    public static final String DATA_STATE_SEND = "500";

    /**
     * 数据形态 700 信贷登记失败
     */
    public static final String DATA_STATE_CERROR = "700";
    /**
     * 数据形态 800 核心处理成功
     */
    public static final String DATA_STATE_SUCCESS = "800";
    /**
     * 数据形态 900 核心处理失败
     */
    public static final String DATA_STATE_FAIL = "900";
    /**
     * 数据批次形态 000 初始化不进行转化
     */
    public static final String DATA_BATCH_STATE_INIT = "000";
    /**
     * 数据批次形态 100  待转化到信贷系统中
     */
    public static final String DATA_BATCH_STATE_DUE = "100";
    /**
     * 数据批次形态 200  转化成功
     */
    public static final String DATA_BATCH_STATE_BUILD = "800";
    /**
     * 数据批次形态 800  处理成功
     */
    public static final String DATA_BATCH_STATE_SUCCESS = "800";
    /**
     * 数据批次形态 900  处理失败
     */
    public static final String DATA_BATCH_STATE_FAIL = "900";

    /**
     * 任务形态 初始化 0
     */
    public static final String DATA_DISPATCH_STATUS_INIT = "0";

    /**
     * 任务形态 执行成功 1
     */
    public static final String DATA_DISPATCH_STATUS_SUCESSS = "1";
    /**
     * 任务形态 执行失败 9
     */
    public static final String DATA_DISPATCH_STATUS_FAIL = "9";
    /**
     * 审批状态  000 - 未提交
     */
    public static final String APPROVE_STATUS_INIT = "000";
    /**
     * 审批状态  111 - 审批中
     */
    public static final String APPROVE_STATUS_ING = "111";
    /**
     * 审批状态  997 - 审批通过
     */
    public static final String APPROVE_STATUS_PASS = "997";
    /**
     * 审批状态  998 - 审批不通过
     */
    public static final String APPROVE_STATUS_NOPASS = "998";
    /**
     * 初始化 未还款
     */
    public static final String REPAY_PLAN_INIT = "0";
    /**
     * 已还款
     */
    public static final String REPAY_PLAN_REPAY = "1";
    /**
     * 逾期未还款
     */
    public static final String REPAY_PLAN_OVER = "9";
    /**
     * 台账状态 初始化
     */
    public static final String ACC_STATUS_INIT = "0";

    /**
     * 台账状态 正常
     */
    public static final String ACC_STATUS_OUT = "1";

    /**
     * 白名单入库
     */
    public static final String DISPATCH_LEVEL_INIT = "1";

    /**
     * 客户征信授权
     */
    public static final String DISPATCH_LEVEL_AUTH = "2";

    /**
     * 征信查询完成
     */
    public static final String DISPATCH_LEVEL_CCIS = "3";

    /**
     * 合同预登记 待审批
     */
    public static final String DISPATCH_LEVEL_REG = "4";

    /**
     * 合同构建完成
     */
    public static final String DISPATCH_LEVEL_CONT = "5";

    /**
     * 放款申请
     */
    public static final String DISPATCH_LEVEL_DETAIL = "6";
    /**
     * 出账中
     */
    public static final String DISPATCH_LEVEL_PVP = "7";

    /**
     * 放款成功
     */
    public static final String DISPATCH_LEVEL_ACC = "8";
    /**
     * 放款失败
     */
    public static final String DISPATCH_LEVEL_OVER = "9";
    /**
     * 合同状态预登记/未生效
     */
    public static final String CONT_STATE_INIT="100";
    /**
     * 合同状态 生效
     */
    public static final String CONT_STATE_AVAIL="200";
    /**
     * 合同状态 注销
     */
    public static final String CONT_STATE_CANCEL="300";

    /**
     * 借据 预登记
     */
    public static final String BILL_STATE_INIT="0";
    /**
     * 借据状态 正常
     */
    public static final String BILL_STATE_NORMAL="1";
    /**
     * 借据状态 结清
     */
    public static final String BILL_STATE_SELLET="2";

}
