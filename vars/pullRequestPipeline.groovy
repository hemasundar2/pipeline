def call(Object name, String database, String context ) {
	pipeline {
   agent any
script {
                 foo = "bar"
            }
   stages {
      stage('Hello') {
         steps {
            echo 'Hello World   '+context+"_"+database+"_CORE"+foo
		//  echo 'database name'+id+"_"+db
		               withCredentials([usernamePassword(credentialsId: '5f71cca3-f7b0-416a-b799-afb61fa4bb9e', passwordVariable: 'DB_PASSWORD', usernameVariable: 'DB_USER')]) {
				       echo 'Hello World 1'+database
	    // createDatabase(DB_USER,DB_PASSWORD,database, url)
}
              
         }
      }
   }
}
}

