def call(Object name) {
	pipeline {
   agent any

   stages {
      stage('Hello') {
         steps {
            echo 'Hello World'+name
		               withCredentials([usernamePassword(credentialsId: 'e5f8e3f0-dd6f-44c8-a89d-29375ea34cd9', passwordVariable: 'DB_PASSWORD', usernameVariable: 'DB_USER')]) {
    
				       echo 'Hello World 1'+USER_NAME
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
