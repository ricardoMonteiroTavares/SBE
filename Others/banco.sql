DROP TABLE banco.card;
DROP TABLE banco.travel;
DROP TABLE banco.execute_travel;
DROP TABLE banco.boarding;

CREATE TABLE banco.card (
  code INT(15) NOT NULL AUTO_INCREMENT,
  name VARCHAR(40) NOT NULL,
  balance DECIMAL(8, 2) NOT NULL,
  version INT DEFAULT 0,
  PRIMARY KEY (code)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;

CREATE TABLE banco.travel (
  id INT NOT NULL AUTO_INCREMENT,
  line VARCHAR(10) NOT NULL,
  origin VARCHAR(40) NOT NULL,
  destination VARCHAR(40) NOT NULL,
  ticketValue DECIMAL(8, 2) NOT NULL,
  version INT DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT TRAVEL_LINE_UN UNIQUE(line)
)

ENGINE = INNODB
CHARACTER SET utf8mb4;

CREATE TABLE banco.execute_travel (
	id INT NOT NULL AUTO_INCREMENT,
	id_travel INT NOT NULL,
	company VARCHAR(50) NOT NULL,
	vehicleCode VARCHAR(20) NOT NULL,
	date DATE NOT NULL, 
	time TIME NOT NULL,
	direction VARCHAR(40) NOT NULL,
	ticketValue DECIMAL(8, 2) NOT NULL,
	
	version INT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id_travel) REFERENCES banco.travel(id)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;

CREATE TABLE banco.boarding (
	id INT NOT NULL AUTO_INCREMENT,
	id_executeTravel INT NOT NULL,
    id_card INT NOT NULL,
	date DATE NOT NULL, 
	time TIME NOT NULL,
	
	version INT DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT BOARDING_EXTRAVEL_FK FOREIGN KEY (id_executeTravel) REFERENCES banco.execute_travel(id),
    FOREIGN KEY (id_card) REFERENCES banco.card(code)
)
ENGINE = INNODB
CHARACTER SET utf8mb4;
