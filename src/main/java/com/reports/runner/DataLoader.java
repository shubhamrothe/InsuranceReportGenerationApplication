//package com.reports.runner;
//
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.reports.entities.CitizenPlan;
//import com.reports.repositories.CitizenPlanRepo;
//
//@Component
//public class DataLoader implements ApplicationRunner {
//
//	@Autowired
//	private CitizenPlanRepo repo;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//
//		//Cash Plan Data
//		CitizenPlan c1= new CitizenPlan();
//		c1.setCitizenName("John");
//		c1.setGender("Male");
//		c1.setPlanName("Cash");
//		c1.setPlanStatus("Aproved");
//		c1.setPlanStartDate(LocalDate.now());
//		c1.setPlanEndDate(LocalDate.now().plusMonths(6));
//		c1.setBenefitAmt(5000.00);
//	}
//
//}
