databaseChangeLog {
    changeSet(id: 'create-reduction-table', author: 'ralf.bettermann') {
        createTable(tableName: 'reduction') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'season', type: 'int') {
                constraints(nullable: 'false')
            }
            column(name: 'member_id', type: 'UUID') {
                constraints(nullable: 'false')
            }
            column(name: 'status', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
            column(name: 'reduction', type: 'int') {
                constraints(nullable: 'false')
            }
        }
        addUniqueConstraint(tableName:'reduction', columnNames: 'season, member_id')
    }
}
