package com.bbw.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.math.BigDecimal;
import java.util.Calendar;

public class PlanUtils {
    /**
     * 传统等额本息算法
     * @param loanAmount
     * @param loanStartDate
     * @param dueDateTime
     * @param monthRate
     * @return
     */

    public static double equalAmountOfInterest(BigDecimal loanAmount, DateTime loanStartDate, DateTime dueDateTime, BigDecimal monthRate) {
        int month = Convert.toInt(DateUtil.betweenMonth(loanStartDate, dueDateTime, true));//总期数
        return (loanAmount.doubleValue() * monthRate.doubleValue() * Math.pow(1 + monthRate.doubleValue(), month))
                / (Math.pow(1 + monthRate.doubleValue(), month) - 1);
    }

    /**
     * 等额本息每个月还款额度
     *
     * @param loanAmount
     * @param loanStartDate
     * @param dueDateTime
     * @param repayDay
     * @param dayRate
     * @return
     */
    public static double equalAmountOfInterest(BigDecimal loanAmount, DateTime loanStartDate, DateTime dueDateTime, int repayDay, BigDecimal dayRate) {
        int term = Convert.toInt(DateUtil.betweenMonth(loanStartDate, dueDateTime, true));//总期数
        DateTime beginDateTime = DateUtil.date(loanStartDate);//起始时间
        double sumPeriodValue = 1d, curPeriodValue = 0, totalPeriodValue = 0;
        for (int i = 1; i <= term; i++) {
            //触发还息计算
            DateTime nextMonthTime = DateUtil.offsetMonth(beginDateTime, 1);
            //还款日
            DateTime repayDateTime = nextMonthTime.setField(Calendar.DAY_OF_MONTH, repayDay);
            if (i == term) {
                //最后一期触发日期替换
                repayDateTime = dueDateTime;
            }
            //当期天数
            long days = DateUtil.between(beginDateTime, repayDateTime, DateUnit.DAY);
            //当月利率
            curPeriodValue = 1 + dayRate.doubleValue() * days;
            sumPeriodValue /= curPeriodValue;
            totalPeriodValue += sumPeriodValue;
        }
        return loanAmount.divide(new BigDecimal(totalPeriodValue), 8, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        // BigDecimal dayRate = new BigDecimal("0.08").divide(new BigDecimal("360"),12,BigDecimal.ROUND_HALF_UP);
        //BigDecimal loanAmount = new BigDecimal("10000");
        //double amount = PlanUtils.EqualAmountOfInterest(loanAmount, DateUtil.parseDate("2017-06-19"), DateUtil.parseDate("2018-06-19"), 20, dayRate);
        // System.err.println(amount);
       // System.err.println(DateUtil.parse("2017-02-01").getDay());
    }


}
