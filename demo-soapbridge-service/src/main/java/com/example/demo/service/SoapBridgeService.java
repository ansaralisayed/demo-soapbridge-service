package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.SoapBridgeClient;
import com.example.demo.api.CustomerInfo;
import com.example.demo.api.OrderProductsInfo;
import com.example.demo.api.OrderProductsResponseInfo;
import com.example.demo.api.ProductInfo;
import com.example.demo.converter.OrderMgmtConverter;
import com.example.demo.exception.DemoException;
import com.example.demo.soap.customer.GetCustomerRequest;
import com.example.demo.soap.customer.GetCustomerResponse;
import com.example.demo.soap.order.OrderProductsRequest;
import com.example.demo.soap.order.OrderProductsResponse;
import com.example.demo.soap.products.GetProductsRequest;
import com.example.demo.soap.products.GetProductsResponse;

@Service
public class SoapBridgeService {

	@Autowired
	private SoapBridgeClient client;
	
	@Value("${customer.soap.service.url}")
	private String CUSTOMER_SERVICE_URL;

	@Value("${product.soap.service.url}")
	private String PRODUCT_SERVICE_URL;

	@Value("${order.soap.service.url}")
	private String ORDER_SERVICE_URL;

	public CustomerInfo getCustomer(String customerId) throws DemoException {
		GetCustomerRequest request = OrderMgmtConverter.convertToGetCustomerRequest(customerId);

		GetCustomerResponse getCustomerResponse = client.getCustomer(CUSTOMER_SERVICE_URL, request);
		
		return OrderMgmtConverter.convertToCustomerInfo(getCustomerResponse);
	}
	
	public List<ProductInfo> getProducts(String customerId, String category) throws DemoException {
		GetProductsRequest request = OrderMgmtConverter.convertToGetProductsRequest(customerId, category);
		
		GetProductsResponse response = client.getProducts(PRODUCT_SERVICE_URL, request);

		return OrderMgmtConverter.convertToProductsInfo(response);
	}

	public OrderProductsResponseInfo submitOrder(OrderProductsInfo orderProductsInfo) throws DemoException {
		OrderProductsRequest request = OrderMgmtConverter.convertToOrderProductsRequest(orderProductsInfo);
		
		OrderProductsResponse response = client.submitOrder(ORDER_SERVICE_URL, request);
		
		return OrderMgmtConverter.convertToOrderProductsResponseInfo(response);
	}


}
