def call(Object name) {
	pipeline {
   agent any

   stages {
      stage('Hello') {
         steps {
            echo 'Hello World'+name
		               withCredentials([usernamePassword(credentialsId: 'e5f8e3f0-dd6f-44c8-a89d-29375ea34cd9', passwordVariable: 'PASSWORD', usernameVariable: 'USER_NAME')]) {
             echo 'Hello World 1'+USER_NAME
              }
         }
      }
   }
}
}
