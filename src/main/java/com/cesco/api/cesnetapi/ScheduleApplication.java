package com.cesco.api.cesnetapi;

import java.sql.SQLException;

import com.cesco.api.cesnetapi.res.services.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class ScheduleApplication {

    @Autowired
    private RestaurantService restaurantService;
	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

	@Scheduled(cron = "0 32 13 * * ?") // 스케쥴러 실행 일시
	public void scheduleTaskUsingCronExpression() throws SQLException { 
		restaurantService.setDailySalesList();
		// restaurantService.setFranchisesList();
		//restaurantService.setItemListList();
		restaurantService.setSalesDtlList();
		restaurantService.setSalesreceiptList();
		restaurantService.setSalesreceiptDtlList();
		restaurantService.setPrepaidCardSale();
		restaurantService.setPrepaidCardSaleDtl();
	}
}
