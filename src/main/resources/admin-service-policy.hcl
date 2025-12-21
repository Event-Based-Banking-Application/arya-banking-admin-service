# Full access to all arya-banking secrets (admin-service only)

# Data (KV v2)
path "secret/data/arya-banking/*" {
  capabilities = ["create", "read", "update", "delete", "patch"]
}

# Metadata (KV v2)
path "secret/metadata/arya-banking/*" {
  capabilities = ["read", "delete"]
}
