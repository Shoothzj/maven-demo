package com.github.shoothzj.demo.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class PrometheusService {

    @Autowired
    private MeterRegistry meterRegistry;

    Counter testCounter;

    @PostConstruct
    public void init() {
        testCounter = Counter.builder("test_counter").tag("key", "value").register(meterRegistry);
        Gauge.builder("test_builder", new Supplier<Number>() {
            @Override
            public Number get() {
                return new Random().nextDouble();
            }
        }).register(meterRegistry);
    }

    @Scheduled(fixedDelay = 10)
    public void scheduleCounter() {
        testCounter.increment(new Random().nextDouble());
    }

}
