package com.bbw;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import com.bbw.beans.LoanDetail;
import com.bbw.beans.LoanPlan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author zhouuan@bankofbbg.com
 * @Date 2020/2/28 16:36
 * @Version 1.0
 **/
public class MainTest {
    public static void main(String[] args) {
        MainTest main = new MainTest();
        int term = 36;
        String startDate = "2020-03-01";
        double amount = 1200000;
        LoanDetail loanDetail = main.initLoanDetail(amount, term, startDate);
        String preRepayDate = "2021-06-02";
        double preRepayAmount = 21000;
        main.rebuildPlan(loanDetail, preRepayDate, preRepayAmount);
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

}
