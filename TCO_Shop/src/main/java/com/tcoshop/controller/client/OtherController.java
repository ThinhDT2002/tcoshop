package com.tcoshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class OtherController {
    
	@RequestMapping("/blog")
	public String Blog() {
		return "tco-client/other/blog-full-width.html";
	}

    @RequestMapping("/faq")
	public String Faq() {
		return "tco-client/other/faq.html";
	}

    @RequestMapping("/offer")
	public String Offer() {
		return "tco-client/other/offer.html";
	}

    @RequestMapping("/coming-soon")
	public String ComingSoon() {
		return "tco-client/other/coming-soon.html";
	}

    @RequestMapping("/error-page")
	public String Error() {
		return "tco-client/other/error-page.html";
	}

	@RequestMapping("/about-us")
	public String AboutUs() {
		return "tco-client/other/about-us.html";
	}
}
