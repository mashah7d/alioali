package ir.corestockexchanging.model.dao.impl;

import ir.corestockexchanging.model.dao.DepositRequestDao;
import ir.corestockexchanging.model.dao.impl.helper.IndexFinder;
import ir.corestockexchanging.model.domain.DepositRequest;

import java.util.List;

/**
 * Created by Ali on 03/27/2016.
 */
public class DepositRequestDaoImpl implements DepositRequestDao {
    private static long nextId = 0;

    public DepositRequestDaoImpl(){

    }

    @Override
    public List<DepositRequest> getDepositRequestsList() {
        return Repository.depositRequests;
    }

    @Override
    public void addDepositRequest(DepositRequest depositRequest) {
        depositRequest.setId(nextId);
        nextId += 1;
        Repository.depositRequests.add(depositRequest);
    }

    @Override
    public DepositRequest findById(long id) {
        int index = new IndexFinder(Repository.depositRequests).findIndexById(id);
        if (index == -1)
            return null;
        return Repository.depositRequests.get(index);
    }

    @Override
    public void delete(long id) {
        int index = new IndexFinder(Repository.depositRequests).findIndexById(id);
        if (index != -1)
            Repository.depositRequests.remove(index);
    }


}
