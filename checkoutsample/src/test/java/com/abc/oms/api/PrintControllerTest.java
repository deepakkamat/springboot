package com.abc.oms.api;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abc.oms.api.PrintController;
import com.abc.oms.app.model.OrderItem;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.service.OrderServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)

public class PrintControllerTest {

	@Mock
	private OrderServiceImpl orderService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private PrintController printControllerTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(printControllerTest).build();

	}

	@Test
	public void testPrintWithNoOrder() throws Exception {

		Mockito.when(orderService.getOrder(any(String.class))).thenReturn(null);

		this.mockMvc.perform(get("/oms/bill.xhtml?orderNo=123").header("Accept-Language", "en_us")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

	}

	@Test
	public void testPrint() throws Exception {
		PurchaseOrder order = new PurchaseOrder();
		OrderItem orderItem = new OrderItem();

		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem);
		Mockito.when(orderService.getOrder(any(String.class))).thenReturn(order);
		Mockito.when(orderService.getOrderItems(any(String.class))).thenReturn(orderItems);

		this.mockMvc.perform(get("/oms/bill.xhtml?orderNo=123").header("Accept-Language", "en_us")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}