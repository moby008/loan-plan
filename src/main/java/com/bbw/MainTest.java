package com.bbw;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import com.bbw.beans.LoanDetail;
import com.bbw.beans.LoanPlan;
import com.bbw.beans.RepayMode;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author zhouuan@bankofbbg.com
 * @Date 2020/2/28 16:36
 * @Version 1.0
 **/
public class MainTest {
    public static void main1(String[] args) {
        MainTest main = new MainTest();
        int term = 36;
        String startDate = "2020-03-01";
        double amount = 1200000;
        LoanDetail loanDetail = main.initLoanDetail(amount, term, startDate);
        String preRepayDate = "2021-06-02";
        double preRepayAmount = 21000;
        main.rebuildPlan(loanDetail, preRepayDate, preRepayAmount);
    }
    public static void main(String[] args) {
        MainTest main = new MainTest();
        DateTime startDateTime = DateUtil.parseDate("2020-01-01");
        DateTime dueDateTime = DateUtil.parseDate("2030-01-01");
        BigDecimal loanAmount = BigDecimal.valueOf(1000000);
        Float yearRate = Float.valueOf(0.0475f);
        RepayMode repeyMode = new RepayMode();
        repeyMode.setSpecialMode(1);
        repeyMode.setPrincipalMonth(1);
        repeyMode.setInterestSplit(1);
        List<LoanPlan> loanPlans = main.buildLoanPlanList(startDateTime, dueDateTime, "billNo", "serno", loanAmount, BigDecimal.ZERO, yearRate, repeyMode);
        loanPlans.stream().forEach(LoanPlan::print);
    }
    private LoanDetail initLoanDetail(double amount, int term, String startDate) {
        BigDecimal loanAmount = BigDecimal.valueOf(amount);
        LoanDetail loanDetail = this.buildLoan(term, loanAmount, startDate);
        //loanDetail.print();
        List<LoanPlan> loanPlans = this.buildPlan(term, loanAmount, startDate);
        loanDetail.setLoanPlans(loanPlans);
        Console.log("--------- 本金:{},期数:{} 放款日期:{} 初始还款计划 ---------", NumberUtil.decimalFormat("#,##0.00", amount), term, startDate);
        loanPlans.stream().forEachOrdered(loanPlan -> loanPlan.print());
        return loanDetail;
    }


    /**
     * 构建贷款借据
     *
     * @param term
     * @param loanAmount
     * @param startDate
     * @return
     */
    private LoanDetail buildLoan(int term, BigDecimal loanAmount, String startDate) {
        LoanDetail loanDetail = LoanDetail.builder().startDate(startDate).loanAmount(loanAmount).loanBalance(loanAmount).dueDate(DateUtil.parseDate(startDate).offsetNew(DateField.MONTH, term).toDateStr()).build();
        return loanDetail;
    }

    /**
     * 生成随机本金还款计划
     *
     * @param term
     * @param loanAmount
     * @param startDate
     * @return
     */
    private List<LoanPlan> buildPlan(int term, BigDecimal loanAmount, String startDate) {
        List<LoanPlan> plans = new ArrayList<>();
        BigDecimal loanBalance = loanAmount;
        Random random = new Random();
        int min = 10;
        int max = 30;
        for (int i = 1; i <= term; i++) {
            double p = 1;
            if (i != term) {
                //生成随机本金比例 上限0.3 下限0.1
                p = Double.valueOf(random.nextInt(max - min) + min) / 100;
                // Console.log("p={}", p);
            }
            BigDecimal amount = loanBalance.multiply(BigDecimal.valueOf(p));
            loanBalance = loanBalance.subtract(amount);
            LoanPlan plan = LoanPlan.builder()
                    .period(i)
                    .planAmount(amount)
                    .planRemain(loanBalance)
                    .planRepayDate(DateUtil.parseDate(startDate).offsetNew(DateField.MONTH, i).toDateStr())
                    .build();
            plans.add(plan);
        }
        this.checkPlan(plans, loanAmount);
        return plans;

    }

    private boolean checkPlan(List<LoanPlan> plans, BigDecimal loanAmount) {
        BigDecimal planAmount = plans.stream().map(LoanPlan::getPlanAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        boolean success = planAmount.subtract(loanAmount).intValue() == 0;
        Console.log("还款计划校验 : planAmount={} loanAmount={} 结果:{}", NumberUtil.decimalFormat("#,##0.00", planAmount.doubleValue()), NumberUtil.decimalFormat("#,##0.00", loanAmount.doubleValue()), success ? "正确" : "错误");
        return success;
    }

    /**
     * 根据提起还款情况 生成新还款计划
     *
     * @param loanDetail
     * @param repayDate      提前还款日期
     * @param preRepayAmount 提前还款金额
     */
    private void rebuildPlan(LoanDetail loanDetail, String repayDate, double preRepayAmount) {
        List<LoanPlan> loanPlans = loanDetail.getLoanPlans();
        Console.log("----------------------{} 提前还本:{} 后新还款计划 -----------------", repayDate, NumberUtil.decimalFormat("#,##0.00", preRepayAmount));
        //取出已还款 计划
        List<LoanPlan> nLoanPlans = loanPlans.stream()
                .filter(loanPlan -> {
                    String planRepayDate = loanPlan.getPlanRepayDate();
                    return DateUtil.parseDate(repayDate).compareTo(DateUtil.parseDate(planRepayDate)) > 0;
                })
                .collect(Collectors.toList());
        nLoanPlans.stream().forEachOrdered(LoanPlan::print);
        Console.log("-----> {} 提前还本 : {} <-----", repayDate, NumberUtil.decimalFormat("#,##0.00", preRepayAmount));
        //计划已还本金
        double actualAmount = nLoanPlans.stream()
                .mapToDouble(loanPlan -> loanPlan.getPlanAmount().doubleValue())
                .sum();
        //提前还款前本金余额
        double beforePlanLoanBalance = loanDetail.getLoanAmount().doubleValue() - actualAmount;
        //提前还款后本金余额
        double afterPlanLoanBalance = beforePlanLoanBalance - preRepayAmount;
        //按公式 新当期还款本金=提前还款后本金余额*旧当期还款本金/提前还款前本金余额 计算新的还款计划
        loanPlans.stream()
                .filter(loanPlan -> {
                    String planRepayDate = loanPlan.getPlanRepayDate();
                    return DateUtil.parseDate(repayDate).compareTo(DateUtil.parseDate(planRepayDate)) <= 0;
                })
                .forEachOrdered(loanPlan -> {
                    double oldPlanAmount = loanPlan.getPlanAmount().doubleValue();
                    double newPlanAmount = afterPlanLoanBalance * oldPlanAmount / beforePlanLoanBalance;
                    loanPlan.setPlanAmount(BigDecimal.valueOf(newPlanAmount));
                    loanPlan.print();
                    nLoanPlans.add(loanPlan);
                });
        loanDetail.setLoanPlans(nLoanPlans);
        this.checkPlan(nLoanPlans, loanDetail.getLoanAmount().subtract(BigDecimal.valueOf(preRepayAmount)));


    }

    /**
     * 生成还款计划
     *
     * @param startDateTime
     * @param dueDateTime
     * @param billNo
     * @param serno
     * @param loanAmount
     * @param loanBalance
     * @param yearRate
     * @param repayMode1
     * @return
     */
    public List<LoanPlan> buildLoanPlanList(DateTime startDateTime, DateTime dueDateTime, String billNo, String serno, BigDecimal loanAmount, BigDecimal loanBalance, Float yearRate, RepayMode repayMode1) {
        int principalMonth = repayMode1.getPrincipalMonth();//还本间隔
        int interestSplit = repayMode1.getInterestSplit();//还息间隔
        int repayDay = repayMode1.getRepayDay();//每月20号还款  为0 时 则按放款日对日计算
        int specialMode = repayMode1.getSpecialMode();//特殊还款方式
        double tempRepayRate = 0;
        double repayRate = repayMode1.getRepayRate();//还本比例
        BigDecimal principalRepayAmount = BigDecimal.ZERO;//当期归还本金
        BigDecimal totalRepayAmount = BigDecimal.ZERO;//当期归还本金利息总和
        BigDecimal dayRate = new BigDecimal(yearRate.toString()).divide(new BigDecimal("360"), 12, BigDecimal.ROUND_HALF_UP);//日利率
        BigDecimal monthRate = new BigDecimal(yearRate.toString()).divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);//月利率
        int term = Convert.toInt(DateUtil.betweenMonth(startDateTime, dueDateTime, true));//总期数
        DateTime beginDateTimeOfMonth = startDateTime;
        List<LoanPlan> plans = new ArrayList<LoanPlan>();
        switch (specialMode) {
            case 1:
                // totalRepayAmount = new BigDecimal(PlanUtils.equalAmountOfInterest(loanAmount, startDateTime, dueDateTime, repayDay, dayRate));
                break;
            case 2:
                principalRepayAmount = loanAmount.divide(new BigDecimal(term), 12, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                ;
        }
        //开始生成还款计划
        for (int i = 1; i <= term && tempRepayRate <= 1; i++) {
            Date time = new Date();
            LoanPlan plan = new LoanPlan();
            plan.setBillNo(billNo);
            plan.setPeriod(i);
            plan.setSerno(serno);
            plan.setTotalPeriod(term);
            plan.setYearRate(yearRate);
            plan.setCreateTime(time);
            //plan.setDataDate(globalParamsService.getOpenDay());
            plan.setUpdateTime(time);
            plan.setBeginDate(beginDateTimeOfMonth.toDateStr());
            plan.setPlanRepayStatus(CPConstants.REPAY_PLAN_INIT);
            plan.setDataStatus(CPConstants.DATA_STATE_INIT);
            DateTime nextBeginDateTimeOfMonth = null;
            if (i % interestSplit == 0 || i == term) {
                //触发还息计算
                DateTime intestOffsetMonthTime = DateUtil.offsetMonth(beginDateTimeOfMonth, interestSplit);
                //还息日期当期
                DateTime intestDueMonthTime = intestOffsetMonthTime.setField(Calendar.DAY_OF_MONTH, repayDay);
                if (i == term) {
                    //最后一期触发日期替换
                    intestDueMonthTime = dueDateTime;
                }
                long days = DateUtil.between(beginDateTimeOfMonth, intestDueMonthTime, DateUnit.DAY);
                monthRate = dayRate.multiply(Convert.toBigDecimal(days));
                plan.setTotalDays(Convert.toInt(days));
                //当期利息金额
                BigDecimal intestAmount = loanBalance.multiply(dayRate).multiply(new BigDecimal(days));
                plan.setPlanInterest(intestAmount);
                plan.setPlanRepayDate(intestDueMonthTime.toDateStr());
                nextBeginDateTimeOfMonth = intestDueMonthTime;
            }

            if ((principalMonth > 0 && i % principalMonth == 0) || i == term) {
                //当期还本金额
                BigDecimal termPlanPrincipal = BigDecimal.ZERO;
                if (i == term) {
                    termPlanPrincipal = loanBalance;
                    loanBalance = BigDecimal.ZERO;
                } else {
                    switch (specialMode) {
                        case 1:
                            //termPlanPrincipal = Convert.toBigDecimal(loanAmount.doubleValue()* monthRate.doubleValue() * (Math.pow((1+monthRate.doubleValue()),i-1))/(Math.pow(1+monthRate.doubleValue(),term)-1));
                            termPlanPrincipal = loanAmount.multiply(monthRate).multiply(monthRate.add(BigDecimal.ONE).pow(i - 1)).divide(monthRate.add(BigDecimal.ONE).pow(term).subtract(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
                            break;
                        case 2:
                            termPlanPrincipal = principalRepayAmount;
                            break;
                        case 3:
                            termPlanPrincipal = BigDecimal.ZERO;
                            break;
                        default:
                            tempRepayRate = tempRepayRate + repayRate;
                            termPlanPrincipal = loanAmount.multiply(new BigDecimal(repayRate));
                            break;

                    }
                    loanBalance = loanBalance.subtract(termPlanPrincipal);
                    //最后一期余额一定为0
                    if (i == term && loanBalance.compareTo(BigDecimal.ZERO) > 0) {
                        termPlanPrincipal = termPlanPrincipal.add(loanBalance);
                        loanBalance = BigDecimal.ZERO;
                    }
                }
                plan.setPlanPrincipal(termPlanPrincipal);
            }
            plan.setPlanRemain(loanBalance);
            plan.setPlanAmount(plan.getPlanPrincipal().add(plan.getPlanInterest()));
            plans.add(plan);
            beginDateTimeOfMonth = nextBeginDateTimeOfMonth;
        }
        return plans;
    }

}
