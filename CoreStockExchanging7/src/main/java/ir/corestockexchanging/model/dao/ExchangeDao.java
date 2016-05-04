package ir.corestockexchanging.model.dao;

import ir.corestockexchanging.model.domain.Exchange;

import java.util.List;

/**
 * Created by Ali on 04/07/2016.
 */
public interface ExchangeDao {
    List<Exchange> getAll();

    void add(Exchange exchange);

//    Exchange findById(long id);
}
