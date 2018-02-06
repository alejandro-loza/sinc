package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CredentialRepository extends JpaRepository<Credential, String> {

  List <IdOnly> findAllByInstitutionIdAndProviderIdAndStatus(
      Long institutionId, Long providerId, String status )

}

interface IdOnly {
  String getId()
}
