package com.bol.service;

import com.bol.client.TransporterService;
import com.bol.proto.TransporterMessages;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo app endpoint. Shows off how Spring can inject a Feign client that is aware of all available instances
 * of a service by consulting Eureka. The Feign client will automatically loadbalance over the available instances
 * using a round-robin loadbalancer. Other implementations are available and can also be customized. For this demo,
 * the round-robin implementation should be fine.
 */
@RestController
@RequestMapping
public class DemoAppEndpoint {

    // Inject the transporterservice. Feign will provide the implementation
    @Autowired
    private TransporterService transporterService;

    /**
     * Endpoint implementation that just echo's the identity for the remote service. Used to
     * show the round-robin loadbalancing over the different available hosts.
     *
     * @return the server identity for the remote server
     */
    @RequestMapping(value = "/identity", method = RequestMethod.GET)
    public String hello() {
        return transporterService.getServerIdentity();
    }

    /**
     * Transporters endpoint, echo's the results from the remote server. Communication between servers
     * is executed using protobuf.
     *
     * @return a list of transporters from the remote service
     */
    @RequestMapping(value = "/transporters", method = RequestMethod.GET)
    @HystrixCommand
    public TransporterMessages.Transporters getTransporters() {
        return transporterService.getTransporters();
    }
}
