package com.observability.printservice.controller;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/print")
@CrossOrigin
public class ChildController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final Tracer tracer;

    @Autowired
    public ChildController(Tracer tracer){
        this.tracer = tracer;
    }

    @PostMapping
    public String print(@RequestBody String message){
        Span parentSpan = tracer.spanBuilder("child-service-parent").setSpanKind(SpanKind.SERVER).startSpan();
        try {
            LOG.info("Getting message: " + message);
        } finally {
            parentSpan.end();
        }
        return message;
    }
}
