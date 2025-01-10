package com.reports.utils;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.reports.entities.CitizenPlan;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {

	public void generate(HttpServletResponse response, List<CitizenPlan> plans) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		//Creating font
		//Setting font style and size
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		//Creating Paragraph
		Paragraph p= new Paragraph("Citizen's Plan Information", fontTitle);
		//Aligning the paragraph in document
		p.setAlignment(Paragraph.ALIGN_CENTER);
		//Adding created paragraph in document
		document.add(p);	
		
		PdfPTable table = new PdfPTable(8);
		table.setSpacingBefore(10);
		table.addCell("Id");
		table.addCell("Holder Name");
		table.addCell("Gender");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Plan StartDate");
		table.addCell("Plan EndDate");
		table.addCell("Benefit Amount");
		
		//List<CitizenPlan> plans = this.repo.findAll();
		
		for(CitizenPlan plan:plans) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getGender());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			table.addCell(plan.getPlanStartDate() +" ");
			table.addCell(plan.getPlanEndDate()+" ");
			table.addCell(String.valueOf(plan.getBenefitAmt()));	
		}
		document.add(table);
		document.close();
	}
}
