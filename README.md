# Card Opening Service
## Description
This service is responsible for cards and card accounts opening for the client.

## Technical details

- [Source code](https://gitlab.starboost.aval/applications/cards/card-opening-service) in GitLab.
- Jenkins [CI pipeline](https://jenkins.avalaunch.aval/job/cards/job/card-opening-ci/).
- [SonarQube project](https://sonar.avalaunch.aval/dashboard?id=card-opening) with static code analysis results.
- Service [configuration parameters](https://gitlab.starboost.aval/applications/cards/card-opening-service/-/blob/master/src/main/resources/application.yml).
- Configuration for [DEV](https://gitlab.starboost.aval/applications/cards/cards-deployment/-/blob/master/environments/dev/values.yaml), [UAT](https://gitlab.starboost.aval/applications/cards/cards-deployment/-/blob/master/environments/uat/values.yaml), and [PROD](https://gitlab.starboost.aval/applications/cards/cards-deployment/-/blob/master/environments/prod/values.yaml) environments.

## Architectural diagram

![Architectural diagram](./docs/card-opening-architecture.jpg)


## Service API

Service exposes [REST API](https://cards-admin.dev.apps.test-avalaunch.aval/swagger-ui/?urls.primaryName=card-opening) for cards and card accounts opening requests.

Some REST API endpoints are protected with JWT based authorization (personal or service to service).

All information about request processing is provided as events in dedicated Kafka topics. After the card is opened all card management operations could be done via Card Service. 

## Data storages

#### PostgreSQL DB

**card-opening** - main DB schema to store cards and card accounts opening requests.

**onboarding-camunda** - common domain level DB for Camunda engines.

#### Kafka topics

| Topic name | Cluster | Permissions | Description |
| ---------- | ------- | ----------- | ----------- |
| **cardrequest-events** | EXTERNAL | WRITE | Card opening request processing events |
| **cardaccountrequest-events** | INTERNAL | WRITE | Card accounts opening request processing events |

## Integrations

| System | DEV | UAT | PROD | Purpose |
| ------ | --- | --- | ---- | ---- |
| CMD | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/dev/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/uat/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/prod/values.yaml) | To retrieve client links service integrates with CMD. |
| IBM MQ | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/dev/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/uat/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/prod/values.yaml) | For card accounts opening in IS-Card service integrates with IS-Card via IBM MQ. |
| BankMaster | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/dev/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/uat/values.yaml) | [config](https://gitlab.avalaunch.aval/applications/cards/cards-deployment/-/blob/master/environments/prod/values.yaml) | To link card accounts to BankMaster accounts service integrates with BankMaster via BMRS Gateway. |

## Implementation details

To orchestrate card accounts opening process embedded Camunda engine is used with common domain level DB to store process state.

When a card registration event is processed service generates a unique card token as the hash from account ID, client ID, and card ID. Then full card information is registered in the Card Service.

To link the registered card to card opening request service uses card account token and card product token. It means that collisions are possible when several additional cards are opened in parallel for the same client.

Embedded Camunda engine is used for orchestration of card accounts opening process. All integrations are done as the process steps.

During the card accounts opening process several accounts in different currencies could be requested. Due to a lack of transactional support, it is possible that only part of the requested accounts is opened.
