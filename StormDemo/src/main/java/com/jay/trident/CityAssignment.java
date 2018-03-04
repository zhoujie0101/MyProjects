package com.jay.trident;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jay on 16/7/20.
 */
public class CityAssignment extends BaseFunction {
    private static final Logger LOG = LoggerFactory.getLogger(CityAssignment.class);
    private static final long serialVersionUID = -6317342228959428023L;

    private static Map<String, double[]> CITIES = new HashMap<>();

    { // Initialize the cities we care about.
        double[] phl = {39.875365, -75.249524};
        CITIES.put("PHL", phl);
        double[] nyc = {40.71448, -74.00598};
        CITIES.put("NYC", nyc);
        double[] sf = {-31.4250142, -62.0841809};
        CITIES.put("SF", sf);
        double[] la = {-34.05374, -118.24307};
        CITIES.put("LA", la);
    }

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        DiagnosisEvent diagnosis = (DiagnosisEvent) tuple.getValue(0);
        double leastDistance = Double.MAX_VALUE;
        String closestCity = "NONE";
        for (Map.Entry<String, double[]> city : CITIES.entrySet()) {
            double R = 6371; // km
            double x = (city.getValue()[0] - diagnosis.lng) * Math.cos((city.getValue()[0] + diagnosis.lng) / 2);
            double y = (city.getValue()[1] - diagnosis.lat);
            double d = Math.sqrt(x * x + y * y) * R;
            if (d < leastDistance) {
                leastDistance = d;
                closestCity = city.getKey();
            }
        }
        List<Object> values = new ArrayList<>();
        values.add(closestCity);
        LOG.debug("Closest city to lat=[" + diagnosis.lat + "], lng=[" + diagnosis.lng + "] == ["
                + closestCity + "], d=[" + leastDistance + "]");
        collector.emit(values);
    }
}
