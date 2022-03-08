package com.starry.codeview.threads.P00_ShowMeTheDifference;

/**
 * Alibaba面试题
 * 比较两个方法的不同
 * 答案： 第一个方法result能被访问到中间状态的脏数据， 而第一个方法 要么就是初始值0要么就是最终结果
 */
public class ShowMeTheDifference {
    final class Accumulator {
        private double result = 0.0D;

        public void addAll(Double[] doubles) {
            for (Double aDouble : doubles) {
                result += aDouble;
            }
        }
    }

    final class Accumulator2 {
        private double result = 0.0D;

        public void addAll(Double[] doubles) {
            Double sum = 0.0D;
            for (Double aDouble : doubles) {
                sum += aDouble;
            }
            result += sum;
        }
    }
}
