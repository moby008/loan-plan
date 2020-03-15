package com.bbw.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhouuan@bankofbbg.com
 * @Date 2020/3/15 15:45
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepayMode {
    int principalMonth = 3;//还本间隔
    int interestSplit = 1;//还息间隔
    int repayDay = 20;//每月20号还款
    double repayRate = 0.15;//还本比例
    int specialMode = 0;//1:等额本息 2:等额本金 3:到期还本
}
