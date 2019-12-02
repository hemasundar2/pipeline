def call(Object name) {
	pipeline {
   agent any

   stages {
      stage('Hello') {
         steps {
            echo 'Hello World'+name
		               withCredentials([usernamePassword(credentialsId: '5f71cca3-f7b0-416a-b799-afb61fa4bb9e', passwordVariable: 'DB_PASSWORD', usernameVariable: 'DB_USER')]) {
				       echo 'Hello World 1'+DB_USER
	     createDatabase(DB_USER,DB_PASSWORD)
}
              
         }
      }
   }
}
}

import groovy.sql.Sql

import java.sql.*

def createDatabase(String user, String password){
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
     
          vsql = Sql.newInstance("jdbc:sqlserver://hrsabmssql1dev.nih.gov", user,password, "com.microsoft.sqlserver.jdbc.SQLServerDriver")
    echo 'Hello World 2'
          //vsql.execute("create database LIQUIBASE_UMS_CORE")
          vsql.close()  

}
