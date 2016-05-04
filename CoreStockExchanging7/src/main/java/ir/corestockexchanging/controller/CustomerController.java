package ir.corestockexchanging.controller;

import ir.corestockexchanging.model.ApplicationFacade;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.DepositRequest;
import ir.corestockexchanging.model.service.CustomerService;
import ir.corestockexchanging.model.service.impl.CustomerServiceImpl;
import ir.corestockexchanging.viewmodel.DepositResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ali on 03/21/2016.
 */
//Customer Request Mapping
@Controller
public class CustomerController {
//TODO use autowired instead of new


    CustomerService customerService = new CustomerServiceImpl();

    @RequestMapping(value = "/rest/customer/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Customer getCustomerJson(@PathVariable("id") long id) {
        return customerService.getCustomerById(id);
    }


    @RequestMapping(value = "/rest/customer/deposit/", method = RequestMethod.PUT, produces = {"application/json; charset=utf-8"})
    @ResponseBody
    public DepositResponse customerDepositService(@RequestBody DepositRequest depositRequest) {
        ApplicationFacade.addDepositRequest(depositRequest);
        //TODO: What about admin confirmation in this phase
        ApplicationFacade.confirmDepositRequest(depositRequest.getId());
        DepositResponse depositResponse = new DepositResponse();

        Customer customer = ApplicationFacade.getCustomer(depositRequest.getUserId());
        depositResponse.setCurrentCredit(customer.getCredit());
        depositResponse.setSuccessful(true);
        return depositResponse;
    }
}
