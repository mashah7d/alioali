package ir.corestockexchanging.model.dao.impl;

import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.DepositRequest;
import ir.corestockexchanging.model.domain.Exchange;
import ir.corestockexchanging.model.domain.Instrument;
import ir.corestockexchanging.model.domain.dealer.DefaultBuyComparator;
import ir.corestockexchanging.model.domain.dealer.DefaultSellComparator;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Ali on 04/04/2016.
 */
public class Repository {
    static List<Instrument> instruments;
    static List<Customer> customers;
    static List<DepositRequest> depositRequests;
    static List<Exchange> exchanges;


    static Repository repository = new Repository();

    private Repository() {
        instruments = new ArrayList<>();
        customers = new ArrayList<>();
        depositRequests = new ArrayList<>();
        exchanges = new ArrayList<>();
    }

}
