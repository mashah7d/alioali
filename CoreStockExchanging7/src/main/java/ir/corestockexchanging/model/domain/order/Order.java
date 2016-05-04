package ir.corestockexchanging.model.domain.order;

import ir.corestockexchanging.model.domain.Entity;

/**
 * Created by Ali on 02/17/2016.
 */
public abstract class Order extends Entity{

//    public enum Type{GTC, IOC, MPO}
    protected final long customerId_;
    protected final long instrumentId_;
    protected final long price_;
    protected long quantity_;
    //    protected final Type type_;
    protected final String type_;

    protected Order(long customerId, long instrumentId, long price, long quantity, String type) {
        customerId_ = customerId;
        instrumentId_ = instrumentId;
        price_ = price;
        quantity_ = quantity;
        type_ = type;
    }

    public Order(Order order) {
        customerId_ = order.getCustomerId();
        price_ = order.getPrice();
        quantity_ = order.getQuantity();
        type_ = order.getType();
        instrumentId_ = order.getInstrumentId();
    }

    public long getQuantity() {
        return quantity_;
    }

    public long getCustomerId() {
        return customerId_;
    }

    public long getPrice() {
        return price_;
    }

//    public Type getType() {
//        return type_;
//    }

    public String getType() {
        return type_;
    }
/*
    public static boolean isNegotiable(BuyOrder buyOrder, SellOrder sellOrder) {
        return buyOrder.getPrice() >= sellOrder.getPrice();

    }
*/
    public boolean isEmpty() {
        return quantity_ == 0;
    }

    public void reduceQuantity(long howMany) {
        quantity_ = (quantity_ > howMany) ? (quantity_ - howMany) : 0;
    }

    public long getInstrumentId() {
        return instrumentId_;
    }

    /*
    public static Type stringToType(String type) {
        switch (type.toLowerCase()){
            case "gtc":
                return Type.GTC;
            case "ioc":
                return Type.IOC;
            case "mpo":
                return Type.MPO;
            default:
                return null;
        }
    }
    */
}
