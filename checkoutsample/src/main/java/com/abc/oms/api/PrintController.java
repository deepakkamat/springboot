package com.abc.oms.api;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.oms.service.OrderServiceImpl;

/**
 * MVC Controller used for Printing the order bill
 */
@RequestMapping("/oms")
@Controller
public class PrintController {

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Print Bill 
	 * @param model
	 * @param locale
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/bill.xhtml" )
	String print(Model model, Locale locale, @RequestParam("orderNo") String orderNo) {
		if (orderService.getOrder(orderNo) == null) {
			String param[] = { orderNo };
			throw new ResourceNotFoundException(messageSource.getMessage("order.not.found", param, locale));
		}
		model.addAttribute("order", orderService.getOrder(orderNo));
		model.addAttribute("bill", orderService.getOrderItems(orderNo));
		return "bill";
	}

}