# Data access (KV v2 data path)
path "secret/data/arya-banking/audit-service" {
  capabilities = ["read"]
}

path "secret/data/arya-banking/audit-service/*" {
  capabilities = ["read"]
}

# Logical access (KV v2 - Spring Cloud Vault uses this)
path "secret/arya-banking/audit-service" {
  capabilities = ["read"]
}

path "secret/arya-banking/audit-service/*" {
  capabilities = ["read"]
}

# Metadata access (REQUIRED for KV v2)
path "secret/metadata/arya-banking/audit-service" {
  capabilities = ["read"]
}

path "secret/metadata/arya-banking/audit-service/*" {
  capabilities = ["read"]
}