package com.develop_mouse.gummy_dang.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProbeController {

	@RequestMapping("/probe")
	public String probe() {
		return "dev-rat!";
	}

}
