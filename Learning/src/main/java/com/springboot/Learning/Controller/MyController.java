package com.springboot.Learning.Controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.springboot.Learning.Model.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@RestController
public class MyController {

    @Autowired
    private UserDaoService service;
    private MessageSource messageSource;

    public MyController(MessageSource messageSource){
       this.messageSource =messageSource;
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/helloWorldBean")
    public HelloWorld helloWorldBean() {
        return new HelloWorld("hey");
    }

    @GetMapping("/helloWorldBean/{id}")
    public HelloWorld helloWorldBeanpath(@PathVariable Long id ) {

        return new HelloWorld("PATH");
    }

    @PostMapping("/createUsers")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = service.save(user);
        // CREATED
        // /user/{id}     savedUser.getId()

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    //implementing hateos
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if(user==null){
            throw new UserNotFoundException("id" + id);
        }
        EntityModel<User> entityModel =EntityModel.of(user);
        WebMvcLinkBuilder builder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
        entityModel.add(builder.withRel("all-users"));
        return entityModel;
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null,
                locale);
    }

    /*@GetMapping("/users")
    public List<User> getAllUser() {
        return service.findAll();
    }*/

    @GetMapping("/users")
    public MappingJacksonValue getAllUser() {

        MappingJacksonValue value = new MappingJacksonValue(service.findAll());
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<>()));
        value.setFilters(filters);
        return value;
    }
}
