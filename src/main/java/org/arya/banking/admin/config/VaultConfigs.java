package org.arya.banking.admin.config;

import lombok.Data;
import org.arya.banking.admin.dto.AppRole;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "vault")
public class VaultConfigs {

    private String vaultUri;
    private AppRole appRole;
}
