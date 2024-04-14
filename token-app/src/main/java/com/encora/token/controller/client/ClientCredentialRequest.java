package com.encora.token.controller.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientCredentialRequest {

  private String clientId;
  private String password;

}
