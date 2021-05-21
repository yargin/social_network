package com.getjavajob.training.yarginy.socialnetwork.web.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ReactiveChatController {
    private final List<Stock> stocks = new ArrayList<>();
    private final Random random = new Random(System.currentTimeMillis());
    private TaskScheduler taskScheduler;
    private SimpMessagingTemplate simpleMessagingTemplate;

    public ReactiveChatController() {
        stocks.add(new Stock("VMW", 1.));
        stocks.add(new Stock("EMC", 1.));
        stocks.add(new Stock("GOOG", 1.));
        stocks.add(new Stock("IBM", 1.));
    }

    @Autowired
    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Autowired
    public void setSimpleMessagingTemplate(SimpMessagingTemplate simpleMessagingTemplate) {
        this.simpleMessagingTemplate = simpleMessagingTemplate;
    }

    @MessageMapping("/addstock")
    public void addStock(Stock stock) {
        stocks.add(stock);
        broadcastUpdatedPrices();
    }

    private void broadcastUpdatedPrices() {
        for (Stock stock : stocks) {
            stock.setPrice(stock.getPrice() + getUpdatedPrice() * stock.getPrice());
            stock.setDate(new Date());
        }
        simpleMessagingTemplate.convertAndSend("/topic/price", stocks);
    }

    private double getUpdatedPrice() {
        double priceChange = random.nextDouble() * 5.;
        if (random.nextInt(2) == 1) {
            priceChange = -priceChange;
        }
        return priceChange / 100.;
    }

    @PostConstruct
    public void broadcastPeriodically() {
        taskScheduler.scheduleAtFixedRate(this::broadcastUpdatedPrices, 1000);
    }

    @GetMapping("/rates")
    public String showChat() {
        return "chat/rates";
    }
}
