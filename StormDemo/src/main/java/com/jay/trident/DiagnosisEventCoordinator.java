package com.jay.trident;

import org.apache.storm.trident.spout.ITridentSpout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by jay on 16/7/20.
 */
public class DiagnosisEventCoordinator implements ITridentSpout.BatchCoordinator<Long>, Serializable {

    private static final long serialVersionUID = -8416815267968868123L;

    private static final Logger logger = LoggerFactory.getLogger(DiagnosisEventCoordinator.class);

    @Override
    public Long initializeTransaction(long txid, Long prevMetadata, Long currMetadata) {
        logger.info("initialize transaction [" + txid + "]");
        return null;
    }

    @Override
    public void success(long txid) {
        logger.info("Successful transaction [" + txid + "]");
    }

    @Override
    public boolean isReady(long txid) {
        return true;
    }

    @Override
    public void close() {

    }
}
