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
class MainFailedService {

  @Value('${synchronizer.credential_failed_max_threads}')
  Integer maxThreads

  @Value('${synchronizer.credential_failed_batch_timeout}')
  Integer batchTimeout

  @Autowired
  ApiService apiService

  @Autowired
  BankConnectionService bankConnectionService

  @Autowired
  CredentialService credentialService

  @Autowired
  FinancialInstitutionRepository financialInstitutionRepository

  @Scheduled(cron = '0 30 6,14,22 * * *')
  void runBanamex() {
    runByInstitutionId( 2L )
  }

  @Scheduled(cron = '0 32 14 * * *')
  void runSantander() {
    runByInstitutionId( 7L )
  }

  @Scheduled(cron = '0 34 6,14,22 * * *')
  void runHsbc() {
    runByInstitutionId( 8L )
  }

  @Scheduled(cron = '0 36 6,14,22 * * *')
  void runAmex() {
    runByInstitutionId( 9L )
  }

  @Scheduled(cron = '0 38 6,14,22 * * *')
  void runInvex() {
    runByInstitutionId( 10L )
  }

  @Scheduled(cron = '0 40 6,14,22 * * *')
  void runScotiabank() {
    runByInstitutionId( 11L )
  }

  @Scheduled(cron = '0 48 6,14,22 * * *')
  void runLiverpool() {
    runByInstitutionId( 15L )
  }

  @Scheduled(cron = '0 50 6,14,22 * * *')
  void runBancoppel() {
    runByInstitutionId( 16L )
  }

  private void runByInstitutionId( Long institutionId ) {

    if ( isInstitutionActive( institutionId ) ) {
      run( institutionId )
    }

  }

  private boolean isInstitutionActive( Long institutionId ) throws Exception {

    def instance = financialInstitutionRepository.findOne( institutionId )
    instance.status == 'ACTIVE'

  }

  private void run( Long institutionId ) throws Exception {

    def ids = getCredentialIds( institutionId )
    def indexPointer = 0

    while ( indexPointer < ids.size() ) {

      if ( !credentialsCanBeSynchronized() ) { return }
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

  private List getCredentialIds( institutionId ) throws Exception {

    def calendar = Calendar.instance
    calendar.add( Calendar.HOUR_OF_DAY, -8 )
    def to = new Date( calendar.time.time )
    calendar.add( Calendar.HOUR_OF_DAY, -168 )
    def from = new Date( calendar.time.time )
    credentialService.findAllFailedIdsByInstitutionId( institutionId, from, to )

  }

  private boolean credentialsCanBeSynchronized() throws Exception {

    def hour = Calendar.instance.get( Calendar.HOUR_OF_DAY )
    return hour >= 6 && hour <= 22

  }

}

