package com.jay.metricsdemo;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by jay on 2017/8/12.
 */
public class QueueManager {
    private static final MetricRegistry metrics = new MetricRegistry();
    private Counter counter = metrics.counter(MetricRegistry.name(QueueManager.class, "pengding-jobs"));

    private Queue<Job> queue;

    public QueueManager(MetricRegistry metrics, String name) {
        this.queue = new ConcurrentLinkedQueue<>();
        metrics.register(MetricRegistry.name(QueueManager.class, name, "size"),
                (Gauge<Integer>) () -> QueueManager.this.queue.size());
    }

    public static void main(String[] args) {
        startReport();

        QueueManager manager = new QueueManager(metrics, "jobs");
        Job job = new Job();
        manager.addJob(job);

        wait5Seconds();

        manager.removeJob(job);

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

    private void addJob(Job job) {
        counter.inc();
        this.queue.add(job);
    }

    private boolean removeJob(Job job) {
        counter.dec();
        return this.queue.remove(job);
    }

    private static class Job {
    }
}
