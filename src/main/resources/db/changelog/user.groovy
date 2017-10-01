databaseChangeLog {
    changeSet(id: 'create-user-table', author: 'ralf.bettermann') {
        createTable(tableName: 'user_entity') {
            column(name: 'id', type: 'UUID') {
                constraints(primaryKey: 'true', nullable: 'false')
            }
            column(name: 'first_name', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
            column(name: 'last_name', type: 'varchar(255)') {
                constraints(nullable: 'false')
            }
            column(name: 'email', type: 'varchar(255)') {
                constraints(nullable: 'false', unique: 'true')
            }
        }
    }
}