package mx.finerio.synchronizer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SynchronizerApplication {

  static void main( String[] args ) {
    SpringApplication.run( SynchronizerApplication, args )
  }

}
