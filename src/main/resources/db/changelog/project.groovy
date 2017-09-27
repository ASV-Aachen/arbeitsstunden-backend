databaseChangeLog {
    changeSet(id: 'create-projects-table', author: 'ralf.bettermann') {
        createTable(tableName: 'project_entity') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'name', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
            column(name: 'description', type: 'varchar(1024)')
        }
    }
}