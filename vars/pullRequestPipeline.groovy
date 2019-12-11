def call(Object name, String database, String env, String dburl ) {
	pipeline {
		agent any
		environment {
			boolean skipBuild = false
			DB_URL = dburl
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
					bat 'liquibase --url=%DB_URL% --context=%env%'
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
