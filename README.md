# arbeitsstunden-backend

Das Arbeitsstunden-Backend ist eine Java Spring API für die Arbeitsstundendatenbank.

# Deployment
Das Package im Repo kann ohne Einschränkungen im Produktivbetrieb des ASV eingesetzt werden.

## Config
Zum Deployen des Containers müssen einige Variablen für die Anbindung an die Datenbank und Keycloak gesetzt werden.

``
  arbeitsstunden-backend:
    environment:
      DB_HOSTNAME: arbeitsstunden-db
      DB_PORT: 5432
      DB_DATABASE: workinghours
      DB_USER: workinghours
      DB_PASS: my-secret-pw
      KEYCLOAK_REALM: asv
      KEYCLOAK_URL: '***'
      ddl-auto: none
      checkSSL: "False"
``

# Sicherheit
Jede Abfrage ans Backend wird auf einen Keycloak Token geprüft. 
Der Token wird dann auf korrektheit geprüft und es wird gecheckt ob der Token auch zum entsprechenden Nutzer gehört.

## ZU BEACHTEN
Da das Backend keine eigene Datenhaltung hat muss selbstverständlich eine Datenbank mit deployed werden. Dafür wird eine PostgreSQL benötigt.

