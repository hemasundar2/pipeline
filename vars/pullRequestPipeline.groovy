def call(Object name, String database, String url ) {
	pipeline {
   agent any
   id = 2
   stages {
      stage('Hello') {
         steps {
            echo 'Hello World'+id+"_"+database
		               withCredentials([usernamePassword(credentialsId: '5f71cca3-f7b0-416a-b799-afb61fa4bb9e', passwordVariable: 'DB_PASSWORD', usernameVariable: 'DB_USER')]) {
				       echo 'Hello World 1'+DB_USER
	    // createDatabase(DB_USER,DB_PASSWORD,database, url)
}
              
         }
      }
   }
}
}

