databaseChangeLog {
    include(file: 'changelog/project.groovy', relativeToChangelogFile: true)
    include(file: 'changelog/user.groovy', relativeToChangelogFile: true)
}