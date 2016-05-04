package ir.corestockexchanging.model.dao;

import ir.corestockexchanging.model.domain.DepositRequest;

import java.util.List;

/**
 * Created by Ali on 03/27/2016.
 */
public interface DepositRequestDao {
     List<DepositRequest> getDepositRequestsList();

     void addDepositRequest(DepositRequest depositRequest);

     DepositRequest findById(long id);

     void delete(long id);


}
