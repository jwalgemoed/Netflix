package com.bol.client;

import com.bol.proto.TransporterMessages;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Client interface specification for the TransporterService. All endpoint mappings are registered here,
 * which means they automatically apply for the implementing endpoint.
 *
 * The FeignClient annotation allows consumers for these services to retrieve an automatically generated client
 * that uses these same mappings to connect to the server endpoints. The Ribbon loadbalancer and Eureka integration
 * will allow the Feign client to retrieve servers dynamically - without any additional configuration.
 */
@FeignClient(value = "transporterService", loadbalance = true)
public interface TransporterService {

    /**
     * Retrieve a list of supported transporters
     *
     * @return all supported transporters
     */
    @RequestMapping(value = "/transporters", method = RequestMethod.GET)
    TransporterMessages.Transporters getTransporters();

    /**
     * Getter to retrieve the server identity (name/host/port)
     *
     * @return the server identity
     */
    @RequestMapping(value = "/identity", method = RequestMethod.GET)
    String getServerIdentity();
}
