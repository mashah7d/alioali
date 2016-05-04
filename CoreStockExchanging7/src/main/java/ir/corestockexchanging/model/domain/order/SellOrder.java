package ir.corestockexchanging.model.domain.order;

/**
 * Created by Ali on 04/01/2016.
 */
public class SellOrder extends Order {
    static int nextId = 0; //temporary

    public SellOrder(long customerId, long instrumentId, long price, long quantity, String type) {
        super(customerId, instrumentId, price, quantity, type);
        this.id = nextId;
        nextId += 1;
    }

    public SellOrder(Order order) {
        super(order);
    }
}
