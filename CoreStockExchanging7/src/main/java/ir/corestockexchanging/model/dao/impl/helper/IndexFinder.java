package ir.corestockexchanging.model.dao.impl.helper;

import ir.corestockexchanging.model.domain.Entity;

import java.util.List;

/**
 * Created by Ali on 03/29/2016.
 */
public class IndexFinder {
    private List<? extends Entity> list;
    public IndexFinder(List<? extends Entity> list){
        this.list = list;
    }



    public int findIndexById(long id){
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).getId() == id)
                return i;
        }
        return -1;
    }
}
