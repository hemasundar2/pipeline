def call(Object name, String database, String env ) {
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

				when {
					// environment variables are always stored as Strings and need to be converted
					expression {!skipBuild.toBoolean()}
				}

				steps{
					echo "Create database"
					printTest()
				}
			}

		}

	}
}
def printTest(){
	echo "Create database 1"
}
