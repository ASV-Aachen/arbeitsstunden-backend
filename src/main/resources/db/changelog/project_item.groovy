databaseChangeLog {
    changeSet(id: 'create-project_item-table', author: 'ralf.bettermann') {
        createTable(tableName: 'project_item') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'project_id', type: 'UUID') {
                constraints(nullable: 'false')
            }
            column(name: 'season', type: 'int') {
                constraints(nullable: 'false')
            }
            column(name: 'title', type: 'varchar(1024)') {
                constraints(nullable: 'false')
            }
            column(name: 'description', type: 'varchar(1024)')
        }
    }
}