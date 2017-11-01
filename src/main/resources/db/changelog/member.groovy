databaseChangeLog {
    changeSet(id: 'create-member-table', author: 'ralf.bettermann') {
        createTable(tableName: 'member') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'user_id', type: 'UUID')
            column(name: 'first_name', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
            column(name: 'last_name', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
        }
    }
}