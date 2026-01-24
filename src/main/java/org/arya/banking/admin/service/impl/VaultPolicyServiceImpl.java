package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.service.VaultPolicyService;
import org.arya.banking.common.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.vault.core.VaultTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.arya.banking.common.utils.CommonUtils.loadConfig;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VaultPolicyServiceImpl implements VaultPolicyService {

    private static final String POLICY_PATH = "/sys/policies/acl/";
    private final VaultTemplate vaultTemplate;

    @Override
    public List<String> getPolicies() {
        return vaultTemplate.list(POLICY_PATH);
    }

    @Override
    public VaultResponseDto uploadPolicy(String service) throws IOException {
        String policyName = resolvePolicyName(service);
        String policy = loadConfig(resolvePolicyPath(policyName));
        Map<String, Object> body = new HashMap<>();
        body.put("policy", policy);
        vaultTemplate.write(resolvePolicy(policyName), body);
        return new VaultResponseDto("200", "Policy uploaded successfully");
    }

    @Override
    public VaultResponseDto deletePolicy(String service) {
        String policyName = resolvePolicyName(service);
        vaultTemplate.delete(resolvePolicy(policyName));
        return new VaultResponseDto("200", "Vault policy deleted successfully");
    }

    private static String resolvePolicy(String policyName) {
        return POLICY_PATH + policyName;
    }

    private String resolvePolicyPath(String name) {
        return String.format(name + ".hcl");
    }

    private String resolvePolicyName(String service) {
        return String.format(service+"-policy");
    }


}
