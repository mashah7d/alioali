package ir.corestockexchanging.model.dao.impl;

import ir.corestockexchanging.model.dao.InstrumentDao;
import ir.corestockexchanging.model.dao.impl.helper.IndexFinder;
import ir.corestockexchanging.model.domain.dealer.DefaultBuyComparator;
import ir.corestockexchanging.model.domain.dealer.DefaultSellComparator;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.Instrument;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Ali on 03/28/2016.
 */
public class InstrumentDaoImpl implements InstrumentDao {
    private static long nextId = 0;

    public InstrumentDaoImpl() {
    }

    @Override
    public List<Instrument> getInstrumentsList() {
        return Repository.instruments;
    }

    @Override
    public void addInstrument(Instrument instrument) {
        instrument.setId(nextId);
        nextId += 1;
        Repository.instruments.add(instrument);
    }

    @Override
    public Instrument findById(long id) {
        int index = new IndexFinder(Repository.instruments).findIndexById(id);
        if (index == -1)
            return null;
        return Repository.instruments.get(index);
    }

    @Override
    public Instrument findBySymbol(String symbol){
        for(int i = 0; i < Repository.instruments.size(); i++){
            Instrument iInstrument = Repository.instruments.get(i);
            if (iInstrument.getSymbol().equals(symbol))
                return iInstrument;
        }
        return null;
    }

    @Override
    public void delete(long id) {
        int index = new IndexFinder(Repository.instruments).findIndexById(id);
        if(index != 1)
            Repository.instruments.remove(index);
    }

    static {
        //<dummy data just for test>
        //instrument
        Instrument a = new Instrument();
        a.setId(1);
        a.setSymbol("irankhodro");
        Queue<BuyOrder> buyOrders = new PriorityQueue<>(new DefaultBuyComparator());
        Queue<SellOrder> sellOrders = new PriorityQueue<>(new DefaultSellComparator());
        sellOrders.add(new SellOrder(2, 1, 10, 4, "GTC"));
        sellOrders.add(new SellOrder(3, 1, 16, 4, "GTC"));
        a.setBuyOrdersQueue(buyOrders);
        a.setSellOrdersQueue(sellOrders);

        Instrument b = new Instrument();
        b.setId(2);
        b.setSymbol("saipa");
        Queue<BuyOrder> bbuyOrders = new PriorityQueue<>(new DefaultBuyComparator());
        Queue<SellOrder> bsellOrders = new PriorityQueue<>(new DefaultSellComparator());
        bsellOrders.add(new SellOrder(3, 2, 8, 2, "GTC"));
        bbuyOrders.add(new BuyOrder(2, 2, 1, 6, "GTC"));
        b.setBuyOrdersQueue(bbuyOrders);
        b.setSellOrdersQueue(bsellOrders);

        Instrument c= new Instrument();
        c.setId(3);
        c.setSymbol("irankhodro");
        Queue<BuyOrder> cbuyOrders = new PriorityQueue<>(new DefaultBuyComparator());
        Queue<SellOrder> csellOrders = new PriorityQueue<>(new DefaultSellComparator());
        csellOrders.add(new SellOrder(4, 3, 300, 1, "GTC"));
        cbuyOrders.add(new BuyOrder(2, 3, 200, 2, "GTC"));
        c.setBuyOrdersQueue(cbuyOrders);
        c.setSellOrdersQueue(csellOrders);

        nextId = 4;

        Repository.instruments.add(a);
        Repository.instruments.add(b);
        Repository.instruments.add(c);

    }
}
