CREATE TABLE INSTALACIONES(
codInstalacion VARCHAR(25)  PRIMARY KEY NOT NULL,
nombreInstalacion VARCHAR(40) NOT NULL,
disponible INT (1) DEFAULT  '1');
CREATE TABLE USUARIOS(
codUsuario VARCHAR(25) PRIMARY KEY NOT NULL,
password VARCHAR(15) NOT NULL,
nombreUsuario VARCHAR (20) NOT NULL,
apellidoUsuario VARCHAR (20) NOT NULL,
telefonoUsuario VARCHAR (20) NOT NULL,
DNIUsuario VARCHAR (10) NOT NULL,
emailUsuario VARCHAR (40) NOT NULL
);
CREATE TABLE SOCIOS(
codSocio VARCHAR(15) NOT NULL,
codUsuario VARCHAR(25) NOT NULL,
PRIMARY KEY (codSocio, codUsuario),
FOREIGN KEY (codUsuario) REFERENCES USUARIOS (codUsuario)
);
CREATE TABLE ADMINS(
codAdmin VARCHAR(15) NOT NULL,
codUsuario VARCHAR(25) NOT NULL,
PRIMARY KEY (codAdmin, codUsuario),
FOREIGN KEY (codUsuario) REFERENCES USUARIOS (codUsuario)
);
CREATE TABLE RESERVAS(
codInstalacion VARCHAR(15) NOT NULL,
codUsuario VARCHAR(15) NOT NULL,
fecha VARCHAR(10) NOT NULL,
horaInicio VARCHAR(5) NOT NULL,
horaFinal VARCHAR(5) NOT NULL,
instalacionOcupada INT(1) DEFAULT '0', 
recogidaLlaves VARCHAR(5),
entregaLlaves VARCHAR(5),
PRIMARY KEY (codUsuario, codInstalacion),
FOREIGN KEY (codUsuario) REFERENCES USUARIOS (codUsuario),
FOREIGN KEY (codInstalacion) REFERENCES INSTALACIONES (codInstalacion)
);
INSERT INTO INSTALACIONES VALUES ('piscina-01','Piscina Olimpica',1);
INSERT INTO INSTALACIONES VALUES ('piscina-02','Piscina Bebes',1);
INSERT INTO INSTALACIONES VALUES ('piscina-03','Piscina Waterpolo',0);
INSERT INTO INSTALACIONES VALUES ('futbol-01','Pista Futbol Sala',1);
INSERT INTO INSTALACIONES VALUES ('futbol-02','Pista Futbol 7',1);
INSERT INTO INSTALACIONES VALUES ('futbol-03','Pista Futbol 11',1);
INSERT INTO INSTALACIONES VALUES ('baloncesto-01','Pista Baloncesto',1);
INSERT INTO INSTALACIONES VALUES ('velodromo-01','Velodromo',1);
INSERT INTO INSTALACIONES VALUES ('tenis-01','Pista Tenis Tierra',1);
INSERT INTO INSTALACIONES VALUES ('tenis-02','Pista Tenis Cesped',0);
INSERT INTO USUARIOS VALUES ('U-001','00','AdminsBD','...','...','...','...');
INSERT INTO USUARIOS VALUES ('U-002','12345','Rosa','Valdes','985124578','71720869-T','valdesRosa@gmail.com');
INSERT INTO USUARIOS VALUES ('U-003','56789','Ivan','Casielles','984678514','71005264-U','ivanCasielles@gmail.com');
INSERT INTO USUARIOS VALUES ('U-004','14725','Pelayo','Diaz','649853164','09846514-D','pelayoDiaz@gmail.com');
INSERT INTO USUARIOS VALUES ('U-005','58369','Jose','Garcia','684619848','71730041-J','joseGarcia@gmail.com');
INSERT INTO USUARIOS VALUES ('U-006','11','UserBD','...','...','...','...');
INSERT INTO USUARIOS VALUES ('U-007','abcd','Alberto','Mendez','781364521','71748319-W','elMasChulo@hotmail.es');
INSERT INTO USUARIOS VALUES ('U-008','1a2b3c','Olga','Gutierrez','68473698','74110869-F','asturias-Olga@yahoo.com');
INSERT INTO SOCIOS VALUES ('S-001','U-006');
INSERT INTO SOCIOS VALUES ('S-002','U-007');
INSERT INTO SOCIOS VALUES ('S-003','U-008');
INSERT INTO ADMINS VALUES ('A-001','U-001');
INSERT INTO ADMINS VALUES ('A-002','U-002');
INSERT INTO ADMINS VALUES ('A-003','U-003');
INSERT INTO ADMINS VALUES ('A-004','U-004');
INSERT INTO ADMINS VALUES ('A-005','U-005');
INSERT INTO RESERVAS (codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada) VALUES ('piscina-01','U-001','15/10/2017','10:00','11:00',0);
INSERT INTO RESERVAS (codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada) VALUES ('futbol--01','U-006','17/10/2017','12:00','14:00',0);
INSERT INTO RESERVAS (codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada) VALUES ('piscina-02','U-006','21/10/2017','14:00','15:00',0);
INSERT INTO RESERVAS (codInstalacion,codUsuario,fecha,horaInicio,horaFinal,instalacionOcupada) VALUES ('velodromo-01','U-006','21/10/2017','15:00','17:00',0);
INSERT INTO RESERVAS VALUES ('piscina-01','U-007','07/10/2017','18:00','19:00',1,'17:58','19:06');
INSERT INTO RESERVAS VALUES ('piscina-01','U-008','04/09/2017','09:00','11:00',1,'09:17','10:56');