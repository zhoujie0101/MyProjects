package com.jay.test.operator;

import com.jay.test.model.DiagnosisEvent;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiseaseFilter extends BaseFilter {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DiseaseFilter.class);

    @Override
    public boolean isKeep(TridentTuple tuple) {
        DiagnosisEvent diagnosis = (DiagnosisEvent) tuple.getValue(0);
        Integer code = Integer.parseInt(diagnosis.diagnosisCode);
        if (code.intValue() <= 322) {
            LOG.debug("Emitting disease [" + diagnosis.diagnosisCode + "]");
            return true;
        } else {
            LOG.debug("Filtering disease [" + diagnosis.diagnosisCode + "]");
            return false;
        }
    }
}