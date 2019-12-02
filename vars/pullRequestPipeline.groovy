def call(String project, String repo, String baseDirectory, String driver, String url, String uname, String password) {
	pipeline {
		agent any

		options {
			buildDiscarder logRotator(
					artifactDaysToKeepStr: '',
					artifactNumToKeepStr: '2',
					daysToKeepStr: '',
					numToKeepStr: '10'
					)
		}

		parameters {
			string defaultValue: '', description: 'The target revision being merged into', name: 'PULL_REQUEST_TO_HASH', trim: false
			string defaultValue: '', description: 'The source revision for the merge', name: 'PULL_REQUEST_FROM_HASH', trim: false
			string defaultValue: '', description: 'Pull Request ID', name: 'PULL_REQUEST_ID', trim: false
			string defaultValue: '', description: 'Name of the branch being merged into', name: 'PULL_REQUEST_TO_BRANCH', trim: false
		}

		environment {
			skipBuild = false
		}

		stages {
			stage ('Checkout') {
				steps {
					// Clean the workspace before beginning
					echo 'Checkout started'
					deleteDir()

					checkoutFromRepo(project, repo)

					bat '''\
            git reset --hard %PULL_REQUEST_TO_HASH% || exit \b 1
            git status
            git merge %PULL_REQUEST_FROM_HASH% --no-commit --no-ff || exit \b 1
            '''.stripIndent()

					script {
						try {
							bat "git status --porcelain | findstr ${baseDirectory}"
						} catch (Exception e) {
							echo "no changes found in ${baseDirectory}; marking build to be skipped"
							skipBuild = true
						}
					}

					echo 'Checkout Finished'
				}
			}

			stage ("Build and Test") {
				when {
					// environment variables are always stored as Strings and need to be converted
					expression {!skipBuild.toBoolean()}
				}

				steps {
					echo "Notifying Bitbucket of build in progress"


					notifyBitbucketServer(PULL_REQUEST_FROM_HASH)

					echo "Creating Database"
					createDatabase(driver, url, uname, password)


					dir (env.WORKSPACE + '/' + baseDirectory) {
						withCredentials([
							usernamePassword(credentialsId: '90f08200-27ae-42d7-afea-eea9cd1bdd4c', passwordVariable: 'PASSWORD', usernameVariable: 'USER_NAME')
						]) {
							bat 'liquibase --driver=com.microsoft.sqlserver.jdbc.SQLServerDriver --url=jdbc:sqlserver://hrsabmssql1dev.nih.gov; --changeLogFile=changelog-master.yml --liquibaseCatalogName=LIQUIBASE_UMS_CORE --defaultSchemaName=dbo --username=%USER_NAME% --password=%PASSWORD% --logLevel=debug update'

						}

					}
				}

				post {
					always {
						// Drop database
						echo "Droping Database"
						dropDatabase()
					}

					success {
						// Build result needs to be updated for bitbucket notification to work; see https://github.com/jenkinsci/stashnotifier-plugin
						script {currentBuild.result = 'SUCCESS'}
						notifyBitbucketServer(PULL_REQUEST_FROM_HASH)

					}

					failure {
						// Build result needs to be updated for bitbucket notification to work; see https://github.com/jenkinsci/stashnotifier-plugin
						script {currentBuild.result = 'FAILURE'}
						notifyBitbucketServer(PULL_REQUEST_FROM_HASH)
					}
				}
			}
		}
	}
}

import groovy.sql.Sql

import java.sql.*

def createDatabase(){
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
	 
		  vsql = Sql.newInstance("jdbc:sqlserver://hrsabmssql1dev.nih.gov", "sys","VSA62Q7afS8u5oWHA3nC", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
	
		  vsql.execute("create database LIQUIBASE_UMS_CORE")
		  vsql.close()

}

def dropDatabase(){
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
	 
		  vsql = Sql.newInstance("jdbc:sqlserver://hrsabmssql1dev.nih.gov", "sys","VSA62Q7afS8u5oWHA3nC", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
	
		  vsql.execute("drop database LIQUIBASE_UMS_CORE")
		  vsql.close()

}