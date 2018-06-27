package mx.finerio.synchronizer.domain

import javax.persistence.*

import groovy.transform.ToString

@Entity
@Table(name = 'bank_connections')
@ToString(includePackage = false, includeNames = true)
class BankConnection {

  @Id @GeneratedValue
  @Column(name = 'id', updatable = false)
  Long id

  @Column(name = 'credential_id', nullable = false)
  String credentialId

  @Column(name = 'start_date', nullable = false)
  Date startDate

}
