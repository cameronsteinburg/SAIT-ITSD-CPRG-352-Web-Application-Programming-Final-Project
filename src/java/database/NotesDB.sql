DROP DATABASE if exists NotesDB;
CREATE DATABASE NotesDB;
USE NotesDB;

CREATE TABLE `company` (
  `CompanyID` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(25) NOT NULL,
  PRIMARY KEY (`CompanyID`)
);

CREATE TABLE `role` (
  `RoleID` int(11) NOT NULL,
  `RoleName` varchar(25) NOT NULL,
  PRIMARY KEY (`RoleID`)
);

CREATE TABLE `user`( 
    Username VARCHAR(10) NOT NULL,
    Password VARCHAR(10) NOT NULL,
    Email VARCHAR(30) NOT NULL,
    Active BIT NOT NULL,
    Firstname VARCHAR(50) NOT NULL,
    Lastname VARCHAR(50) NOT NULL,
    `Role` INT(11) NOT NULL,
	`Company` INT(11) NOT NULL,
    PRIMARY KEY (Username),
    KEY `FK_User_Role` (`Role`),
    CONSTRAINT `FK_User_Role` FOREIGN KEY (`Role`) REFERENCES `role` (`RoleID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    KEY `FK_Company_User` (`Company`),
    CONSTRAINT `FK_Role_Company` FOREIGN KEY (`Company`) REFERENCES `company` (`CompanyID`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `note` (
  `NoteID` int(11) NOT NULL AUTO_INCREMENT,
  `DateCreated` datetime NOT NULL,
  `Title` varchar(30) NOT NULL,
  `Contents` varchar(20000) CHARACTER SET utf8 NOT NULL,
  `Owner` varchar(10) NOT NULL,
  PRIMARY KEY (`NoteID`),
  KEY `FK_Note_User` (`Owner`),
  CONSTRAINT `FK_Note_User` FOREIGN KEY (`Owner`) REFERENCES `user` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ;

INSERT INTO `role` VALUES (1, 'system admin');
INSERT INTO `role` VALUES (2, 'regular user');
INSERT INTO `role` VALUES (3, 'company admin');

INSERT INTO `company` (CompanyName) VALUES ('SAIT');
INSERT INTO `company` (CompanyName) VALUES ('Star Trek');
INSERT INTO `company` (CompanyName) VALUES ('My Little Pony');

INSERT INTO `User` VALUES ('admin', 'password', 'cprg352+admin@gmail.com', 1, 'Bob', 'Bobberson', 1, 1);
INSERT INTO `User` VALUES ('admin2', 'password', 'cprg352+admin2@gmail.com', 0, 'Admin2', 'Admin2', 1, 1);

INSERT INTO `user` VALUES ('comp1', 'password', 'cprg352+comp1@gmail.com', 1, 'Jean-Luc', 'Picard', 3, 2);
INSERT INTO `user` VALUES ('user1', 'password', 'cprg352+user1@gmail.com', 1, 'William', 'Riker', 2, 2);
INSERT INTO `user` VALUES ('user2', 'password', 'cprg352+user2@gmail.com', 1, 'Geordi', 'La Forge', 2, 2);
INSERT INTO `user` VALUES ('user3', 'password', 'cprg352+user3@gmail.com', 0, 'Wesley', 'Crusher', 2, 2);

INSERT INTO `user` VALUES ('comp2', 'password', 'cprg352+comp2@gmail.com', 1, 'Princess', 'Celestia', 3, 3);
INSERT INTO `user` VALUES ('user4', 'password', 'cprg352+user4@gmail.com', 1, 'Twinkle', 'Sparkle', 2, 3);
INSERT INTO `user` VALUES ('user5', 'password', 'cprg352+user5@gmail.com', 1, 'Pinkie', 'Pie', 2, 3);
INSERT INTO `user` VALUES ('user6', 'password', 'cprg352+user6@gmail.com', 0, 'Rainbow', 'Dash', 2, 3);

INSERT INTO `note` (`DateCreated`, `Title`, `Contents`, `Owner`) VALUES (NOW(), 'Captains Log Stardate 41153.7', 'Our destination is planet Deneb IV beyond which lies the great, unexplored mass of the galaxy.', 'comp1');
INSERT INTO `note` (`DateCreated`, `Title`, `Contents`, `Owner`) VALUES (NOW(), 'Personal Log, Stardate 41153.7', 'The USS Hood has dropped me off at Farpoint Station.', 'user1');
INSERT INTO `note` (`DateCreated`, `Title`, `Contents`, `Owner`) VALUES (NOW(), 'My motivational quote', 'If I can\'t find a friendship problem, I\'ll make a friendship problem!', 'user4');
