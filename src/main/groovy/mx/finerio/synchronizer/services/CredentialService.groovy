package mx.finerio.synchronizer.services

import mx.finerio.synchronizer.domain.CredentialRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CredentialService {

  @Autowired
  CredentialRepository credentialRepository

  List findAllIdsByInstitutionId( Long institutionId ) throws Exception {

    credentialRepository.
        findAllByInstitutionIdAndProviderIdAndStatusAndDateDeletedIsNull(
            institutionId, 3L, 'ACTIVE' )*.id

  }

}
