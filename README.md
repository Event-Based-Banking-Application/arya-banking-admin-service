# Arya Banking Admin Service

Infrastructure administration microservice — programmatic Vault secrets and AppRole management, Keycloak role and client provisioning.

## Quick Start

```powershell
# Prerequisites: Docker infra running, Vault unsealed, common library built
mvn clean spring-boot:run
```

The service starts on port **8089** and registers as `ARYA-BANKING-ADMIN-SERVICE` in Eureka.

## Links

- [Local Development Setup](https://event-based-banking-application.github.io/arya-banking/docs/local-development/)
- [Admin Service Docs](https://event-based-banking-application.github.io/arya-banking/docs/admin-service/)
