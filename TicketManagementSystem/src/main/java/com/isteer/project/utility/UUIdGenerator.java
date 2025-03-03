// UUIdGenerator.java
package com.isteer.project.utility;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class UUIdGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private int counter = 0;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "SELECT counter FROM IDCounter";
        try {
            counter = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    private void persistCounter() {
        try {
            jdbcTemplate.update("UPDATE IDCounter SET counter = ?", counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateShortID() {
        synchronized (this) {
            counter = (counter + 1) % 1000;
        }

        long timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String formattedTimestamp = sdf.format(new Date(timestamp));

        char randomChar = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()));
        String shortCounter = String.format("%03d", counter);

        return formattedTimestamp.substring(formattedTimestamp.length() - 4)
                + randomChar
                + shortCounter.charAt(shortCounter.length() - 1);
    }

    @PreDestroy
    public void onDestroy() {
        persistCounter();
    }
}