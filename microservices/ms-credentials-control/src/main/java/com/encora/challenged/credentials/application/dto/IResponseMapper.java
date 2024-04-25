package com.encora.challenged.credentials.application.dto;
import com.encora.challenged.credentials.domain.model.CredentialModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IResponseMapper {
  SignupResponse entityToApi(CredentialModel model);
  CredentialModel apiToEntity(SignupResponse api);
}
