package com.example.demo.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnExpression("false")

public class Task1 {
    @Scheduled(fixedRate = 1000)
    public void Task1() throws InterruptedException {
        System.out.println("Task1 - " + System.currentTimeMillis() / 1000);
        Thread.sleep(1000);
    }

    @Scheduled(fixedRate = 1000)
    public void Task2() throws InterruptedException {
        System.out.println("Task2 - " + System.currentTimeMillis() / 1000);
        Thread.sleep(1000);
    }

    @Scheduled(fixedRate = 1000)
    public void Task3() throws InterruptedException {
        System.out.println("Task3 - " + System.currentTimeMillis() / 1000);
        Thread.sleep(1000);
    }
}

