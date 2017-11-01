databaseChangeLog {
    changeSet(id: 'create-project_item_hour-table', author: 'ralf.bettermann') {
        createTable(tableName: 'project_item_hour') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'project_item_id', type: 'UUID') {
                constraints(nullable: 'false')
            }
            column(name: 'member_id', type: 'UUID') {
                constraints(nullable: 'false')
            }

            column(name: 'duration', type: 'int') {
                constraints(nullable: 'false')
            }
        }
    }
}