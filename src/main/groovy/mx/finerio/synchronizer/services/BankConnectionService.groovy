package mx.finerio.synchronizer.services

import mx.finerio.synchronizer.domain.BankConnection
import mx.finerio.synchronizer.domain.BankConnectionRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankConnectionService {

  @Autowired
  BankConnectionRepository bankConnectionRepository

  BankConnection findLast( String credentialId ) throws Exception {

    bankConnectionRepository
        .findFirstByCredentialIdOrderByStartDateDesc( credentialId )

  }

}

