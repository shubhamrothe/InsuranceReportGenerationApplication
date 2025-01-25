package com.reports.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.reports.entities.CitizenPlan;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExcelGenerator {


	@SuppressWarnings("resource")
	public void generate(HttpServletResponse response, List<CitizenPlan> records, File file) throws Exception {
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
		
		//List<CitizenPlan> records = this.repo.findAll();
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
 
		//to send file to current folder(in project structure)
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		
		//send file to browser
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		
		workbook.close();
	}
}
