package org.arya.banking.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNullApi;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.RestTemplateBuilder;
import org.springframework.vault.client.RestTemplateFactory;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class AppRoleConfig extends AbstractVaultConfiguration {

    private final VaultConfigs vaultConfigs;

    @Bean
    public VaultTemplate vaultTemplate() {
        return new VaultTemplate(vaultEndpoint(), clientAuthentication());
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(vaultConfigs.getVaultUri());
    }

    @Bean
    @Primary
    public RestTemplateFactory restTemplateFactory() {
        return new RestTemplateFactory() {
            @Override
            public RestTemplate create(Consumer<RestTemplateBuilder> customizer) {
                return null;
            }
        };
    }

    @Override
    public ClientAuthentication clientAuthentication() {

        AppRoleAuthenticationOptions authenticationOptions = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided(vaultConfigs.getAppRole().getRoleId()))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided(vaultConfigs.getAppRole().getSecretId()))
                .build();
        return new AppRoleAuthentication(authenticationOptions, restOperations());
    }
}
