package com.microservice.currencyconversionservice.fiegn;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.currencyconversionservice.model.ConversionValue;


//@FeignClient(name="currency-exchange-service", url="localhost:8000")
@FeignClient(name="zuulgateway")
@RibbonClient(name="currency-exchange-service")
public interface ExchangeServiceProxy {

	//@GetMapping("/currency-exchange-service/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public ConversionValue getConvertionVal(@PathVariable("from") String from, @PathVariable("to") String to);
}
