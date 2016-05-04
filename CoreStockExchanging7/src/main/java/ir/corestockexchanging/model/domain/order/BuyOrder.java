package ir.corestockexchanging.model.domain.order;

/**
 * Created by Ali on 04/01/2016.
 */
public class BuyOrder extends Order {
    static int nextId = 0;//This is temporary

    public BuyOrder(long customerId, long instrumentId, long price, long quantity, String type) {
        super(customerId, instrumentId, price, quantity, type);
        this.id = nextId;
        nextId += 1;
    }

    public BuyOrder(Order order) {
        super(order);
    }
}
