package de.chrthms.mco;

import de.chrthms.mco.impl.MicroProcessEngineFactoryImpl;

/**
 * Created by christian on 18.05.17.
 */
public interface MicroProcessEngineFactory {

    static MicroProcessEngineFactory getInstance() {
        return new MicroProcessEngineFactoryImpl();
    }

    MicroProcessEngineFactory jdbcDriver(String driver);

    MicroProcessEngineFactory jdbcUrl(String url);

    MicroProcessEngineFactory jdbcUsername(String username);

    MicroProcessEngineFactory jdbcPassword(String password);

    MicroProcessEngine build();

}
