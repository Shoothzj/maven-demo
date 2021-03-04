package com.github.shoothzj.demo.prometheus;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

/**
 * @author hezhangjian
 */
@Slf4j
public class PrometheusMetricsProvider {

    private final CollectorRegistry registry;

    public PrometheusMetricsProvider() {
        this.registry = CollectorRegistry.defaultRegistry;
        new StandardExports().register(registry);
        new MemoryPoolsExports().register(registry);
    }

    public void writeAllMetrics(Writer writer) throws IOException {
        writeMetricsByRegistry(writer);
    }

    public void writeMetricsByRegistry(Writer w) throws IOException {
        Enumeration<Collector.MetricFamilySamples> metricFamilySamples = registry.metricFamilySamples();
        while (metricFamilySamples.hasMoreElements()) {
            Collector.MetricFamilySamples metricFamily = metricFamilySamples.nextElement();

            for (int i = 0; i < metricFamily.samples.size(); i++) {
                Collector.MetricFamilySamples.Sample sample = metricFamily.samples.get(i);
                w.write(sample.name);
                w.write('{');
                for (int j = 0; j < sample.labelNames.size(); j++) {
                    if (j != 0) {
                        w.write(", ");
                    }
                    w.write(sample.labelNames.get(j));
                    w.write("=\"");
                    w.write(sample.labelValues.get(j));
                    w.write('"');
                }

                w.write("} ");
                w.write(Collector.doubleToGoString(sample.value));
                w.write('\n');
            }
        }
    }

}
