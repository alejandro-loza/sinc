package mx.finerio.synchronizer.services

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MainService {

  @Value('${synchronizer.max_threads}')
  Integer maxThreads

  @Autowired
  ApiService apiService

  @Autowired
  CredentialService credentialService

  @Scheduled(cron = '0 0 2 * * *')
  void runSantander() {
    run( 7L )
  }

  private void run( Long institutionId ) {

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
