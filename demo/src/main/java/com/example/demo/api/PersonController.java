package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * this class is the connector from client to server (method from GUI will arrive here)
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET)
    public String logOut() {
//        JSONObject temp = param;
//        param.names();
        return "success";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET, value = "/AddLeagueRankPolicyForm")
    public String  AddLeagueRankPolicyForm(@RequestParam String param) {
        return "success access";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/addPerson")
    public void addPerson( Person person){
        personService.addPerson(person);
    }

}
