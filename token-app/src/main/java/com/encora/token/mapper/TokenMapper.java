package com.encora.token.mapper;

import com.encora.token.controller.client.ClientCredentialRequest;
import com.encora.token.controller.client.TokenResponse;
import com.encora.token.model.domain.ClientCredential;
import com.encora.token.model.domain.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper {

    ClientCredential mapClientCredential(ClientCredentialRequest clientCredentialRequest);

    TokenResponse mapStarshipResponse(Token token);
}
