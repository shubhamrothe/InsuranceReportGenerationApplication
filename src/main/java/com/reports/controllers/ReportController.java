package com.reports.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.reports.entities.CitizenPlan;
import com.reports.rrquest.SearchRequest;
import com.reports.services.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());
		init(model);
		return "index"; 
	}
	
	private void init(Model model) {
		//model.addAttribute("search", new SearchRequest());
		model.addAttribute("names", this.service.getPlanNames());
		model.addAttribute("status", this.service.getPlanStatuses());
	}
	


	@PostMapping("/search")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		//System.out.println(request);
		List<CitizenPlan> plans = this.service.search(search);
		model.addAttribute("plans", plans);
		init(model); 
		return "index" ;
	}
	
	
}
