package ir.corestockexchanging.model.dao;

import ir.corestockexchanging.model.domain.Instrument;

import java.util.List;

/**
 * Created by Ali on 03/27/2016.
 */
public interface InstrumentDao {
    List<Instrument> getInstrumentsList();

    void addInstrument(Instrument instrument);

    Instrument findById(long id);

    Instrument findBySymbol(String symbol);

    void delete(long id);

}
