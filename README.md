# Template_Distributed_System_on_Apache
A template scalable load-balancing centralized distributed system provides dummy web services with Apache Axis2 on Apache Tomcat
 


**Central Servers**
A central server controls services registration, handles service requests, and evenly assigns service requests to each servers.

At least one central server must be started when starting the entire system.


**Servers**
A server provides several services. When a server starts/shuts down, each service will be registrated/unregistered on the central servers.


**Clients**
A client requests services from an avaiable central server.