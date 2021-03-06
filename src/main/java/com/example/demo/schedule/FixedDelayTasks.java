package com.example.demo.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@EnableScheduling  //Đánh dấu đấy là component chứa tác vụ định thời
@ConditionalOnExpression("false")  //"true": Nạp component này vào ApplicationContext. "false": không nạp, hoặc vô hiệu hoá class này
public class FixedDelayTasks {
    private long timeOfLastTask = System.currentTimeMillis();

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        long delay = System.currentTimeMillis() - timeOfLastTask; // Tính độ trễ
        System.out.println("Fixed delay task - " + delay);

        Random random = new Random();
        int sleepTime = random.nextInt(5) * 1000;
        Thread.sleep(sleepTime); //Giả lập thời gian thực hiện task khác nhau
        timeOfLastTask = System.currentTimeMillis();
    }
}