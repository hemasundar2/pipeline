def call(Object name, String database, String context ) {
	pipeline {
   agent any

   stages {

      stage('Hello') {
         steps {
            echo 'Hello World   '
		 test("Test", context+"_"+database+"_CORE")
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
def test(String a, String s)
{
	 echo 'Hello World 3  '+a+s
}
