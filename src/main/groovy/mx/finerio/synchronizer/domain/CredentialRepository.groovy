package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CredentialRepository extends JpaRepository<Credential, String> {

  List <IdOnly> findAllByInstitutionIdAndProviderId(
      Long institutionId, Long providerId )

}

interface IdOnly {
  String getId()
}
