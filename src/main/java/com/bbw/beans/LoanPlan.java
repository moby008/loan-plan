package com.bbw.beans;


import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanPlan implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 7528625311716999273L;

    /**
     * 借据编号
     */

    private String billNo;
    /**
     * 业务流水
     */

    private String serno;

    /**
     * 当前期次
     */
    @Builder.Default
    private Integer period = 0;

    /**
     * 执行年利率
     */
    @Builder.Default
    private Float yearRate = 0f;

    /**
     * 总期次
     */
    @Builder.Default
    private Integer totalPeriod = 0;

    /**
     * 本期天数
     */
    @Builder.Default
    private Integer totalDays = 0;

    /**
     * 本期开始时间
     */

    private String beginDate;

    /**
     * 本期应还金额
     */
    @Builder.Default
    private BigDecimal planAmount = BigDecimal.ZERO;

    /**
     * 本期应还本金额
     */
    @Builder.Default
    private BigDecimal planPrincipal = BigDecimal.ZERO;

    /**
     * 本期应还息金额
     */
    @Builder.Default
    private BigDecimal planInterest = BigDecimal.ZERO;

    /**
     * 本期应剩余本金
     */
    @Builder.Default
    private BigDecimal planRemain = BigDecimal.ZERO;

    /**
     * 本期应扣担保金额
     */
    @Builder.Default
    private BigDecimal planGrtAmount = BigDecimal.ZERO;

    /**
     * 本期应还款日期
     */
    private String planRepayDate;

    /**
     * 本期实还金额
     */
    @Builder.Default
    private BigDecimal actualAmount = BigDecimal.ZERO;

    /**
     * 本期实还本金额
     */
    @Builder.Default
    private BigDecimal actualPrincipal = BigDecimal.ZERO;

    /**
     * 本期实还息金额
     */
    @Builder.Default
    private BigDecimal actualInterest = BigDecimal.ZERO;

    /**
     * 本期实剩余本金
     */
    @Builder.Default
    private BigDecimal actualRemain = BigDecimal.ZERO;

    /**
     * 本期实扣担保金额
     */
    @Builder.Default
    private BigDecimal actualGrtAmount = BigDecimal.ZERO;

    /**
     * 本期实还款日期
     */
    private String actualRepayDate;

    /**
     * 还款状态
     */
    @Builder.Default
    private String planRepayStatus = "0";

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    /**
     * 数据状态
     */
    private String dataStatus;
    /**
     * 数据日期
     */
    private String dataDate;

    public void print() {
        Console.log("期数:{} 还款时间:{} 偿还本金:{} 偿还利息:{} 还款总额:{} 还款后本金余额:{}", StrUtil.padAfter(this.period.toString(),2," "), StrUtil.padAfter(this.planRepayDate,12," "),StrUtil.padAfter(NumberUtil.decimalFormat("#,##0.00",this.planPrincipal.doubleValue()),12," "),StrUtil.padAfter(NumberUtil.decimalFormat("#,##0.00",this.planInterest.doubleValue()),12," "), StrUtil.padAfter(NumberUtil.decimalFormat("#,##0.00",this.planAmount.doubleValue()),12," "), StrUtil.padAfter(NumberUtil.decimalFormat("#,##0.00",this.planRemain.doubleValue()),12," "));
    }

}