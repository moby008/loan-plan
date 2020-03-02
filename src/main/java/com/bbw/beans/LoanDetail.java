package com.bbw.beans;


import cn.hutool.core.lang.Console;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借据信息
 */
@Data
@Builder
public class LoanDetail implements Serializable {

    /**
     * 业务流水
     */
    private String serno;
    /**
     * 客户编码
     */
    private String cusId;
    /**
     * 客户姓名
     */
    private String cusName;
    /**
     * 借据编号
     */
    private String billNo;
    /**
     * 还款计划信息
     */
    @Builder.Default
    private List<LoanPlan> loanPlans = new ArrayList<LoanPlan>();
    /**
     * 合同编号
     */
    private String contNo;
    /**
     * 借据金额
     */
    @Builder.Default
    private BigDecimal loanAmount = BigDecimal.ZERO;
    /**
     * 借据余额
     */
    @Builder.Default
    private BigDecimal loanBalance = BigDecimal.ZERO;
    /**
     * 贷款期限
     */
    @Builder.Default
    private Integer loanTerm = 0;
    /**
     * 借款用途
     */
    private String loanUse;
    /**
     * 通知合作方次数
     */
    @Builder.Default
    private Integer noticeCount = 0;
    /**
     * 起始日期
     */
    private String startDate;
    /**
     * 到期日期
     */
    private String dueDate;
    /**
     * 借款发放账号
     */
    private String enterAccount;
    /**
     * 借款发放账户名称
     */
    private String enterAccountName;
    /**
     * 借款人存款帐号
     */
    private String depositAccount;
    /**
     * 借款人存款账户名称
     */
    private String depositAccountName;
    /**
     * 借款人还款账号
     */
    private String repaymentAccount;

    /**
     * 借款人还款账户名
     */
    private String repaymentAccountName;
    /**
     * 贷款投向
     */
    private String loanDirection;
    /**
     * 投向地区
     */
    private String areaDirection;
    /**
     * 执行年利率
     */
    @Builder.Default
    private Float yearRate = 0f;
    /**
     * 每月还款日期
     */
    @Builder.Default
    private Integer paymentDate = 20;
    /**
     * 担保费率
     */
    @Builder.Default
    private Float guaranteeRate = 0f;
    /**
     * 借据状态
     */
    @Builder.Default
    private String billStatus = "0";
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 担保方式
     */
    @Builder.Default
    private String guaranteeType = "30";
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 数据状态
     */
    @Builder.Default
    private String dataStatus = "0";
    /**
     * 数据日期
     */
    private String dataDate;

    /**
     * 审批状态
     */
    @Builder.Default
    private String approveStatus = "0";
    /**
     * 审批意见
     */
    @Builder.Default
    private String approveMessage = "尚未出账";

    /**
     * 业务归属地区
     */
    private String bizAreaCode;
    /**
     * 账务机构
     */
    private String finaBrId;
    /**
     * 管理机构
     */
    private String inputBrId;
    /**
     * 客户经理
     */
    private String inputId;

    public void print() {
        Console.log("开始时间:{} 到期时间:{} 贷款金额:{} 贷款余额:{}", this.startDate, this.dueDate, this.loanAmount, this.loanBalance);
    }


}
