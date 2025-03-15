def call() {
    sh '''
       cd $WORKSPACE
       mvn clean install
    '''
}
