package ir.corestockexchanging.model.domain.dealer;

import ir.corestockexchanging.model.domain.order.SellOrder;

import java.util.Comparator;

/**
 * Created by Ali on 04/04/2016.
 */
public class DefaultSellComparator implements Comparator<SellOrder> {
    @Override
    public int compare(SellOrder sellOrder1, SellOrder sellOrder2) {
        return Long.compare(sellOrder1.getPrice(), sellOrder2.getPrice());
    }
}
