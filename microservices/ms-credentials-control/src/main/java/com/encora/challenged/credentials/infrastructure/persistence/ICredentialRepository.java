package com.encora.challenged.credentials.infrastructure.persistence;

import com.encora.challenged.credentials.infrastructure.entity.CredentialEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICredentialRepository extends CrudRepository<CredentialEntity, Integer> {
  Optional<CredentialEntity> findByUserAndClusterKey(String user, String clusterKey);
  Optional<CredentialEntity> findByUser(String user);

  @Query("select c from CredentialEntity c where concat(c.user,c.clusterKey) =?1")
  Optional<CredentialEntity> findByUserCluster(String id);
}
