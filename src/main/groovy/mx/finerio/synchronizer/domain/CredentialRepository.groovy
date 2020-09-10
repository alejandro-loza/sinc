package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CredentialRepository extends JpaRepository<Credential, String> {

  List <IdOnly> findAllByInstitutionIdAndProviderIdAndStatusAndAutomaticFetchingIsTrueAndLastUpdatedLessThanAndDateDeletedIsNull(
      Long institutionId, Long providerId, String status, Date lastUpdated )

  List <IdOnly> findAllByInstitutionIdAndStatusAndStatusCodeNotAndAutomaticFetchingIsTrueAndLastUpdatedGreaterThanEqualAndLastUpdatedLessThanEqualAndDateDeletedIsNull(
      Long institutionId, String status, String statusCode, Date from, Date to )

}

interface IdOnly {
  String getId()
}
