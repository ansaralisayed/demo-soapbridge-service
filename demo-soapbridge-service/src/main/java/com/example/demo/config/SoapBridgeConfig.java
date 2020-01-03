package com.example.demo.config;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.support.KeyManagersFactoryBean;
import org.springframework.ws.soap.security.support.KeyStoreFactoryBean;
import org.springframework.ws.soap.security.support.TrustManagersFactoryBean;

import com.example.demo.SoapServiceMessageSender;

@Configuration
public class SoapBridgeConfig {

	@Value("${http.auth.userid}")
	private String userId;
	@Value("${http.auth.password}")
	private String password;
	@Value("${http.connection.timeout}")
	private int soapConnectionTimeout;
	@Value("${http.read.timeout}")
	private int soapReadTimeout;
	@Value("classpath:${soap.service.truststore.location}")
	private Resource trustStoreLocation;
	@Value("${soap.service.truststore.password}")
	private String trustStorePassword;
	@Value("${soap.service.keystore.password}")
	private String keyStorePpassword;

	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.example.demo.soap.category",
									"com.example.demo.soap.customer",
									"com.example.demo.soap.order",
									"com.example.demo.soap.product",
									"com.example.demo.soap.products");
		
		return marshaller;
	}
	
	@Bean
	public WebServiceTemplate getWebServiceTemplate() throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate(soapMessageFactory());
		webServiceTemplate.setMarshaller(marshaller());
		webServiceTemplate.setUnmarshaller(marshaller());
		
		SoapServiceMessageSender messageSender = new SoapServiceMessageSender();
		
		messageSender.setTrustManagers(trustManagers());
//		messageSender.setKeyManagers(keyManagers());
		messageSender.setSslSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
		
		messageSender.setUserId(userId);
		messageSender.setPassword(password);
		messageSender.setSoapConnectionTimeout(soapConnectionTimeout);
		messageSender.setSoapReadTimeout(soapReadTimeout);
		
		webServiceTemplate.setMessageSender(messageSender);
		return webServiceTemplate;
	}
	
	private KeyManager[] keyManagers() throws Exception {
		KeyManagersFactoryBean factoryBean = new KeyManagersFactoryBean();
		factoryBean.setPassword(keyStorePpassword);
		factoryBean.setKeyStore(keyStore());
		factoryBean.afterPropertiesSet();
		
		return factoryBean.getObject();
	}

	private KeyStore keyStore() throws GeneralSecurityException, IOException {
		KeyStoreFactoryBean factoryBean = new KeyStoreFactoryBean();
		factoryBean.setType(KeyStore.getDefaultType());
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	private TrustManager[] trustManagers() throws Exception {
		TrustManagersFactoryBean factoryBean = new TrustManagersFactoryBean();
		factoryBean.setKeyStore(trustStore());
		factoryBean.afterPropertiesSet();
		
		return factoryBean.getObject();
	}

	private KeyStore trustStore() throws GeneralSecurityException, IOException {
		KeyStoreFactoryBean factoryBean = new KeyStoreFactoryBean();
		factoryBean.setLocation(trustStoreLocation);
		factoryBean.setPassword(trustStorePassword);
		factoryBean.setType(KeyStore.getDefaultType());
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	public SaajSoapMessageFactory soapMessageFactory() {
		SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory();
		saajSoapMessageFactory.afterPropertiesSet();
		return saajSoapMessageFactory;
	}
}
