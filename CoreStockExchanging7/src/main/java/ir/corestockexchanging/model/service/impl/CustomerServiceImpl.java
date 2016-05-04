package ir.corestockexchanging.model.service.impl;

import ir.corestockexchanging.model.dao.CustomerDao;
import ir.corestockexchanging.model.dao.impl.CustomerDaoImpl;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.service.CustomerService;

/**
 * Created by Ali on 05/01/2016.
 */
public class CustomerServiceImpl implements CustomerService {
    public Customer getCustomerById(long id) {
        CustomerDao customerDao = new CustomerDaoImpl();
        return customerDao.findById(id);
    }
}
