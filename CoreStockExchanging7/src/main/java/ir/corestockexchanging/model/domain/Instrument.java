package ir.corestockexchanging.model.domain;

import ir.corestockexchanging.model.domain.dealer.Dealer;
import ir.corestockexchanging.model.domain.dealer.DefaultBuyComparator;
import ir.corestockexchanging.model.domain.dealer.DefaultSellComparator;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.StringWriter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ali on 03/28/2016.
 */
public class Instrument extends Entity {
    private String symbol;
    private String name;
    private Queue<SellOrder> sellOrdersQueue;
    private Queue<BuyOrder> buyOrdersQueue;
    private AtomicLong buysTotalQuantity;
    private AtomicLong sellsTotalQuantity;

    public Instrument() {
        sellOrdersQueue = new PriorityQueue<>(new DefaultSellComparator());
        buyOrdersQueue = new PriorityQueue<>(new DefaultBuyComparator());
        buysTotalQuantity = new AtomicLong(0);
        sellsTotalQuantity = new AtomicLong(0);
    }

    public Instrument(String symbol) {
        this();
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<SellOrder> getSellOrdersQueue() {
    return sellOrdersQueue;
    }

    public void setSellOrdersQueue(Queue<SellOrder> sellOrders) {
        this.sellOrdersQueue = sellOrders;
    }

    public Queue<BuyOrder> getBuyOrdersQueue() {
        return buyOrdersQueue;
    }

    public void setBuyOrdersQueue(Queue<BuyOrder> buyOrders) {
        this.buyOrdersQueue = buyOrders;
    }

    public String buy(BuyOrder buyOrder) {
        StringWriter stringWriter = new StringWriter();
        Dealer dealer = TransactionTypes.get(buyOrder.getType());
        if (dealer != null) {
            dealer.buy(buyOrder, stringWriter, buysTotalQuantity, sellsTotalQuantity, buyOrdersQueue,
                    sellOrdersQueue, symbol);
        }else {
            stringWriter.write("invalid type");
        }
        return stringWriter.toString();
    }

    public String sell(SellOrder sellOrder) {
        StringWriter stringWriter = new StringWriter();
        Dealer dealer = TransactionTypes.get(sellOrder.getType());
        if (dealer != null) {
            dealer.sell(sellOrder, stringWriter, buysTotalQuantity, sellsTotalQuantity, buyOrdersQueue,
                    sellOrdersQueue, symbol);
        }else {
            stringWriter.write("invalid type");
        }
        return stringWriter.toString();
    }

    public void addSellOrder(SellOrder sellOrder){
        sellOrdersQueue.add(sellOrder);
    }

    public void removeBuyOrder(BuyOrder buyOrder, long quantity) {
        for (BuyOrder bo : buyOrdersQueue){
            if (bo.getId() == buyOrder.getId()){
                if(quantity == bo.getQuantity()){
                    buyOrdersQueue.remove(bo);
                }else {
                    bo.reduceQuantity(quantity);//persist
                }
            }
        }
    }

    public void removeSellOrder(SellOrder sellOrder, long quantity) {
        for (SellOrder so : sellOrdersQueue){
            if (so.getId() == sellOrder.getId()){
                if(quantity == so.getQuantity()){
                    sellOrdersQueue.remove(so);
                }else {
                    so.reduceQuantity(quantity);//persist
                }
            }
        }
    }
}
