package mx.finerio.synchronizer.aop

import mx.finerio.synchronizer.domain.BankConnection

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
class BankConnectionServiceFindLast {

  final static Logger log = LoggerFactory.getLogger(
      'mx.finerio.synchronizer.aop.BankConnectionServiceFindLast' )

  @Pointcut(
    value='execution(mx.finerio.synchronizer.domain.BankConnection mx.finerio.synchronizer.services.BankConnectionService.findLast(..)) && bean(bankConnectionService) && args(credentialId)',
    argNames='credentialId'
  )
  public void findLast( String credentialId ) {}

  @Before('findLast(credentialId)')
  void before( String credentialId ) {
    log.info( "<< credentialId: {}", credentialId )
  }

  @AfterReturning(
    pointcut='findLast(String)',
    returning='response'
  )
  void afterReturning( BankConnection response ) {
    log.info( '>> response: {}', response )
  }

  @AfterThrowing(
    pointcut='findLast(String)',
    throwing='e'
  )
  void afterThrowing( Exception e ) {
    log.info( "XX ${e.class.simpleName} - ${e.message}" )
  }

}

