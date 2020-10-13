# iss-policy-service

We decided to implement it in an extremely simplified version of an insurance sales system to test the following aspects of microservice development:

* Project creation and development
* Access of both relational and NoSQL databases
* Blocking and non-blocking operations implementation
* Microservice to microservice communication (synchronous and asynchronous)
* Service discovery
* Running background jobs

The iss-policy-service microservice is responsible to created offers, converted offers to insurance policies, and allowed for termination of policies.
In this service, we demonstrated use of a CQRS pattern for better read/write operation isolation. This service demonstrated two ways of communicating between services: synchronous REST-based calls to a iss-pricing-service through an HTTP client to get the price, and asynchronous event-based calls using Apache Kafka to publish information about newly created policies. In this service we also accessed an RDBMS using JPA.
