package ir.corestockexchanging.controller;

import ir.corestockexchanging.model.ApplicationFacade;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.DepositRequest;
import ir.corestockexchanging.model.domain.Instrument;
import ir.corestockexchanging.model.service.CustomerService;
import ir.corestockexchanging.model.service.impl.CustomerServiceImpl;
import ir.corestockexchanging.viewmodel.DepositResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by Ali on 03/13/2016.
 */


@Controller
public class Controllers {

    @RequestMapping("/")
    public String home() {
        return "spa";
    }



//    @RequestMapping(value = "/rest/instruments")
//    @ResponseBody
//    public Customer getInstrumentsJson(@PathVariable("id") long id) {
//        return customerService.getCustomerById(id);
//    }
/*
    @RequestMapping(value = urlPattern , method = RequestMethod.POST)
    public @ResponseBody Person save(@RequestBody Person jsonString) {

        Person person=personService.savedata(jsonString);
        return person;
    }
    */
}