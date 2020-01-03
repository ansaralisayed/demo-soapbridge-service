package com.example.demo;

import com.example.demo.api.CartEntryInfo;
import com.example.demo.api.CategoryInfo;
import com.example.demo.api.OrderProductsInfo;
import com.example.demo.api.ProductInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonProcessingException {
		OrderProductsInfo opi = new OrderProductsInfo();
		opi.setCustomerId("1");
		CartEntryInfo cartEntryInfo = new CartEntryInfo();
		cartEntryInfo.setQuantity(100);
		ProductInfo pi = new ProductInfo();
		pi.setCategory(CategoryInfo.DOMESTIC);
		pi.setId("1");
		pi.setName("Dzire");
		pi.setYear(2015);
		cartEntryInfo.setProduct(pi);
		opi.getCartEntries().add(cartEntryInfo);
		
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(opi));
	}
}
