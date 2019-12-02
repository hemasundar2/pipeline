def call(Object name, String database, String context ) {
	pipeline {
		agent any

		options {

		}

		parameters {
			
		}

environment {
skipBuild = false
}

		stages {
			stage ('Checkout') {
				steps {
					// Clean the workspace before beginning
					echo 'Checkout started'
	

					echo 'Checkout Finished'
				}
			}

			stage ("Create Database") {

				when {
					// environment variables are always stored as Strings and need to be converted
					expression {!skipBuild.toBoolean()}
				}

				steps{
					
					}
				}

			}

			stage ("Build Database") {
				when {
					// environment variables are always stored as Strings and need to be converted
					expression {!skipBuild.toBoolean()}
				}

				steps {
					echo "Notifying Bitbucket of build in progress"

				}

				post {
					always {
						
					}

					success {
						// Build result needs to be updated for bitbucket notification to work; see https://github.com/jenkinsci/stashnotifier-plugin


					}

					failure {
						// Build result needs to be updated for bitbucket notification to work; see https://github.com/jenkinsci/stashnotifier-plugin

					}
				}
			}
		}
	}
}
