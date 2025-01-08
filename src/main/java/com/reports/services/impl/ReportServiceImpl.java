package com.reports.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.reports.entities.CitizenPlan;
import com.reports.repositories.CitizenPlanRepo;
import com.reports.rrquest.SearchRequest;
import com.reports.services.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepo repo;

	@Override
	public List<String> getPlanNames() {
		return this.repo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		return this.repo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		
		CitizenPlan entity= new CitizenPlan();
	//	BeanUtils.copyProperties(request, entity);
	 	if(null!=request.getPlanName() && !"".equals(request.getPlanName())) {
	 		entity.setPlanName(request.getPlanName());
	 	}
	 	if(null!=request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
	 		entity.setPlanStatus(request.getPlanStatus());
	 	}
	 	if(null!=request.getGender() && !"".equals(request.getGender())) {
	 		entity.setGender(request.getGender());
	 	} 
	 	if(null!=request.getStartDate() && !"".equals(request.getStartDate())) {
	 		String startDate = request.getStartDate();

	 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 		//convert String to LocalDate
	 		LocalDate localDate =LocalDate.parse(startDate, formatter);
	 		entity.setPlanStartDate(localDate);
	 	}
	 	if(null!=request.getEndDate() && !"".equals(request.getEndDate())) {
	 		String endDate = request.getEndDate();

	 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 		//convert String to LocalDate
	 		LocalDate localDate =LocalDate.parse(endDate, formatter);
	 		entity.setPlanEndDate(localDate);
	 	}
		Example<CitizenPlan> of = Example.of(entity);
		return this.repo.findAll(of);
	}

	@Override
	public boolean exportExcel() {
		return false;
	} 

	@Override
	public boolean exportPdf() {
		return false;
	}

}
