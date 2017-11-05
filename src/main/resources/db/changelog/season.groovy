databaseChangeLog {
    changeSet(id: 'create-season-table', author: 'ralf.bettermann') {
        createTable(tableName: 'season') {
            column(name: 'year', type: 'int') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'obligatory_minutes', type: 'int') {
                constraints(nullable: 'false')
            }
        }
    }
}