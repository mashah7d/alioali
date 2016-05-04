package ir.corestockexchanging.model.dao.impl;

import ir.corestockexchanging.model.dao.CustomerDao;
import ir.corestockexchanging.model.dao.impl.helper.IndexFinder;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.Share;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.util.List;

/**
 * Created by Ali on 03/27/2016.
 */
public class CustomerDaoImpl implements CustomerDao {
    private static long nextId = 2;

    public CustomerDaoImpl() {
        Repository.customers.add(new Customer(1, "admin", "admin"));
    }

    @Override
    public List<Customer> getCustomersList() {
        return Repository.customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        customer.setId(nextId);
        nextId += 1;
        Repository.customers.add(customer);
    }

    static {
        //dummyData
        Customer a = new Customer(2, "Gholi", "Gholami");
        a.addActiveSell(new SellOrder(2, 1, 10, 4, "GTC")); //1 irankhodro 10toman 4
        a.addActiveBuy(new BuyOrder(2, 3, 200, 2, "GTC"));
        a.addActiveBuy(new BuyOrder(2, 2, 1 , 6, "GTC"));
        a.addShare(new Share(1, 2, 7));
        a.deposit(1000);
        Customer b = new Customer(3, "Reza", "Farzi");
        b.addActiveSell(new SellOrder(3, 2, 8, 2, "GTC")); //2 saipa 10 toman 2ta
        b.addActiveSell(new SellOrder(3, 1, 16, 4, "GTC")); //1 irankhodro 16 toman 4ta
        b.addShare(new Share(2, 3, 7)); //
        b.addShare(new Share(1, 3, 10)); //
        b.deposit(500);
        Customer c = new Customer(4, "Abbas", "Monazzam");
        c.addShare(new Share(3, 4, 4)); //3 teta 4ta
        c.deposit(2000);
        nextId = 5;
        Repository.customers.add(a);
        Repository.customers.add(b);
        Repository.customers.add(c);
    }

    @Override
    public Customer findById(long id) {
        int index = new IndexFinder(Repository.customers).findIndexById(id);
        if (index == -1)
            return null;
        return Repository.customers.get(index);
    }

    @Override
    public void delete(long id) {
        int index = new IndexFinder(Repository.customers).findIndexById(id);
        if (index != -1)
            Repository.customers.remove(index);
    }


}
