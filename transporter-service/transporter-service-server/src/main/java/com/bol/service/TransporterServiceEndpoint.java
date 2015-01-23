package com.bol.service;

import com.bol.client.TransporterService;
import com.bol.proto.TransporterMessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restful endpoint, implements the TransporterService interface. This means the requestmappings are already
 * defined, we only need to provide the correct implementations.
 *
 * This class is annotated as a RestController to allow Spring to do the mapping for this resource.
 */
@RestController
public class TransporterServiceEndpoint implements TransporterService {

    private String application;
    private String port;

    @Autowired
    public TransporterServiceEndpoint(@Value("${spring.application.name}") final String application, @Value("${server.port}") final String port) {
        this.application = application;
        this.port = port;
    }

    @Override
    public TransporterMessages.Transporters getTransporters() {
        return TransporterMessages.Transporters.newBuilder()
                .addTransporters(TransporterMessages.Transporter.newBuilder().setName("PostNL").setCode("TNT").build())
                .addTransporters(TransporterMessages.Transporter.newBuilder().setName("DHL").setCode("DHLFORYOU").build())
                .addTransporters(TransporterMessages.Transporter.newBuilder().setName("DPD").setCode("DPD-BE").build())
                .build();
    }

    @Override
    public String getServerIdentity() {
        return application + ":" + port;
    }
}
