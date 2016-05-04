package ir.corestockexchanging.model.domain.dealer;

import ir.corestockexchanging.model.domain.order.BuyOrder;

import java.util.Comparator;

/**
 * Created by Ali on 04/04/2016.
 */
public class DefaultBuyComparator implements Comparator<BuyOrder> {
    @Override
    public int compare(BuyOrder buyOrder1, BuyOrder buyOrder2) {
        return Long.compare(buyOrder2.getPrice(), buyOrder1.getPrice());
    }
}
