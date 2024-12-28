def call(Map envVars) {
    // Update Deployment YAML
    echo 'Updating deployment file...'
    sh """
    sed -i 's|image: .*|image:${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}|g' deployment.yaml
   
    echo 'Pushing New Deployment file to Github..'
    git config user.name "${envVars.githubUsername}"
    git config user.email "${envVars.githubEmail}"
    git remote set-url origin ${envVars.gitRepo}
    git checkout ${envVars.branch}
    git pull origin ${envVars.branch}
    git add .
    git commit -m "Automated commit by Jenkins" || echo "No changes to commit"
    git push origin ${envVars.branch}
    """

}
