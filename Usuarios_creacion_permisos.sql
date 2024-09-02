use aserriodatapro;

-- Script de creación y permisos de usuario
-- usuario 1, dueño del negocio
CREATE USER 'dueño'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'dueño'@'project-sbd.mysql.database.azure.com' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'proyectoAserrio'@'127.0.0.1:3306' WITH GRANT OPTION;

-- usuario 2, secretaria 1 del negocio
CREATE USER 'secret1'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '1111';
GRANT ALL PRIVILEGES ON aserriodatapro.* TO 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret1'@'project-sbd.mysql.database.azure.com';

-- usuario 3, secretaria 2 del negocio
CREATE USER 'secret2'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret2'@'project-sbd.mysql.database.azure.com';

-- usuario 4, secretaria 3 del negocio
CREATE USER 'secret3'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarMaquinaria FROM 'secret3'@'project-sbd.mysql.database.azure.com';
GRANT EXECUTE ON PROCEDURE aserriodatapro.eliminarMaquinaria to 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret3'@'project-sbd.mysql.database.azure.com';

-- usuario 5, secretaria 4 del negocio
CREATE USER 'secret4'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret4'@'project-sbd.mysql.database.azure.com';

-- usuario 6, secretaria 5 del negocio
CREATE USER 'secret6'@'localhost' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret6'@'localhost';

