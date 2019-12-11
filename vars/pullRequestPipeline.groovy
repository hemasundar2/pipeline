def call(Object name, String database ) {
	pipeline {
		agent any

		parameters {
			string defaultValue: '', description: 'The target revision being merged into', name: 'dburl', trim: false
			string defaultValue: '', description: 'The source revision for the merge', name: 'env', trim: false
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
