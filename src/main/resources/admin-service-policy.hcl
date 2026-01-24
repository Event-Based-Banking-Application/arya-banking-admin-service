# Full access to all arya-banking secrets (admin-service only)

# Data (KV v2)
path "secret/data/arya-banking/*" {
  capabilities = ["create", "read", "update", "delete", "patch"]
}

# Metadata (KV v2)
path "secret/metadata/arya-banking/*" {
  capabilities = ["read", "delete"]
}

# Enable auth methods
path "sys/auth/*" {
  capabilities = ["create", "update", "read"]
}

# Manage AppRole roles
path "auth/approle/role/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}

# Generate secret-id
path "auth/approle/role/*/secret-id" {
  capabilities = ["create", "read"]
}

# Read role-id
path "auth/approle/role/*/role-id" {
  capabilities = ["read"]
}

path "sys/policies/acl/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}