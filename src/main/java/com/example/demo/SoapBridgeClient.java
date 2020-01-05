package com.example.demo;

import com.example.demo.exception.DemoException;
import com.example.demo.soap.customer.GetCustomerRequest;
import com.example.demo.soap.customer.GetCustomerResponse;
import com.example.demo.soap.order.OrderProductsRequest;
import com.example.demo.soap.order.OrderProductsResponse;
import com.example.demo.soap.products.GetProductsRequest;
import com.example.demo.soap.products.GetProductsResponse;

public interface SoapBridgeClient {

	GetCustomerResponse getCustomer(String url, GetCustomerRequest getCustomerRequest) throws DemoException;

	GetProductsResponse getProducts(String url, GetProductsRequest getProductsRequest) throws DemoException;

	OrderProductsResponse submitOrder(String url, OrderProductsRequest orderProductsRequest) throws DemoException;

}