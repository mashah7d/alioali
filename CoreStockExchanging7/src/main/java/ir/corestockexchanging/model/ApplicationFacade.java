package ir.corestockexchanging.model;

import ir.corestockexchanging.model.dao.CustomerDao;
import ir.corestockexchanging.model.dao.DepositRequestDao;
import ir.corestockexchanging.model.dao.ExchangeDao;
import ir.corestockexchanging.model.dao.InstrumentDao;
import ir.corestockexchanging.model.dao.impl.CustomerDaoImpl;
import ir.corestockexchanging.model.dao.impl.DepositRequestDaoImpl;
import ir.corestockexchanging.model.dao.impl.ExchangeDaoImpl;
import ir.corestockexchanging.model.dao.impl.InstrumentDaoImpl;
import ir.corestockexchanging.model.domain.Customer;
import ir.corestockexchanging.model.domain.DepositRequest;
import ir.corestockexchanging.model.domain.Exchange;
import ir.corestockexchanging.model.domain.Instrument;
import ir.corestockexchanging.model.domain.order.BuyOrder;
import ir.corestockexchanging.model.domain.order.SellOrder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ali on 04/07/2016.
 */
public class ApplicationFacade {
    private static ApplicationFacade applicationFacade = new ApplicationFacade();

    private ApplicationFacade() {
    }

    private static InstrumentDao instrumentDao = new InstrumentDaoImpl();
    private static CustomerDao customerDao = new CustomerDaoImpl();
    private static DepositRequestDao depositRequestDao = new DepositRequestDaoImpl();
    private static ExchangeDao exchangeDao = new ExchangeDaoImpl();

    public static String buy(long userId, String instrumentSymbol, long price, long quantity, String dealingType) {//
        Instrument instrument = instrumentDao.findBySymbol(instrumentSymbol);
        if (instrument == null) {
            return "Invalid symbol";
        }
        Customer customer = customerDao.findById(userId);
        if(customer == null){
            return "Unknown id";
        }
        if(customer.getCredit() < price * quantity){
            return "Insufficient found";
        }
        return instrument.buy(new BuyOrder(userId, instrument.getId(), price, quantity, dealingType));
        //TODO: persistence of instrument's queues in db
    }

    public static String sell(long userId, String instrumentSymbol, long price, long quantity, String dealingType) {//
        if (userId == 1){
            return adminAddSellOrder(instrumentSymbol, price, quantity);//no matter what is the type.
        }
        Instrument instrument = instrumentDao.findBySymbol(instrumentSymbol);
        if (instrument == null) {
            return "Invalid symbol";
        }
        Customer customer = customerDao.findById(userId);
        if(customer == null){
            return "Unknown id";
        }
        if(customer.getShareQuantity(instrumentSymbol) < quantity){
            return "Insufficient share";
        }
        return instrument.sell(new SellOrder(userId, instrument.getId(), price, quantity, dealingType));
        //TODO: persistence of instrument's queues in db
    }

    private static String adminAddSellOrder(String instrumentSymbol, long price, long quantity) {
        Instrument instrument = instrumentDao.findBySymbol(instrumentSymbol);
        if (instrument == null) {
            instrument = new Instrument(instrumentSymbol);
        }
        instrument.addSellOrder(new SellOrder(1, instrument.getId(), price, quantity, "GTC"));//Hard coded 1 as admin's userId
        return "Order queued";
    }

    public static void declineDepositRequest(long requestId) {
        depositRequestDao.delete(requestId);
    }

    public static void confirmDepositRequest(long requestId) {
        DepositRequest depositRequest = depositRequestDao.findById(requestId);
        Customer customer = customerDao.findById(depositRequest.getUserId());
        customer.deposit(depositRequest.getAmount());
        //TODO: persist customer to db
        depositRequestDao.delete(requestId);
    }

    public static List<DepositRequest> getDepositRequestsList() {
        return  depositRequestDao.getDepositRequestsList();
    }

    public static void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }
    public static Customer getCustomer(long id) {
        return customerDao.findById(id);
    }

    public static void addDepositRequest(DepositRequest depositRequest) {
        depositRequestDao.addDepositRequest(depositRequest);
    }

    public static Instrument getInstrument(long instrumentId) {
        return instrumentDao.findById(instrumentId);
    }

    public static List<Instrument> getInstrumentsList() {
        return instrumentDao.getInstrumentsList();
    }

    public static String exportCsv() {
        final String FILE_NAME = "backup.csv";
        List<Exchange> exchanges = exchangeDao.getAll();

        try
        {
            File file = new File(FILE_NAME);
            System.out.println(file.getAbsolutePath());//debug
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.append("Buyer");
            writer.append(" ," );
            writer.append("Seller");
            writer.append(" ," );
            writer.append("instrument");
            writer.append(" ," );
            writer.append("type of trade");
            writer.append(" ," );
            writer.append("quantity");
            writer.append(" ," );
            writer.append("Buyer Remained Money");
            writer.append(" ," );
            writer.append("Seller Current Money");
            writer.append('\n');

            for (Exchange exchange : exchanges) {
                writer.append(String.valueOf(exchange.getBuyerId()));
                writer.append(" ,");
                writer.append(String.valueOf(exchange.getSellerId()));
                writer.append(" ,");
                writer.append(instrumentDao.findById(exchange.getInstrumentId()).getSymbol());
                writer.append(" ,");
                writer.append(exchange.getType());
                writer.append(" ,");
                writer.append(String.valueOf(exchange.getQuantity()));
                writer.append(" ,");
                writer.append(String.valueOf(exchange.getBuyerRemindMoney()));
                writer.append(" ,");
                writer.append(String.valueOf(exchange.getSellerCurrentMoney()));
                writer.append('\n');
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("hi");//debug

            e.printStackTrace();
        }
        return FILE_NAME;
    }
}
