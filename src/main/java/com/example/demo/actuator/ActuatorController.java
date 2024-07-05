package com.example.demo.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorController {

//    @Autowired
//    private HealthEndpoint healthEndpoint;
//
//    @Autowired
//    private InfoEndpoint infoEndpoint;

    @Autowired
    private MetricsEndpoint metricsEndpoint;

//    @GetMapping("/api/health")
//    public Object health() {
//        return healthEndpoint.health();
//    }
//
//    @GetMapping("/api/info")
//    public Object info() {
//        return infoEndpoint.info();
//    }

    @GetMapping("/api/metrics")
    public Map<String, Object> metrics() {
        Map<String, Object> metricsData = new HashMap<>();
        
        // Fetch the relevant metrics
        metricsData.put("systemCpuUsage", getMetricValue("system.cpu.usage"));
        metricsData.put("processCpuUsage", getMetricValue("process.cpu.usage"));
        metricsData.put("jvmMemoryUsed", getMetricValue("jvm.memory.used"));
        metricsData.put("jvmMemoryMax", getMetricValue("jvm.memory.max"));
        metricsData.put("jvmMemoryCommitted", getMetricValue("jvm.memory.committed"));
        metricsData.put("jvmThreadsLive", getMetricValue("jvm.threads.live"));
        metricsData.put("jvmThreadsDaemon", getMetricValue("jvm.threads.daemon"));
        metricsData.put("jvmThreadsPeak", getMetricValue("jvm.threads.peak"));

        return metricsData;
    }

    @GetMapping("/api/metrics/{name}")
    public Object metric(@PathVariable String name) {
        return metricsEndpoint.metric(name, null);
    }

    private Double getMetricValue(String metricName) {
        MetricsEndpoint.MetricDescriptor metricDescriptor = metricsEndpoint.metric(metricName, null);
        if (metricDescriptor != null && metricDescriptor.getMeasurements() != null && !metricDescriptor.getMeasurements().isEmpty()) {
            return metricDescriptor.getMeasurements().get(0).getValue();
        }
        return null;
    }
}
