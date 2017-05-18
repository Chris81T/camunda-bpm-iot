package de.chrthms.mco;

import org.camunda.bpm.engine.ProcessEngine;

/**
 * Created by christian on 18.05.17.
 */
public interface MicroProcessEngine extends ProcessEngine {

    MicroOpenhabService getMicroOpenhabService();

}
