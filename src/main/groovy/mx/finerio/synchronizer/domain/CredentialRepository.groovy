package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CredentialRepository extends JpaRepository<Credential, String> {

  List <IdOnly> findAllByInstitutionIdAndProviderIdAndStatusAndLastUpdatedLessThanAndDateDeletedIsNull(
      Long institutionId, Long providerId, String status, Date lastUpdated )

}

interface IdOnly {
  String getId()
}
