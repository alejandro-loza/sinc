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
class ApiServiceUpdateCredential {

  final static Logger log = LoggerFactory.getLogger(
      'mx.finerio.synchronizer.aop.ApiServiceUpdateCredential' )

  @Pointcut(
    value='execution(java.util.Map mx.finerio.synchronizer.services.ApiService.updateCredential(..)) && bean(apiService) && args(id)',
    argNames='id'
  )
  public void updateCredential( String id ) {}

  @Before('updateCredential(id)')
  void before( String id ) {
    log.info( "<< id: {}", id )
  }

  @AfterReturning(
    pointcut='updateCredential(String)',
    returning='data'
  )
  void afterReturning( Map data ) {
    log.info( ">> data: {}", data )
  }

  @AfterThrowing(
    pointcut='updateCredential(String)',
    throwing='e'
  )
  void afterThrowing( Exception e ) {
    log.info( "XX ${e.class.simpleName} - ${e.message}" )
  }

}
