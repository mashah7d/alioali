package ir.corestockexchanging.model.domain.dealer;


import ir.corestockexchanging.model.dao.CustomerDao;
import ir.corestockexchanging.model.dao.ExchangeDao;
import ir.corestockexchanging.model.dao.InstrumentDao;
import ir.corestockexchanging.model.dao.impl.CustomerDaoImpl;
import ir.corestockexchanging.model.dao.impl.ExchangeDaoImpl;
import ir.corestockexchanging.model.dao.impl.InstrumentDaoImpl;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.Exchange;
import ir.corestockexchanging.model.domain.Instrument;
import ir.corestockexchanging.model.domain.Share;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.StringWriter;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ali on 02/24/2016.
 */
public abstract class Dealer {
    protected String name;

    public abstract void buy(BuyOrder buyOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                             Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol);

    public abstract void sell(SellOrder sellOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                              Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol);

    protected void transact(long buyerId, long sellerId, long quantity, long price, String symbol, StringWriter stringWriter) {
        stringWriter.append(sellerId + "").append(" sold ").append(quantity + "").append(" shares of ").
                append(symbol).append(" @").append(price + "").append(" to ").append(buyerId + "");
        //instead of upper line add a csv generation mechanism

        CustomerDao customerDao = new CustomerDaoImpl();
        InstrumentDao instrumentDao = new InstrumentDaoImpl();
        Customer buyer = customerDao.findById(buyerId);
        Customer seller = customerDao.findById(sellerId);
        Instrument instrument = instrumentDao.findBySymbol(symbol);
        buyer.addShare(new Share(instrument.getId(), buyerId));
        //Persist customer
        seller.deposit(price);
        seller.reduceShare(new Share(instrument.getId(), sellerId));

        ExchangeDao exchangeDao = new ExchangeDaoImpl();
        exchangeDao.add(new Exchange(buyerId, sellerId, instrument.getId(), name, quantity, buyer.getCredit(),
                seller.getCredit()));
//        OrderData.addInstrument(String.valueOf(buyerId), symbol, String.valueOf(quantity));
//        OrderData.removeInstrument(String.valueOf(sellerId), symbol, String.valueOf(quantity));
//        CustomerData.deposit(String.valueOf(sellerId), String.valueOf(price * quantity));
//        CustomerData.withdraw(String.valueOf(sellerId), String.valueOf(price * quantity));
//        OrderData.addDoneBuy(String.valueOf(buyerId), symbol, String.valueOf(price), String.valueOf(quantity));
//        OrderData.addDoneSell(String.valueOf(sellerId), symbol, String.valueOf(price), String.valueOf(quantity));
    }

    protected void removeOrReduceBuyOrder(BuyOrder buyOrder, String symbol, long quantity) {
        InstrumentDao instrumentDao = new InstrumentDaoImpl();

        Instrument instrument = instrumentDao.findBySymbol(symbol);//persist
        instrument.removeBuyOrder(buyOrder, quantity);
    }

    protected void removeOrReduceSellOrder(SellOrder sellOrder, String symbol, long quantity){
        InstrumentDao instrumentDao = new InstrumentDaoImpl();

        Instrument instrument = instrumentDao.findBySymbol(symbol);//persist
        instrument.removeSellOrder(sellOrder, quantity);
    }

    //it's the default logic
    protected static boolean isNegotiable(BuyOrder buyOrder, SellOrder sellOrder) {
        return buyOrder.getPrice() >= sellOrder.getPrice();
    }

    protected void declineOrder(StringWriter stringWriter) {
        stringWriter.write("Order is declined");
//        OrderData.addDeclinedOrder();
    }
}
