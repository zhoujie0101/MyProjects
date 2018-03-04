package com.jay.metricsdemo;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Created by jay on 2017/8/12.
 */
public class GetStarted {
    private static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) {
        startReport();

        Meter meter = metrics.meter("request");
        meter.mark();

        wait5Seconds();

        meter.mark();

        wait5Seconds();
    }

    private static void wait5Seconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();

        reporter.start(1, TimeUnit.SECONDS);
    }
}
