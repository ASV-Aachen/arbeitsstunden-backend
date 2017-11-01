databaseChangeLog {
    changeSet(id: 'create-user-table', author: 'ralf.bettermann') {
        createTable(tableName: 'user_') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'member_id', type: 'UUID')
            column(name: 'email', type: 'varchar(255)') {
                constraints(nullable: 'false', unique: 'true')
            }
        }
    }
}