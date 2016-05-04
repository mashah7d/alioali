package ir.corestockexchanging.model.domain;

/**
 * Created by Ali on 04/07/2016.
 */
public class Exchange extends Entity {
    private long buyerId;
    private long sellerId;
    private long instrumentId;
    private String type;

    public long getBuyerId() {
        return buyerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public String getType() {
        return type;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getBuyerRemindMoney() {
        return buyerRemindMoney;
    }

    public long getSellerCurrentMoney() {
        return sellerCurrentMoney;
    }

    private long quantity;
    private long buyerRemindMoney;
    private long sellerCurrentMoney;

    public Exchange(long buyerId, long sellerId, long instrumentId, String type, long quantity, long buyerRemindMoney,
                    long sellerCurrentMoney) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.instrumentId = instrumentId;
        this.type = type;
        this.quantity = quantity;
        this.buyerRemindMoney = buyerRemindMoney;
        this.sellerCurrentMoney = sellerCurrentMoney;
    }
}
