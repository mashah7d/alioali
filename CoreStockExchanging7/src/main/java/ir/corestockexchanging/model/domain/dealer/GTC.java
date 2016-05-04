package ir.corestockexchanging.model.domain.dealer;

import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.StringWriter;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ali on 02/24/2016.
 */
public class GTC extends Dealer {

    public GTC(){
        this.name = "GTC";
    }

    private void gtcRec(BuyOrder buyOrder, SellOrder sellOrder, Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue,
                        AtomicLong buysTotal, AtomicLong sellsTotal, String symbol, StringWriter stringWriter) {
        long minQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
        transact(buyOrder.getCustomerId(), sellOrder.getCustomerId(), minQuantity, buyOrder.getPrice(), symbol, stringWriter);
        removeOrReduceBuyOrder(buyOrder, symbol, minQuantity);
        removeOrReduceSellOrder(sellOrder, symbol, minQuantity);

        buysTotal.addAndGet(-1 * minQuantity);
        sellsTotal.addAndGet(-1 * minQuantity);
        if (buyOrder.getQuantity() < sellOrder.getQuantity()) {
            buysQueue.poll();
            if (isNegotiable(buysQueue.peek(), sellOrder)) {
                stringWriter.append("\n");
                gtcRec(buysQueue.peek(), sellOrder, buysQueue, sellsQueue, buysTotal, sellsTotal, symbol, stringWriter);
            }
        } else if (buyOrder.getQuantity() > sellOrder.getQuantity()) {
            sellsQueue.poll();
            if (isNegotiable(buyOrder, sellsQueue.peek())) {
                stringWriter.append("\n");
                gtcRec(buyOrder, sellsQueue.peek(), buysQueue, sellsQueue, buysTotal, sellsTotal, symbol, stringWriter);
            }
        } else {
            buysQueue.poll();
            sellsQueue.poll();
            if (isNegotiable(buysQueue.peek(), sellsQueue.peek())) {
                stringWriter.append("\n");
                gtcRec(buysQueue.peek(), sellsQueue.peek(), buysQueue, sellsQueue, buysTotal, sellsTotal, symbol, stringWriter);
            }
        }
    }


    private void addBuyOrder(BuyOrder buyOrder, Queue<BuyOrder> buysQueue, String symbol) {
        buysQueue.add(buyOrder);
//        addBuyOrder(String.valueOf(buyOrder.getCustomerId()), symbol,
//                String.valueOf(buyOrder.getPrice()), String.valueOf(buyOrder.getQuantity()));
    }

    private void addSellOrder(SellOrder sellOrder, Queue<SellOrder> sellsQueue, String symbol) {
        sellsQueue.add(sellOrder);
//        addSellOrder(String.valueOf(sellOrder.getCustomerId()), symbol,
//                String.valueOf(sellOrder.getPrice()), String.valueOf(sellOrder.getQuantity()));
    }

    @Override
    public void buy(BuyOrder buyOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                    Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol) {
        addBuyOrder(buyOrder, buysQueue, symbol);
        if (isNegotiable(buyOrder, sellsQueue.peek())) { //Error checking, null checking
            gtcRec(buysQueue.peek(), sellsQueue.peek(), buysQueue, sellsQueue, buysTotal, sellsTotal, symbol, stringWriter);
        } else {
            stringWriter.write("Order is queued");
        }
    }

    @Override
    public void sell(SellOrder sellOrder, StringWriter stringWriter, AtomicLong buysTotal, AtomicLong sellsTotal,
                     Queue<BuyOrder> buysQueue, Queue<SellOrder> sellsQueue, String symbol) {
        addSellOrder(sellOrder, sellsQueue, symbol);
        if (isNegotiable(buysQueue.peek(), sellOrder)) {
            gtcRec(buysQueue.peek(), sellsQueue.peek(), buysQueue, sellsQueue, buysTotal, sellsTotal, symbol,
                    stringWriter);
        } else {
            stringWriter.write("Order is queued");
        }
    }
}
