package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.demo.exception.DemoException;
import com.example.demo.exception.handler.message.DemoError;
import com.example.demo.soap.customer.GetCustomerRequest;
import com.example.demo.soap.customer.GetCustomerResponse;
import com.example.demo.soap.order.OrderProductsRequest;
import com.example.demo.soap.order.OrderProductsResponse;
import com.example.demo.soap.products.GetProductsRequest;
import com.example.demo.soap.products.GetProductsResponse;

@Service
public class SoapBridgeClientImpl implements SoapBridgeClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	/* (non-Javadoc)
	 * @see com.example.demo.SoapBridgeClient#getCustomer(java.lang.String, com.example.demo.soap.customer.GetCustomerRequest)
	 */
	@Override
	public GetCustomerResponse getCustomer(String url, GetCustomerRequest getCustomerRequest) throws DemoException {
		GetCustomerResponse getCustomerResponse = null;
		
		try {
			getCustomerResponse = (GetCustomerResponse) this.webServiceTemplate.marshalSendAndReceive(url, getCustomerRequest);
		} catch(WebServiceTransportException wte) {
			throw new DemoException(DemoError.DEMO002.getCode(), wte.getMessage());
		}
		
		return getCustomerResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.example.demo.SoapBridgeClient#getProducts(java.lang.String, com.example.demo.soap.products.GetProductsRequest)
	 */
	@Override
	public GetProductsResponse getProducts(String url, GetProductsRequest getProductsRequest) throws DemoException {
		GetProductsResponse response = null;
		try{	
			response = (GetProductsResponse) this.webServiceTemplate.marshalSendAndReceive(url, getProductsRequest);
		} catch(WebServiceTransportException wte) {
			throw new DemoException(DemoError.DEMO002.getCode(), wte.getMessage());
		}

		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.example.demo.SoapBridgeClient#submitOrder(java.lang.String, com.example.demo.soap.order.OrderProductsRequest)
	 */
	@Override
	public OrderProductsResponse submitOrder(String url, OrderProductsRequest orderProductsRequest) throws DemoException {
		OrderProductsResponse response = null;
		try{	
			response = (OrderProductsResponse) this.webServiceTemplate.marshalSendAndReceive(url, orderProductsRequest);
		} catch(WebServiceTransportException wte) {
			throw new DemoException(DemoError.DEMO002.getCode(), wte.getMessage());
		}

		return response;
	}
	
}
