package com.example.demo;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

public class SoapServiceMessageSender extends HttpsUrlConnectionMessageSender {

	private String userId;
	private String password;
	private int soapConnectionTimeout;
	private int soapReadTimeout;
	
	protected void prepareConnection(HttpURLConnection connection) throws IOException {
		byte[] authorization = Base64.encodeBase64(new StringBuilder(userId).append(":").append(password).toString().getBytes());
		connection.setRequestProperty("Authorize", "Basic " + new String(authorization));
		connection.setConnectTimeout(soapConnectionTimeout);
		connection.setReadTimeout(soapReadTimeout);
		super.prepareConnection(connection);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSoapReadTimeout() {
		return soapReadTimeout;
	}

	public void setSoapReadTimeout(int soapReadTimeout) {
		this.soapReadTimeout = soapReadTimeout;
	}

	public int getSoapConnectionTimeout() {
		return soapConnectionTimeout;
	}

	public void setSoapConnectionTimeout(int soapConnectionTimeout) {
		this.soapConnectionTimeout = soapConnectionTimeout;
	}
	
	
	
}
