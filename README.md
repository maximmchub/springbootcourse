# Customer Request Service
## Description
This service is responsible for creating and process clients requests.

## Technical details

- [Source code](https://github.com/maximmchub/springbootcourse/) in GitHub.
- Jenkins [CI pipeline](https://jenkins.devops.aval:8443/job/BASS/job/svc_333/).
- [SonarQube project](http://sonar.devops.aval:9003/dashboard?id=bass_471) with static code analysis results.
- Service [configuration parameters](https://github.com/maximmchub/springbootcourse/blob/master/src/main/resources/application.yaml).
- Configuration for [DEV](), [UAT](), and [PROD]() environments.

## Architectural diagram

![Architectural diagram](./doc/customer-request-service.JPG)

## Sequence diagram
![Sequence diagram](doc/sequence-diagramm.JPG)

## Service API

Service exposes [REST API](https://editor.swagger.io/?_ga=2.40922853.672648488.1640770285-1123841614.1594292781) for creating and processing client requests.

Some REST API endpoints are protected with JWT based authorization (personal or service to service).

## Data storages

#### PostgreSQL DB

**tickets** - main DB schema to store cliemt requests.

**history** - table to store ticket poscessing history and statuses.

#### Kafka topics

| Topic name | Cluster | Permissions | Description |
| ---------- | ------- | ----------- | ----------- |
| **prod.clients.event.ticket-created** | INTERNAL | WRITE | Ticket created request processing events |
| **prod.clients.event.ticket-changed** | INTERNAL | WRITE | Ticket changed request processing events |

## Integrations

## Implementation details

