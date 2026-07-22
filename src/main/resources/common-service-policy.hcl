# Data access
path "secret/data/arya-banking/common-service" {
  capabilities = ["read"]
}

path "secret/data/arya-banking/common-service/*" {
  capabilities = ["read"]
}

# Metadata access (REQUIRED for KV v2)
path "secret/metadata/arya-banking/common-service" {
  capabilities = ["read"]
}

path "secret/metadata/arya-banking/common-service/*" {
  capabilities = ["read"]
}
