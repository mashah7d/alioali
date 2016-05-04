package ir.corestockexchanging.model.domain;

import ir.corestockexchanging.model.domain.order.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ali on 02/20/2016.
 */
public class Customer extends Entity {

    private String name_;
    private String family_;
    private long credit_;
    private List<Order> activeBuys_;
    private List<Order> activeSells_;
    private List<Order> doneBuys_;
    private List<Order> doneSells_;
    private List<Order> declinedBuys_;
    private List<Order> declinedSells_;
    private List<Share> shares_;

    public enum WithdrawResult{
        SUCCESSFUL,
        UNKNOWN_ID,
        NOT_ENOUGH_MONEY
    }

    public Customer() {
        this(0, "", "");
    }

    public Customer(long id, String name, String family) {
        this.id = id;
        name_ = name;
        family_ = family;
        credit_ = 0;
        activeBuys_ = new ArrayList<>();
        activeSells_ = new ArrayList<>();
        declinedBuys_ = new ArrayList<>();
        declinedSells_ = new ArrayList<>();
        doneBuys_ = new ArrayList<>();
        doneSells_ = new ArrayList<>();
        shares_ = new ArrayList<>();
    }

    public String getName() {
        return name_;
    }

    public void setName(String name_) {
        this.name_ = name_;
    }

    public String getFamily() {
        return family_;
    }

    public void setFamily(String family_) {
        this.family_ = family_;
    }

    public void deposit(long amount){
        credit_ = credit_ + amount;
    }

    public boolean withdraw(long amount){
        if(credit_ >= amount) {
            credit_ = credit_ - amount;
            return true;
        }
        return false;
    }

    public long getCredit() {
        return credit_;
    }

    public long getShareQuantity(String share){
        int indexOfShare = shares_.indexOf(share);
        if(indexOfShare == -1) {
            return 0;
        } else {
            return shares_.get(indexOfShare).getQuantity();
        }
    }

    public List<Order> getActiveBuys() {
        return activeBuys_;
    }

    public List<Order> getActiveSells() {
        return activeSells_;
    }

    public List<Share> getShares() {
        return shares_;
    }

    public void reduceActiveBuy(){

    }

    public void reduceActiveSell(){

    }

    public void addActiveBuy(Order order){
        activeBuys_.add(order);
    }

    public void addActiveSell(Order order){
        activeSells_.add(order);
    }

    public void addDoneBuy(Order order){
        doneBuys_.add(order);
    }

    public void addDoneSell(Order order){
        doneSells_.add(order);
    }

    public void addDeclinedBuy(Order order){
        declinedBuys_.add(order);
    }

    public void addDeclinedSell(Order order){
        declinedSells_.add(order);
    }

    public void addShare(Share share){
        int indexOfShare = shares_.indexOf(share);
        if(indexOfShare == -1) {
            shares_.add(share);
        } else {
            shares_.get(indexOfShare).incrementQuantity(share.getQuantity());
        }
        //error checking?
        //persist share?
    }

    public void reduceShare(Share share){
        int indexOfShare = shares_.indexOf(share);
        if(indexOfShare != -1) {
            shares_.get(indexOfShare).decrementQuantity(share.getQuantity());
        }
        //Good interface?!
        //error checking?
        //persist share?
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return this.id == customer.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}