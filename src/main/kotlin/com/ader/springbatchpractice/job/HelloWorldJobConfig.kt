package com.ader.springbatchpractice.job

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

private val logger = KotlinLogging.logger {}

@Configuration
class HelloWorldJobConfig {

  @Bean
  fun job(
    jobRepository: JobRepository,
    transactionManager: PlatformTransactionManager
  ): Job {
    return JobBuilder("helloWorldJob", jobRepository)
      .start(step(jobRepository, transactionManager))
      .build()
  }

  @Bean
  fun step(
    jobRepository: JobRepository,
    transactionManager: PlatformTransactionManager
  ): Step {
    return StepBuilder("collectStep", jobRepository)
      .tasklet( {_,_ ->
        logger.info { "hello, world" }
        RepeatStatus.FINISHED
      }, transactionManager)
      .build()
  }
}
