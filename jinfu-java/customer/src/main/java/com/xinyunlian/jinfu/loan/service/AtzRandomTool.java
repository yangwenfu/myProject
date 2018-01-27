package com.xinyunlian.jinfu.loan.service;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by godslhand on 2017/7/3.
 */
public class AtzRandomTool {


    public static Integer randomInteger(Integer start, Integer max) {
        int distance = max - start + 1;
        Integer result = new Random().nextInt(distance) + start;
        return result;

    }

    public static Double randomDouble(Double start, Double max) {
        return new Random().nextDouble() * (max - start) + start;
    }

    /**
     * 一年内的活跃
     *
     * @param num
     * @return
     */
    public static Integer randomMothLastYear(String num) {
        Integer index = null;
        try {
            index = Integer.valueOf(num);
        } catch (Exception e) {
            return randomInteger(6, 9);
        }
        return randomInteger(index, 12);

    }


    /**
     * andomInteger(10000,30000));
     * else
     * <p>
     * 月均定烟额度
     *
     * @param num
     * @return
     */
    public static Double randomMonthAmout(String num) {
        Double amout = null;
        try {
            amout = Double.valueOf(num);
        } catch (Exception e) {
            return Double.valueOf(randomInteger(10000, 30000));
        }

        return Math.floor(amout * randomDouble(1.2, 1.5));
    }

    /**
     * 定烟波动率|定烟活跃度
     *
     * @param num
     * @return
     */
    public static Double randomVariance(String num) {
        DecimalFormat df = new DecimalFormat("#.##");
        Double amout = null;
        try {
            amout = Double.valueOf(num);
        } catch (Exception e) {
            return Double.parseDouble(df.format(randomDouble(0.90, 1.10)));
        }
        return Double.parseDouble(df.format(amout * randomDouble(1.20, 1.50)));
    }

    public static void main(String[] args) {

        System.out.println(randomDouble(1.20,1.50));
        System.out.println(randomDouble(1.20,1.50));
        System.out.println(randomDouble(1.2,1.5));
        System.out.println(randomDouble(1.2,1.5));
//        randomMothLastYear()
        System.out.println(randomMothLastYear(null));
        System.out.println(randomMothLastYear("5.6"));
        System.out.println(randomMothLastYear("aaa"));


        System.out.println(randomMonthAmout(null));
        System.out.println(randomMonthAmout("30000"));
        System.out.println(randomMonthAmout("aaa"));


        System.out.println(randomVariance(null));
        System.out.println(randomVariance("5.6"));
        System.out.println(randomVariance("aaa"));
    }


}
