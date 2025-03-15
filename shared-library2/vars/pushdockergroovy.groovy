def call(String registry, String imageName, String tag) {
    sh """
       docker push ${registry}/${imageName}:${tag}
    """
}