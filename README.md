# cf-service-broker
Demo to learn Cloud Foundry Service Broker and Service Client to consume published services

Service Broker for Hashmap as a Service

Repository contains two projects:
	
	1.	hashmap-service-broker - Service broker to publish HashMap as a Service
	2.	hashmap-service-client – Client to consume HashMap as a Service

Technologies used:
Java -1.8, Spring Boot - 1.4.2.RELEASE, Spring Cloud, Cloud Foundry Service Broker, Spring Security, JPA, HSQLDB, REST Template, Maven, Junit, Mockito

Deployment Instructions:
1.	Build hashmap-service-broker and hashmap-service-client using Maven.
2.	Push hashmap-service-broker to PCF using “cf push" command. (manifest.yml file is present in root directory with necessary parameters)
3.	Register the service broker using below command:

	cf create-service-broker SERVICE_BROKER USERNAME PASSWORD URL [--space-scoped]

	Example:
	cf create-service-broker hashmap-service-broker user password http://hashmap-service-broker-overscented-unthriftiness.cfapps.io --space-scoped
	
4.	Now you should be able to see your service broker in PCF Marketplace. Use below commands to see service details:
	
	cf marketplace [-s SERVICE]
	
	Example:
	cf marketplace
	cf marketplace -s HashMap-Service
	
5.	Create service instance using service broker. Use below command:
	
	cf create-service SERVICE PLAN SERVICE_INSTANCE
	
	Example:
	cf create-service HashMap-Service Basic-HashMap-Service custom-hashmap-service

6.	You can view the created services using below command:

	cf services

	OR

	cf service SERVICE_INSTANCE

	Example:
	cf service custom-hashmap-service

7.	Push hashmap-service-client to PCF using “cf push" command. (manifest.yml file is present in root directory with necessary parameters)
	
List o REST APIs to test the services from REST Client (Postman:

1.	To view the service catelog
	
	http://USERNAME:PASSWORD@SERVICE_BROKER_ROUTE_URL/v2/catalog
	
	Example:
	http://user:password@hashmap-service-broker-clickless-peroxyacid.cfapps.io/v2/catalog

2.	To get service binding information from service client

	GET -> http://SERVICE_CLIENT_ROUTE_URL/hashmap/info

	Example:
	GET -> http://hashmap-service-client-plummy-assimilator.cfapps.io/hashmap/info

3.	To put Key-Value pair in bound hashmap service instance

	PUT -> http://SERVICE_CLIENT_ROUTE_URL/hashmap/KEY
	Request Body - VALUE

	Example:
	
	PUT -> http://hashmap-service-client-plummy-assimilator.cfapps.io/hashmap/name
	Request Body – Rohit Patil

4.	To get value for given key from bound hashmap service instance

	GET -> http://SERVICE_CLIENT_ROUTE_URL/hashmap/KEY
	
	Example:
	GET -> http://hashmap-service-client-plummy-assimilator.cfapps.io/hashmap/name

5.	To delete value for given key from bound hashmap service instance
	
	DELETE -> http://SERVICE_CLIENT_ROUTE_URL/hashmap/KEY
	
	Example:
	DELETE -> http://hashmap-service-client-plummy-assimilator.cfapps.io/hashmap/name 

