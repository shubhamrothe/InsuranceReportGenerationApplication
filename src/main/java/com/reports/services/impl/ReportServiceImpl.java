package com.reports.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.reports.entities.CitizenPlan;
import com.reports.repositories.CitizenPlanRepo;
import com.reports.rrquest.SearchRequest;
import com.reports.services.ReportService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

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
	public boolean exportExcel(HttpServletResponse response) throws IOException {		
		Workbook workbook = new HSSFWorkbook(); //to create document or  use XSSFWorkbook
		Sheet sheet = workbook.createSheet("plan-data");
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Holder Name");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("Plan Name");
		headerRow.createCell(4).setCellValue("Plan Status");
		//headerRow.createCell(5).setCellValue("");
		headerRow.createCell(5).setCellValue("Start Date");
		headerRow.createCell(6).setCellValue("End Date");
		headerRow.createCell(7).setCellValue("Benefit Amount");
		
		List<CitizenPlan> records = this.repo.findAll();
		int dataRowIndex=1;
		for(CitizenPlan plan : records) {
			Row dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getGender());
			dataRow.createCell(3).setCellValue(plan.getPlanName());
			dataRow.createCell(4).setCellValue(plan.getPlanStatus());
			if(null!=plan.getPlanStartDate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanStartDate() +" ");
			}else {
				dataRow.createCell(5).setCellValue("N/A");
			}
			if(null!=plan.getPlanEndDate()) {
				dataRow.createCell(6).setCellValue(plan.getPlanEndDate()+" ");
			}else {
				dataRow.createCell(6).setCellValue("N/A");
			}
			//dataRow.createCell(7).setCellValue(plan.getBenefitAmt());// if value is not allowed then by default it stored null, so we get null pointer exception
			if(null != plan.getBenefitAmt()) {
				dataRow.createCell(7).setCellValue(plan.getBenefitAmt());
			}else {
				dataRow.createCell(7).setCellValue("N/A");
			}
			
			dataRowIndex++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		return true;
	} 

	@Override
	public boolean exportPdf() {
		return false;
	}

}
