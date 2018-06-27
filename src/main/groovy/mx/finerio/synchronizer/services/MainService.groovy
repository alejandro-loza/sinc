package mx.finerio.synchronizer.services

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import mx.finerio.synchronizer.domain.FinancialInstitution
import mx.finerio.synchronizer.domain.FinancialInstitutionRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MainService {

  @Value('${synchronizer.max_threads}')
  Integer maxThreads

  @Value('${synchronizer.batch_timeout}')
  Integer batchTimeout

  @Autowired
  ApiService apiService

  @Autowired
  CredentialService credentialService

  @Autowired
  FinancialInstitutionRepository financialInstitutionRepository

  @Scheduled(cron = '0 0 3 * * 3')
  void runBanamex() {
    runByInstitutionId( 2L )
  }

  @Scheduled(cron = '0 0 2 * * 3')
  void runSantander() {
    runByInstitutionId( 7L )
  }

  @Scheduled(cron = '0 1 2 * * 3')
  void runHsbc() {
    runByInstitutionId( 8L )
  }

  @Scheduled(cron = '0 2 2 * * 3')
  void runAmex() {
    runByInstitutionId( 9L )
  }

  @Scheduled(cron = '0 3 2 * * 3')
  void runInvex() {
    runByInstitutionId( 10L )
  }

  @Scheduled(cron = '0 4 2 * * 3')
  void runScotiabank() {
    runByInstitutionId( 11L )
  }

  @Scheduled(cron = '0 0 9 * * 3')
  void runBanorte() {
    runByInstitutionId( 12L )
  }

  @Scheduled(cron = '0 5 2 * * 3')
  void runInbursa() {
    runByInstitutionId( 13L )
  }

  private void runByInstitutionId( Long institutionId ) {

    if ( isInstitutionActive( institutionId  ) ) {
      run( institutionId )
    }

  }

  private boolean isInstitutionActive( Long institutionId ) throws Exception {

    def instance = financialInstitutionRepository.findOne( institutionId )
    instance.status == 'ACTIVE'

  }

  private void run( Long institutionId ) throws Exception {

    def ids = credentialService.findAllIdsByInstitutionId( institutionId )
    def indexPointer = 0

    while ( indexPointer < ids.size() ) {

      def executorService = Executors.newCachedThreadPool()
      def totalThreads = 0

      while ( indexPointer < ids.size() && totalThreads < maxThreads ) {

        def myThread = new MyThread()
        myThread.apiService = apiService
        myThread.credentialId = ids[ indexPointer ]
        executorService.execute( myThread )
        indexPointer++
        totalThreads++

      }

      executorService.shutdown()
      executorService.awaitTermination( 10, TimeUnit.MINUTES )
      Thread.sleep( batchTimeout )

    }

  }

}

class MyThread implements Runnable {

  ApiService apiService
  String credentialId

  void run() {
    apiService.updateCredential( credentialId )
  }

}
