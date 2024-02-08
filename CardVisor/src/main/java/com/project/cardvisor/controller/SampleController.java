package com.project.cardvisor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping("/sample")
	public String f1() {
		return "hellodddd";
	}
}
