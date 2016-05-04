package ir.corestockexchanging.model.dao;

import ir.corestockexchanging.model.domain.Customer;

import java.util.List;

/**
 * Created by Ali on 03/27/2016.
 */
public interface CustomerDao {
     List<Customer> getCustomersList();

     void addCustomer(Customer Customer);

     Customer findById(long id);

     void delete(long id);

}
