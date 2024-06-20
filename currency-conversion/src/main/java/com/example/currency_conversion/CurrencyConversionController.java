package com.example.currency_conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);
        ResponseEntity<CurrencyConversionBean> conversionBeanResponseEntity =
                          new RestTemplate().
                        getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversionBean.class,uriVariables);
        CurrencyConversionBean currencyConversionBean =conversionBeanResponseEntity.getBody();
        return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,quantity,
                currencyConversionBean.getConversionMultiple(),quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getEnv());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        CurrencyConversionBean currencyConversionBean =proxy.retrieveExchangeValue(from,to);
        return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,quantity,
                currencyConversionBean.getConversionMultiple(),quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getEnv());
    }

}
