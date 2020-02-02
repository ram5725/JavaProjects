package com.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.currencyconversionservice.fiegn.ExchangeServiceProxy;
import com.microservice.currencyconversionservice.model.ConversionValue;

@EnableFeignClients(basePackages="com.microservice.currencyconversionservice")
@RestController
public class ConversionController {
	
	@Autowired
	private ExchangeServiceProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ConversionValue getConvertionVal(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
		HashMap<String, String> map =new HashMap<>();
		map.put("from", from);
		map.put("to", to);
		ResponseEntity<ConversionValue> respEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange-service/from/{from}/to/{to}", ConversionValue.class, map);
		//return new ConversionValue(0l, from, to, BigDecimal.valueOf(6500), quantity, quantity, 0);
		ConversionValue body = respEntity.getBody();
		body.setQuantity(quantity);
		body.setTotalAmount(quantity.multiply(body.getConvMultiple()));
		return respEntity.getBody();
	}
	@GetMapping("/currency-conversion-fiegn/from/{from}/to/{to}/quantity/{quantity}")
	public ConversionValue getConvertionValue(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
		
		ConversionValue body = proxy.getConvertionVal(from, to);
		body.setQuantity(quantity);
		body.setTotalAmount(quantity.multiply(body.getConvMultiple()));
		System.out.println("from feign....");
		return body;
	}
	

}
