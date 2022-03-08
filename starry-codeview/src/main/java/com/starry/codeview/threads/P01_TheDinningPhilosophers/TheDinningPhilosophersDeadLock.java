package com.starry.codeview.threads.P01_TheDinningPhilosophers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 哲学家就餐问题：
 * 五位哲学家围坐在一张圆形餐桌旁，做以下两件事情之一：吃饭，或者思考。吃东西的时候，他们就停止思考，思考的时候也停止吃东西。
 * 餐桌中间有一大碗饭，每两个哲学家之间有一只筷子。必须拿到两只筷子才能吃东西。他们只能使用自己左右手边的那两只。
 * <p>
 * 解决方案 是奇数取左，偶数取右
 */
public class TheDinningPhilosophersDeadLock {

    public static void main(String[] args) {

        KuaiZi k1 = new KuaiZi("1");
        KuaiZi k2 = new KuaiZi("2");
        KuaiZi k3 = new KuaiZi("3");
        KuaiZi k4 = new KuaiZi("4");
        KuaiZi k5 = new KuaiZi("5");

        List<Philosopher> philsophers = new ArrayList<>();
        philsophers.add(new Philosopher(1, "luffy", k1, k2));
        philsophers.add(new Philosopher(2, "nami", k2, k3));
        philsophers.add(new Philosopher(3, "chorbar", k3, k4));
        philsophers.add(new Philosopher(4, "zoro", k4, k5));
        philsophers.add(new Philosopher(5, "sanji", k5, k1));

        for (Philosopher p : philsophers) {
            p.start(); // 注意执行线程是start 不能run。run的话还是主线程当方法执行各个子线程类的run方法， 这样就产生了多线程死锁问题
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Philosopher extends Thread {

        private Integer workNumber;
        private String userName;

        private KuaiZi left;

        private KuaiZi right;

        public void eat() throws InterruptedException {
            /**
             * i & 1
             * 两个只要有一个是偶数就为等于0
             * 两个都是奇数等于1
             */

            if ((workNumber & 1) != 0) {
                synchronized (left) {
                    Thread.sleep(1000);
                    if (right != null) {
                        synchronized (right) {
                            Thread.sleep(1000);
                            System.out.println(workNumber + "号哲学家：" + userName + "拿到两只筷子，吃饭！");
                        }

                    }
                }
            } else {
                synchronized (right) {
                    Thread.sleep(1000);
                    if (left != null) {
                        synchronized (left) {
                            Thread.sleep(1000);
                            System.out.println(workNumber + "号哲学家：" + userName + "拿到两只筷子，吃饭！");
                        }

                    }
                }
            }
        }

        @Override
        public void run() {
            try {
                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class KuaiZi {
        private String name;

    }
}
