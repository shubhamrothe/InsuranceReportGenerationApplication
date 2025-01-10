package com.reports.services;

import java.util.List;

import com.reports.entities.CitizenPlan;
import com.reports.rrquest.SearchRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	List<String> getPlanNames();
	
	List<String> getPlanStatuses();
	
	List<CitizenPlan> search(SearchRequest request);
	
	boolean exportExcel(HttpServletResponse response) throws Exception;
	
	boolean exportPdf();
}

