/*
Created: 27.02.2023
Modified: 27.02.2023
Model: toyshop
Database: SQLite 3.7
*/

-- Create tables section -------------------------------------------------

-- Table toy

CREATE TABLE toy
(
  toyId INTEGER NOT NULL
        CONSTRAINT PK_toy PRIMARY KEY AUTOINCREMENT,
  toyName varchar(30) NOT NULL,
  toyCnt INTEGER NOT NULL,
  toyRate decimal NOT NULL
)
;

-- Table prize

CREATE TABLE prize
(
  prizeId INTEGER NOT NULL,
  prizeName varchar(30) NOT NULL,
  prizeGiven boolean,
  CONSTRAINT PK_prize PRIMARY KEY (prizeId)
)
;

