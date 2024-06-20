package com.springboot.Learning.Controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/f")
public class FilterController {

    //dynamic filtering
    @GetMapping("/filter")
    public MappingJacksonValue filtering(@RequestParam(required = false) boolean param){
        User user =  new User(1,"Chriso",new Date());
        MappingJacksonValue value = new MappingJacksonValue(user);
        if(param) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("birthDate");
            FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
            value.setFilters(filters);
        }
        return value;
    }

    @GetMapping("/filter2")
    public MappingJacksonValue filter2(@RequestParam(required = false) boolean param){
        User user =  new User(1,"Chriso",new Date());
        MappingJacksonValue value = new MappingJacksonValue(user);

        if(param) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("student_id");
            FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
            value.setFilters(filters);
        }
        return value;
    }
}
