-- This is the SQL-code for creating the SQL-database
-- updated on 20260309 reduced and with the following columns `id`, `postingName`, `company`, `postingLink`, `cotactPersonFullName`, `notes`
-- important: schema applify enhanced by table `savedJobsList`

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';



-- -----------------------------------------------------
-- Table `applify`.`savedJobsList`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applify`.`savedJobsList` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `postingName` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NOT NULL,
  `postingLink` VARCHAR(500) NULL,
  `contactPersonFullName` VARCHAR(100) NULL,
  `notes` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
