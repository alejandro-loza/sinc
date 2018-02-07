package mx.finerio.synchronizer.domain

import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'financial_institution')
@ToString(includeNames = true, includePackage = false)
class FinancialInstitution {

  @Id
  @Column(name = 'id', nullable = false, updatable = false)
  Long id

  @Column(name = 'status', nullable = false)
  String status

}
