package mx.finerio.synchronizer.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SantanderService {

  @Autowired
  ApiService apiService

  @Autowired
  CredentialService credentialService

  @Scheduled(cron = '0 0 2 * * *')
  void run() {

    def ids = credentialService.findAllIdsByInstitutionId( 7L )

    ids.each {
      apiService.updateCredential( it )
    }

  }

}
