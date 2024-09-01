use aserriodatapro;

-- Script de creación y permisos de usuario

-- usuario 1, dueño del negocio
CREATE USER 'dueño'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'dueño'@'localhost' WITH GRANT OPTION;

-- usuario 2, secretaria 1 del negocio
CREATE USER 'secret1'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '1111';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret1'@'project-sbd.mysql.database.azure.com';

-- usuario 3, secretaria 2 del negocio
CREATE USER 'secret2'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret2'@'project-sbd.mysql.database.azure.com';

-- usuario 4, secretaria 3 del negocio
CREATE USER 'secret3'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret3'@'project-sbd.mysql.database.azure.com';

-- usuario 5, secretaria 4 del negocio
CREATE USER 'secret4'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret4'@'project-sbd.mysql.database.azure.com';

-- usuario 5, secretaria 4 del negocio
CREATE USER 'secret5'@'localhost' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret5'@'localhost';

