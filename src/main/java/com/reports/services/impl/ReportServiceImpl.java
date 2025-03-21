package com.reports.services.impl;

import java.io.File;
import java.io.IOException;
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
import com.reports.utils.EmailUtils;
import com.reports.utils.ExcelGenerator;
import com.reports.utils.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepo repo;
	
	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtils emailUtils;
	
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
	public boolean exportExcel(HttpServletResponse response) throws Exception {		
		File f = new File("Plans.xls");
		
		List<CitizenPlan> plans = this.repo.findAll();
		this.excelGenerator.generate(response, plans, f);
		
		String subject ="Test mail subject";
		String body="<h1>Test mail body</h1>";
		String to ="shubhamrothe005@gmail.com";
		
		emailUtils.sendEmail(subject, body, to,f);
		
		f.delete();
		return true;
	} 

	@Override
	public boolean exportPdf(HttpServletResponse response) throws IOException {
		File f = new File("Plans.pdf"); 
		
		List<CitizenPlan> plans = this.repo.findAll();
		this.pdfGenerator.generate(response, plans, f);
		String subject ="Test mail subject";
		String body="<h1>Test mail body</h1>";
		String to ="shubhamrothe005@gmail.com";
		
		emailUtils.sendEmail(subject, body, to, f);
		
		f.delete();
		return true;
	}

}
