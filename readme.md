# Octoroute

A lightweight headless, developer-first, general-purpose pipeline system written in Java.



## Releases

 - All releases can be found in the /release/ folder



## Different setups

 - Built in webserver
 - Lambda function



## Features

  - Integrates with multiple third party services
  - Brokers, queue and workers
  - DLQ (Dead Letter Queue)



## Integrations

 - Iterable - iterable.com
   - Update user by ID



## Getting Started

Compilation

 - `javac -cp octoroute.jar Main.java`

Execution

 - `java -cp ".;octoroute.jar" Main`


## Architecture overview

Webserver (Scheduled Worker Broker) Based

```mermaid
flowchart TD
    external["Third Party Service (api.iterable.com)"]
    integration["An Integration Service (For example Iterable)"]
    webserver["Built-in Webserver"]
    queue["Queue (Thread-safe queue of Scheduled Requests)"]
    worker["Worker (that runs in its own thread)"]
    network_service["Network Service"]
    broker["Queue Broker"]

    subgraph framework["Framework"]
        integration
        webserver
        broker
        queue
        worker
        network_service
    end

    webserver -- invokes an event to be sent --> integration
    integration --"Tells the broker to send a request" --> broker
    broker -- "Adds a Schedules Request to the queue" --> queue
    worker -- Worker Fetches schedules requests from --> queue
    worker -- "Tells the network service to send a request" --> network_service
    network_service -- sends request to --> external
```

Lambda (Immediate Broker) Based

```mermaid
flowchart TD
    external["Third Party Service (api.iterable.com)"]
    broker["Immediate Broker"]
    integration["An Integration Service (For example Iterable)"]
    gateway["Lambda Gateway"]
    network_service["Network Service"]

   subgraph framework["Framework"]
      integration
      broker
      network_service
   end

    gateway -- "invokes an event to be sent" --> integration
    integration -- "Tells the broker to execute a request" --> broker
    broker -- "Tells the network service to immediately send a request" --> network_service
    network_service -- "sends request to" --> external

```



## Software Principles

- Lightweight
- POJO
- SOLID
- OOP
- DI
- Self-Contained


## License

 - TBD
