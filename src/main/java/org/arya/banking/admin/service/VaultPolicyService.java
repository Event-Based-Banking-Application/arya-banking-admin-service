package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.VaultResponseDto;

import java.io.IOException;
import java.util.List;

public interface VaultPolicyService {

    List<String> getPolicies();

    VaultResponseDto uploadPolicy(String service) throws IOException;

    VaultResponseDto deletePolicy(String service);
}
