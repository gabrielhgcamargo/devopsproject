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
        bat 'docker-compose up -d --no-color --wait'
        bat 'docker compose ps'
      }
    }
    stage('Run tests against the container') {
      steps {
        bat 'curl -s http://172.17.0.2:9090'
      }
    }
    stage('Send Slack Message') {
      steps {
        slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#devopsproject', color: 'good', message: 'The Docker image is functioning as intended, all tests have passed successfully.', tokenCredentialId: 'slack-demo'
      }
    }
  }
  
}