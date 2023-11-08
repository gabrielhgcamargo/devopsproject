pipeline {
  agent any
  environment {
        SPRING_PROFILES_ACTIVE = 'homol' 
    }
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
    script {
      def apiUrl = sh(script: 'docker compose port api 9090', returnStatus: true).trim()
      if (apiUrl) {
        bat "curl $apiUrl"
      } else {
        error "O serviço 'api' não está disponível ou a porta 9090 não foi mapeada."
      }
    }
  }
}

  }
  
}