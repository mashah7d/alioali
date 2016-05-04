package ir.corestockexchanging.model.domain.dealer;



import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.StringWriter;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ali on 02/24/2016.
 */
public class MPO extends Dealer {
    public MPO(){
        this.name = "MPO";
    }

    @Override
    public void buy(BuyOrder buyOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                    Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol) {
        long buyOrderQuantity = buyOrder.getQuantity();
        if (buyOrderQuantity <= sellsTotal.get()) {
            while (!buyOrder.isEmpty()) {
                SellOrder sellsLeader = sellsQueue.peek();
                long nowOrderQuantity = buyOrder.getQuantity();
                long sellsLeaderQuantity = sellsLeader.getQuantity();
                long transactionQuantity = Math.min(nowOrderQuantity, sellsLeaderQuantity);
                buyOrder.reduceQuantity(sellsLeaderQuantity);
                sellsLeader.reduceQuantity(nowOrderQuantity);
                transact(buyOrder.getCustomerId(), sellsLeader.getCustomerId(), transactionQuantity,
                        sellsLeader.getPrice(), symbol, stringWriter);
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
        if (sellOrderQuantity <= buysTotal.get()) {
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
                if (!sellOrder.isEmpty()) {
                    stringWriter.append("\n");
                }
            }
            buysTotal.addAndGet(-1 * sellOrderQuantity);
        } else {
            declineOrder(stringWriter);
        }
    }
}
