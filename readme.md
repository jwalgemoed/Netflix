Spring Boot / Netflix / Demo Application
========================================

This repository contains a set of projects that show off the functionality provided by the Spring Boot / Netflix tool
suites. The main goal for this demo app is to demo the incredible ease with which we can build, deploy and support a
highly resilient setup.

The demo breaks down in three concrete parts:

1. Eureka server instance
2. Transporter Service App (Very simple Backspin Clone)
    * Transporter Service Client (Client project)
    * Transporter Service Server (Server project)
3. Transporter-Demo-App (Simple Spring Boot demo app to demo consuming services in the Transporter Service App)

Launching the demo
==================

The first thing to do is kick off the Eureka server. This is the hub of all activity as it will keep track of all
services that register and de-register themselves. Eureka also provides a dashboard with loads of interesting feedback
on registered services, uptime status and peer-to-peer replication.

Eureka also provides host information for dependent applications by exposing a REST interface to share this data.

Start Eureka Server
======================

1. Build the spring-boot-eureka-server application using maven and ensure the build is succesful. You may need to check
your settings.xml to retrieve all dependencies;
2. Run the application by running the EurekaServer.java class from your IDE;
3. Once the application has started, access the dashboard on http://localhost:8000
4. If you are able to see the dashboard AND see a registered instance of the Eureka-Server you are live!

Start an instance of the transporter service
===============================================

1. Build the transporter-service application using maven;
2. Run the application by running the main class: TransporterServiceServer.java
3. Once the application has started, verify that it is accepting requests by connecting to one of its endpoints: http://localhost:1010/identity
4. Open up the Eureka dashboard, and verify that the Transporter-Service has announced itself to Eureka. If not, wait a few
seconds for it to do so.

Launch the demo application to consume services from the transporter-service
===============================================================================

1. Build the Transporter-demo-app using maven;
2. Run the demo app by running the main class: TransporterDemoApp.java
3. Once the application has started, verify that it works by requesting one of its endpoints: http://localhost:8181/identity
4. The reply for this service call should be something like this: transporter-service:1010 - which is ofcourse the identity
for the transporter service. The demo app has retrieved host information from eureka and now automatically connects to one
of the registered hosts for that application, and then consumes and forwards the result for the service!

Launch an additional Transporter Service
===========================================

1. Open up the application.yml for the transporter-service-server project. Change the port number in the configuration.
2. Launch another instance of the transporter service by running the main class: TransporterServiceServer.java
3. Once the application is launched, check the eureka dashboard to see if there are now two registered instances for Transporter-service.
4. Access the service using the demo app (http://localhost:8181/identity) and start refreshing the page repeatedly
5. After the demo app has refreshed its server list from eureka you should start seeing the round robin load balancer do its work,
because the responses should alternate between the two server instances.

Cleanly shutdown a transporter service
======================================

1. In your IDE, trigger a clean shutdown for one of the two transporter service instances. Some IDE's support pressing
CTRL-C in the console, others may have an option for this. Intellij has an icon of an arrow with a door to achieve this.
2. Once the server is down, check the Eureka console to see if it is deregistered there as well
3. Repeatedly reload the demo app again (http://localhost:8181/identity). You may encounter errors, as the client caches
the hosts for a certain time and cycles over them. This is something clients should be able to manage. Keep refreshing and
see that the errors disappear. This happens because the client-side ribbon loadbalancer removes a server after repeat errors or the eureka client
refreshed the list of known hosts.

Relaunch a second transporter server
====================================

1. Follow the steps in launching an additional Transporter Service and make sure the port numbers do not collide;
2. Check that it registers itself and becomes available in Eureka;
3. Repeatedly reload the demo app again (http://localhost:8181/identity) and make sure the new host is known to the client
because you should see responses from it.

Shutdown the Eureka Server
==========================

1. Shutdown the Eureka server process.
2. Repeatedly reload the demo app again (http://localhost:8181/identity) and witness how it still functions because it actively
caches the hosts.

Shutdown a host
===============

1. Shutdown one of the hosts, with eureka still down.
2. Repeatedly reload the demo app again (http://localhost:8181/identity) and witness how the requests take a little longer
but ultimately are picked up by the only instance left. After three concurrent failures for a service, the host is removed
from the client side cache. Everything still works, even though Eureka is down!

Restart Eureka
==============

1. Follow the previously mentioned steps to restart Eureka.
2. Open the dashboard http://localhost:8000 and verify that all still active services are automatically registering again.
3. Launch another instance of the transporter service
4. Repeatedly reload the demo app again (http://localhost:8181/identity) and verify that all transporter services are
servicing the demo app. The demo app should start retrieving the host information from Eureka again automatically once it is up.

Other stuff to look at
======================

There's more stuff in the demo!

* Try using the other endpoint for the client, http://localhost:8181/transporters. This shows off protobuf integration in server and client.
* Look at the hystrix dashboard. Hystrix functions as a circuit breaker when services are experiencing trouble. The dashboard shows
metrics for these (annotated) services: http://localhost:8181/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8181%2Fhystrix.stream
* Look at the code, especially the transporter service. See that the service interface describes endpoints, which are then implemented in the server. The consumer
uses Feign to scan for classes with the @FeignClient annotation, and automatically creates a loadbalanced, ribbon enabled client for this service.
This means hardly any code is involved in creating a client!
* Also look at the code to see how little code is required to create a simple application that is able to server as a REST service,
with many advanced features. You just need to focus on the implementation itself!
* Look at the application.yml files to get an overview of the config involved in making this all work. 