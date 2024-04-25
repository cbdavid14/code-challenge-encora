package com.encora.challenged.credentials.infrastructure.entity;
import com.encora.challenged.credentials.domain.model.CredentialModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEntityMapper {

  CredentialEntity modelToEntity(CredentialModel model);
  CredentialModel entityToModel(CredentialEntity api);
}
