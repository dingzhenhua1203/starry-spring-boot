package com.starry.starrycore;

public class CronUtils {
    // private static Logger logger = LoggerFactory.getLogger(CronUtils.class);

    public static boolean isValidExpression(final String cronExpression) {
        //CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            //trigger.setCronExpression(cronExpression);
            //Date date = trigger.computeFirstFireTime(null);
            //return date != null && date.after(new Date());
        } catch (Exception e) {
            //logger.error("invalid expression:{},error msg:{}", cronExpression, e.getMessage());
        }
        return false;
    }
}
