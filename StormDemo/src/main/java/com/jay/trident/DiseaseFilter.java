package com.jay.trident;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jay on 16/7/20.
 */
public class DiseaseFilter extends BaseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(DiseaseFilter.class);
    private static final long serialVersionUID = 5790220462368334417L;

    @Override
    public boolean isKeep(TridentTuple tuple) {
        DiagnosisEvent event = (DiagnosisEvent) tuple.getValue(0);

        Integer code = Integer.parseInt(event.diagnosisCode);
        if (code <= 322) {
            LOG.debug("Emitting disease [" + event.diagnosisCode + "]");
            return true;
        } else {
            LOG.debug("Filtering disease [" + event.diagnosisCode + "]");
            return false;
        }
    }
}
