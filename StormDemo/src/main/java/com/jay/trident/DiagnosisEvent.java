package com.jay.trident;

import java.io.Serializable;

/**
 * Created by jay on 16/7/20.
 */
public class DiagnosisEvent implements Serializable {

    private static final long serialVersionUID = -5563929503750263684L;

    public double lat;
    public double lng;
    public long time;
    public String diagnosisCode;

    public DiagnosisEvent(double lat, double lng, long time, String diagnosisCode) {
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.diagnosisCode = diagnosisCode;
    }
}
