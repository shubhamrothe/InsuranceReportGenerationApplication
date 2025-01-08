package com.reports.services;

import java.util.List;

import com.reports.entities.CitizenPlan;
import com.reports.rrquest.SearchRequest;

public interface ReportService {

	List<String> getPlanNames();
	
	List<String> getPlanStatuses();
	
	List<CitizenPlan> search(SearchRequest request);
	
	boolean exportExcel();
	
	boolean exportPdf();
}

