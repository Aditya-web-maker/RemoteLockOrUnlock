DROP TABLE IF EXISTS RLUL_DATA;
CREATE TABLE RLUL_DATA
(
    vin VARCHAR(17)  NOT NULL PRIMARY KEY,
    command varchar (30) NOT NULL,
    status varchar (30) NOT NULL,
    timestamp varchar (30) NOT NULL
);