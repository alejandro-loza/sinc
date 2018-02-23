package mx.finerio.synchronizer.domain

import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'credential')
@ToString(includeNames = true, includePackage = false)
class Credential {

  @Id
  @Column(name = 'id', nullable = false, updatable = false)
  String id

  @Column(name = 'institution_id', nullable = false)
  Long institutionId

  @Column(name = 'provider_id', nullable = true)
  Long providerId

  @Column(name = 'status', nullable = false)
  String status

  @Column(name = 'date_deleted', nullable = true)
  Date dateDeleted

}
