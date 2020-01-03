package com.example.demo.converter;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.api.CartEntryInfo;
import com.example.demo.api.CategoryInfo;
import com.example.demo.api.CustomerInfo;
import com.example.demo.api.OrderProductsInfo;
import com.example.demo.api.OrderProductsResponseInfo;
import com.example.demo.api.ProductInfo;
import com.example.demo.soap.category.Category;
import com.example.demo.soap.customer.GetCustomerRequest;
import com.example.demo.soap.customer.GetCustomerResponse;
import com.example.demo.soap.order.CartEntry;
import com.example.demo.soap.order.OrderProductsRequest;
import com.example.demo.soap.order.OrderProductsResponse;
import com.example.demo.soap.product.Product;
import com.example.demo.soap.products.GetProductsRequest;
import com.example.demo.soap.products.GetProductsResponse;

public class OrderMgmtConverter {

	public static GetCustomerRequest convertToGetCustomerRequest(String customerId) {
		GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
		getCustomerRequest.setId(customerId);
		
		return getCustomerRequest;
	}
	
	public static CustomerInfo convertToCustomerInfo(GetCustomerResponse getCustomerResponse) {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setId(getCustomerResponse.getCustomer().getId());
		customerInfo.setName(getCustomerResponse.getCustomer().getName());
		customerInfo.setCategory(CategoryInfo.fromValue(getCustomerResponse.getCustomer().getCategory().value()));
		customerInfo.setAge(getCustomerResponse.getCustomer().getAge());
		
		return customerInfo;
	}
	
	public static GetProductsRequest convertToGetProductsRequest(String customerId, String category) {
		GetProductsRequest request = new GetProductsRequest();
		request.setCustomerId(customerId);
		request.setCategory(Category.fromValue(category));
		
		return request;
	}

	public static List<ProductInfo> convertToProductsInfo(GetProductsResponse response) {
		List<ProductInfo> productInfos = new ArrayList<>();
		ProductInfo productInfo;
		
		for(Product product: response.getProducts()) {
			productInfo = new ProductInfo();
			productInfo.setId(product.getId());
			productInfo.setName(product.getName());
			productInfo.setYear(product.getYear());
			productInfo.setCategory(CategoryInfo.fromValue(product.getCategory().value()));
			
			productInfos.add(productInfo);
		}
		return productInfos;
	}

	public static OrderProductsRequest convertToOrderProductsRequest(OrderProductsInfo orderProductsInfo) {
		OrderProductsRequest request = new OrderProductsRequest();
		
		request.setCustomerId(orderProductsInfo.getCustomerId());
		
		List<CartEntry> cartEntries = new ArrayList<>();
		CartEntry cartEntry = null;
		Product product = null;
		
		for(CartEntryInfo cartEntryInfo: orderProductsInfo.getCartEntries()) {
			cartEntry = new CartEntry();
			cartEntry.setQuantity(cartEntryInfo.getQuantity());
			
			product = new Product();
			product.setId(cartEntryInfo.getProduct().getId());
			product.setName(cartEntryInfo.getProduct().getName());
			product.setYear(cartEntryInfo.getProduct().getYear());
			product.setCategory(Category.fromValue(cartEntryInfo.getProduct().getCategory().value()));
			
			cartEntry.setProduct(product);
			cartEntries.add(cartEntry);
		}
		
		request.getCartEntries().addAll(cartEntries);
		
		return request;
	}

	public static OrderProductsResponseInfo convertToOrderProductsResponseInfo(OrderProductsResponse response) {
		OrderProductsResponseInfo responseInfo = new OrderProductsResponseInfo();
		
		responseInfo.setOrderReference(response.getOrderReference());
		responseInfo.setMessage(response.getMessage());
		
		return responseInfo;
	}
}
