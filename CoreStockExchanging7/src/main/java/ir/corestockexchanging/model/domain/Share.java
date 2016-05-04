package ir.corestockexchanging.model.domain;

/**
 * Created by Ali on 03/28/2016.
 */
public class Share extends Entity {
    private long quantity;
    private long instrumentId;
    private long customerId;

    public Share(long instrumentId, long customerId){
        this.instrumentId = instrumentId;
        this.customerId = customerId;
        quantity = 0;
    }

    public Share(long instrumentId, long customerId, long quantity){
        this.instrumentId = instrumentId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void incrementQuantity(long amount){
        quantity += amount;
    }

    public void decrementQuantity(long amount){
        quantity -= amount;
        //Error checking
    }

    //Not really every two shares with same instrumentId are equal.
    //But for the sake of comparing shares that a customer has, it is true.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Share share = (Share) o;

        return (instrumentId == share.instrumentId) && (customerId == share.customerId);

    }

    @Override
    public int hashCode() {
        return (int) (instrumentId ^ (instrumentId >>> 32));
    }
}
