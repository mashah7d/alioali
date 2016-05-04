package ir.corestockexchanging.model.domain.dealer;


import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.StringWriter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ali on 02/24/2016.
 */
public class IOC extends Dealer {
    public IOC(){
        this.name = "IOC";
    }

    private boolean isIocBuyPossible(BuyOrder buyOrder, Queue<SellOrder> sellsQueue) {
        Queue<SellOrder> copyOfSellQueue = new PriorityQueue<>(sellsQueue);
        BuyOrder copyOfBuyOrder = new BuyOrder(buyOrder);
        while (!copyOfSellQueue.isEmpty()) {
            SellOrder sellOrder = copyOfSellQueue.poll();
            if (isNegotiable(copyOfBuyOrder, sellOrder)) {
                copyOfBuyOrder.reduceQuantity(sellOrder.getQuantity());
            } else {
                return false;
            }
            if (copyOfBuyOrder.isEmpty())
                return true;
        }
        return false;
    }

    private boolean isIocSellPossible(SellOrder sellOrder, Queue<BuyOrder> buysQueue) {
        Queue<BuyOrder> copyOfBuyQueue = new PriorityQueue<>(buysQueue);
        SellOrder copyOfSellOrder = new SellOrder(sellOrder);
        while (!copyOfBuyQueue.isEmpty()) {
            BuyOrder buyOrder = copyOfBuyQueue.poll();
            if (isNegotiable(buyOrder, copyOfSellOrder)) {
                copyOfSellOrder.reduceQuantity(buyOrder.getQuantity());
            } else {
                return false;
            }
            if (copyOfSellOrder.isEmpty())
                return true;
        }
        return false;
    }

    @Override
    public void buy(BuyOrder buyOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                    Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol) {
        long buyOrderQuantity = buyOrder.getQuantity();
        if (isIocBuyPossible(buyOrder, sellsQueue)) {
            while (!buyOrder.isEmpty()) {
                SellOrder sellsLeader = sellsQueue.peek();
                long nowOrderQuantity = buyOrder.getQuantity();
                long sellsLeaderQuantity = sellsLeader.getQuantity();
                long transactionQuantity = Math.min(nowOrderQuantity, sellsLeaderQuantity);
                buyOrder.reduceQuantity(sellsLeaderQuantity);
                sellsLeader.reduceQuantity(nowOrderQuantity);
                transact(buyOrder.getCustomerId(), sellsLeader.getCustomerId(), transactionQuantity,
                        buyOrder.getPrice(), symbol, stringWriter);
                if (sellsLeader.isEmpty())
                    sellsQueue.poll();
                if (!buyOrder.isEmpty())
                    stringWriter.append("\n");
            }
            sellsTotal.addAndGet(-1 * buyOrderQuantity);
        } else {
            declineOrder(stringWriter);
        }

    }

    @Override
    public void sell(SellOrder sellOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                     Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol) {
        long sellOrderQuantity = sellOrder.getQuantity();
        if (isIocSellPossible(sellOrder, buysQueue)) {
            while (!sellOrder.isEmpty()) {
                BuyOrder buyLeader = buysQueue.peek();
                long nowOrderQuantity = sellOrder.getQuantity();
                long buysLeaderQuantity = buyLeader.getQuantity();
                long transactionQuantity = Math.min(nowOrderQuantity, buysLeaderQuantity);
                sellOrder.reduceQuantity(buysLeaderQuantity);
                buyLeader.reduceQuantity(nowOrderQuantity);
                transact(buyLeader.getCustomerId(), sellOrder.getCustomerId(), transactionQuantity,
                        buyLeader.getPrice(), symbol, stringWriter);
                if (buyLeader.isEmpty()) {
                    buysQueue.poll();
                }
                if (sellOrder.isEmpty()) {
                    break;
                } else {
                    stringWriter.append("\n");
                }
            }
            buysTotal.addAndGet(-1 * sellOrderQuantity);
        } else {
            declineOrder(stringWriter);
        }
    }
}
