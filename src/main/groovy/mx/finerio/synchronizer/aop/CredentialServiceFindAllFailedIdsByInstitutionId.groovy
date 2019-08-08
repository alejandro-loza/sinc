package mx.finerio.synchronizer.aop

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component

@Component
@Aspect
class CredentialServiceFindAllFailedIdsByInstitutionId {

  final static Logger log = LoggerFactory.getLogger(
      'mx.finerio.synchronizer.aop.CredentialServiceFindAllFailedIdsByInstitutionId' )

  @Pointcut(
    value='execution(java.util.List mx.finerio.synchronizer.services.CredentialService.findAllFailedIdsByInstitutionId(..)) && bean(credentialService) && args(institutionId, from, to)',
    argNames='institutionId, from, to'
  )
  public void findAllFailedIdsByInstitutionId( Long institutionId, Date from, Date to ) {}

  @Before('findAllFailedIdsByInstitutionId(institutionId, from, to)')
  void before( Long institutionId, Date from, Date to ) {
    log.info( "<< institutionId: {}, from:{} , to: {}", institutionId, from, to )
  }

  @AfterReturning(
    pointcut='findAllFailedIdsByInstitutionId(Long, java.util.Date, java.util.Date)',
    returning='data'
  )
  void afterReturning( List data ) {
    log.info( ">> data: {} elements", data?.size() )
  }

  @AfterThrowing(
    pointcut='findAllFailedIdsByInstitutionId(Long, java.util.Date, java.util.Date)',
    throwing='e'
  )
  void afterThrowing( Exception e ) {
    log.info( "XX ${e.class.simpleName} - ${e.message}" )
  }

}
