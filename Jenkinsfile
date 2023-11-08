pipeline {
  agent any
  stages {
    stage("verify tooling") {
      steps {
        bat '''
          docker version
          docker info
          docker compose version 
          curl --version
          '''
      }
    }
    stage('Prune Docker data') {
      steps {
        bat 'docker system prune -a --volumes -f'
      }
    }
    stage('Start container') {
      steps {
        bat 'docker compose up -d --no-color --wait'
        bat 'docker compose ps'
      }
    }
    stage('Run tests against the container') {
      steps {
        bat 'docker exec -it pipelinecomjenkinsfile curl -X GET http://localhost:9090/curso'
        bat 'docker exec -it pipelinecomjenkinsfile curl -X POST http://localhost:9090/curso'
        bat 'docker exec -it pipelinecomjenkinsfile curl -X GET http://localhost:9090/aluno'
        bat 'docker exec -it pipelinecomjenkinsfile curl -X POST http://localhost:9090/aluno'
      }
    }
  }
  
}