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
    script {
      def response = bat(script: 'curl -s http://localhost:9090/curso', returnStatus: true)
      if (response == 0) {
        def jsonResponse = bat(script: 'curl -s http://localhost:9090/curso', returnStdout: true)
      } else {
        error "Failed to retrieve JSON from the server"
      }
    }
  }
}

  }
  
}