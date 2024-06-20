package com.springboot.Learning.Controller;

import com.springboot.Learning.Model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping("v1/person")
    public Person personV1() {
        return new Person("Bob Charlie");
    }

    @GetMapping("v2/person")
    public PersonV2 personV2() {
        return new PersonV2("Bob", "Charlie");
    }

    @GetMapping(path="v2/person",params = "version=1")
    public PersonV2 personV2RequestParam() {
        return new PersonV2("Bob", "request param");
    }
}
