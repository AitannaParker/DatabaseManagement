
CREATE SCHEMA lab1


CREATE TABLE lab1.Keepers(keeperID int PRIMARY KEY, name varchar (30), hireDate date, keeperLevel char(1), keeperSalary numeric(7,2))
CREATE TABLE lab1.Cages(cageID int PRIMARY KEY, cageSector char(1), keeperID int REFERENCES Keepers(keeperID))
CREATE TABLE lab1.Species(speciesID int PRIMARY KEY, speciesName varchar(30), genus varchar(40), animalCount int)
CREATE TABLE lab1.Animals(animalID int PRIMARY KEY, name varchar(30), speciesID int REFERENCES Species(speciesID), animalAge int, cageID int REFERENCES Cages(cageID))
CREATE TABLE lab1.Members(memberID int PRIMARY KEY, name varchar(30), address varchar (100), memberStatus char(1), joinDate date, expirationDate date)
CREATE TABLE lab1.CageVisits(memberID int , cageID int, visitDate date, PRIMARY KEY (memberID, cageID, visitDate), likedVisit boolean)

