package mx.finerio.synchronizer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@SpringBootApplication
@EnableScheduling
class SynchronizerApplication {

  static void main( String[] args ) {
    SpringApplication.run( SynchronizerApplication, args )
  }

  @Bean
  TaskScheduler poolScheduler() {

    def scheduler = new ThreadPoolTaskScheduler()
    scheduler.threadNamePrefix = 'poolScheduler'
    scheduler.poolSize = 1
    scheduler

  }

}
