# Requirements

- Java 24+
- Maven 3.9+
- MySQL 8+

## Check installations
### Windows / MACOS / LINUX:
```
java -version  
mvn -version  
mysql --version  
```


# Database Setup

## 1. Start your MySQL server.

## 2. Create the schema `applify` with two tables:
- appliedJobsList
- savedJobsList


```
-- Schema applify
CREATE SCHEMA IF NOT EXISTS `applify` DEFAULT CHARACTER SET utf8 ;
USE `applify` ;
```

```
-- Table `applify`.`appliedJobsList`
CREATE TABLE IF NOT EXISTS `applify`.`appliedJobsList` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `postingName` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NOT NULL,
  `postingLink` VARCHAR(500) NULL,
  `applicationDate` DATE NOT NULL,
  `applicationStatus` VARCHAR(45) NOT NULL,
  `nextInterviewDate` DATE NOT NULL,
  `nextInterviewLink` VARCHAR(500) NULL,
  `nextInterviewPlace` VARCHAR(500) NULL,
  `contactPersonFullName` VARCHAR(100) NULL,
  `notes` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`));
```

```
-- Table `applify`.`savedJobsList`
CREATE TABLE IF NOT EXISTS `applify`.`savedJobsList` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `postingName` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NOT NULL,
  `postingLink` VARCHAR(500) NULL,
  `contactPersonFullName` VARCHAR(100) NULL,
  `notes` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`));
```

## 3. Update the database configuration in the project.

Update the database configuration in the following classes:
1) src/main/java/database/appliedJobs/DatabaseHandler.java
2) src/main/java/database/savedJobs/DatabaseHandler.java

As an example:
```
String url = "jdbc:mysql://localhost:3306/applify";
```

## 4. Set the environment variables

### Windows
Command Prompt
```
set DB_USER=applify_user
set DB_PASSWORD=yourpassword
```

Power Shell
```
setx DB_USER "applify_user"
setx DB_PASSWORD "yourpassword"
```

### MACOS/LINUX:
Open terminal and run:
```
export DB_USER=applify_user
export DB_PASSWORD=yourpassword
```


# Run the application

Clone or download the repository and run:
```
mvn clean javafx:run
```
