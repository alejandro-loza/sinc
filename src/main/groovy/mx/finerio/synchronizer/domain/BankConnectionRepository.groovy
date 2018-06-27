package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface BankConnectionRepository extends JpaRepository<BankConnection, Long> {

  BankConnection findFirstByCredentialIdOrderByStartDateDesc(
      String credentialId )

}
