package ir.corestockexchanging.model.service;

import ir.corestockexchanging.model.domain.Customer;

/**
 * Created by Ali on 05/01/2016.
 */
public interface CustomerService {
    Customer getCustomerById(long id);
}
