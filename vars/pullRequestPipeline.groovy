def call(Object name, String database, String env, String dburl ) {
	pipeline {
		agent any
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
					env.DB_URL = dburl
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
