package com.hints.authserver.service;

import com.hints.authserver.model.SysClient;

public interface SysClientService extends BaseService<SysClient> {
    SysClient findByClientId(String clientId);
}
