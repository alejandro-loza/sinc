package mx.finerio.synchronizer.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FinancialInstitutionRepository
    extends JpaRepository<FinancialInstitution, Long> {

}
