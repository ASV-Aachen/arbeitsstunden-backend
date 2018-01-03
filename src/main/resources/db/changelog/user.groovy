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
    changeSet(id: 'add-password', author: 'ralf.bettermann') {
        createTable(tableName: 'password') {
            column(name: 'password', type: 'varchar(255)')
        }
    }
    changeSet(id: 'drop-table-password', author: 'ralf.bettermann') {
        dropTable(tableName: 'password')
    }
    changeSet(id: 'add-password-to-user-table', author: 'ralf.bettermann') {
        addColumn(tableName: 'user_') {
            column(name: 'password', type: 'varchar(255)')
        }
    }
    changeSet(id: 'add-role-to-user-table', author: 'ralf.bettermann') {
        addColumn(tableName: 'user_') {
            column(name: 'role', type: 'varchar(255)')
        }
    }
}