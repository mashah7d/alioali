package ir.corestockexchanging.model.dao.impl;

import ir.corestockexchanging.model.dao.ExchangeDao;
import ir.corestockexchanging.model.domain.Exchange;

import java.util.List;

/**
 * Created by Ali on 04/07/2016.
 */
public class ExchangeDaoImpl implements ExchangeDao {
    @Override
    public List<Exchange> getAll() {
        return Repository.exchanges;
    }

    @Override
    public void add(Exchange exchange) {
        Repository.exchanges.add(exchange);
    }
}
