databaseChangeLog {
    include(file: 'changelog/member.groovy', relativeToChangelogFile: true)
    include(file: 'changelog/user.groovy', relativeToChangelogFile: true)
    include(file: 'changelog/project.groovy', relativeToChangelogFile: true)
    include(file: 'changelog/project_item.groovy', relativeToChangelogFile: true)
    include(file: 'changelog/project_item_hour.groovy', relativeToChangelogFile: true)
}