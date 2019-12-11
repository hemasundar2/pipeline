def call(Object name, String database, String env,String dburl ) {
	pipeline {
		agent any

		parameters {
			string defaultValue: '', description: 'The target revision being merged into', name: 'database', trim: false
			string defaultValue: '', description: 'The source revision for the merge', name: 'PULL_REQUEST_ID', trim: false
			}
		environment {
			boolean skipBuild = false
		}

		stages {
			stage ('Checkout') {
				steps {
					// Clean the workspace before beginning
					echo 'Checkout started'+env


					echo 'Checkout Finished'
				}
			}

			stage ("Create Database") {


				steps{
					script{
					echo "Create database"+dburl
					bat 'liquibase --url=%dburl% --context=%env%'
					printTest()
					}
				}
			}

		}

	}
}
def printTest(){
	echo "Create database 1"
}
