package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.CustomerInfo;
import com.example.demo.api.OrderProductsInfo;
import com.example.demo.api.OrderProductsResponseInfo;
import com.example.demo.api.ProductInfo;
import com.example.demo.exception.DemoException;
import com.example.demo.service.SoapBridgeService;

@RestController
@RequestMapping("v1")
public class SoapBridgeController {
	
	@Autowired
	private SoapBridgeService service;

	@GetMapping("/ordermgmt/customer")
	public CustomerInfo getCustomer(@RequestParam String customerId) throws DemoException {
		return service.getCustomer(customerId);
	}
	
	@GetMapping("/ordermgmt/products")
	public List<ProductInfo> getProducts(@RequestParam String customerId, @RequestParam String category) throws DemoException {
		return service.getProducts(customerId, category);
	}

	@PostMapping("/ordermgmt/order")
	public OrderProductsResponseInfo submitOrder(@RequestBody OrderProductsInfo orderProductsInfo) throws DemoException {
		return service.submitOrder(orderProductsInfo);
	}

}
