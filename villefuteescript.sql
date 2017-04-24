-- MySQL Script generated by MySQL Workbench
-- Mon Apr 24 15:03:41 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`categorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`categorie` ;

CREATE TABLE IF NOT EXISTS `mydb`.`categorie` (
  `categorie_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`categorie_id`));


-- -----------------------------------------------------
-- Table `mydb`.`Utilisateur`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Utilisateur` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Utilisateur` (
  `identifiant` VARCHAR(30) NOT NULL,
  `mdp` VARCHAR(30) NOT NULL,
  `DateCompte` DATE NULL,
  `Ville` VARCHAR(30) NULL,
  PRIMARY KEY (`identifiant`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Reseau`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Reseau` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Reseau` (
  `idReseau` INT NOT NULL AUTO_INCREMENT,
  `Nom_Reseaux` VARCHAR(30) NULL,
  `Description` VARCHAR(255) NULL,
  `Ville` VARCHAR(30) NULL,
  `Politique_join` ENUM('all', 'ask', 'invit_only') NULL,
  `Utilisateur_chef` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idReseau`, `Utilisateur_chef`),
  INDEX `fk_Reseau_Utilisateur1_idx` (`Utilisateur_chef` ASC),
  CONSTRAINT `fk_Reseau_Utilisateur1`
    FOREIGN KEY (`Utilisateur_chef`)
    REFERENCES `mydb`.`Utilisateur` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Personne`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Personne` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Personne` (
  `Age` INT NOT NULL,
  `identifiant` VARCHAR(30) NOT NULL,
  `Politique_notifs` ENUM('never', 'only_reseaux', 'only_categorie', 'categorie_and_reseaux', 'ville') NULL,
  `Nom` VARCHAR(30) NULL,
  `Prenom` VARCHAR(30) NULL,
  PRIMARY KEY (`identifiant`),
  CONSTRAINT `idPersonne`
    FOREIGN KEY (`identifiant`)
    REFERENCES `mydb`.`Utilisateur` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Commercant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Commercant` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Commercant` (
  `identifiant` VARCHAR(30) NOT NULL,
  `nom_Magasin` VARCHAR(45) NULL,
  `Pays` VARCHAR(30) NULL,
  `Rue` VARCHAR(30) NULL,
  `Numero` INT NULL,
  `Info_supp` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`identifiant`),
  CONSTRAINT `identifiant`
    FOREIGN KEY (`identifiant`)
    REFERENCES `mydb`.`Utilisateur` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Utilisateur_has_Reseau`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Utilisateur_has_Reseau` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Utilisateur_has_Reseau` (
  `Utilisateur_identifiant` VARCHAR(30) NOT NULL,
  `Reseau_idReseau` INT NOT NULL,
  PRIMARY KEY (`Utilisateur_identifiant`, `Reseau_idReseau`),
  INDEX `fk_Utilisateur_has_Reseau_Reseau1_idx` (`Reseau_idReseau` ASC),
  INDEX `fk_Utilisateur_has_Reseau_Utilisateur1_idx` (`Utilisateur_identifiant` ASC),
  CONSTRAINT `fk_Utilisateur_has_Reseau_Utilisateur1`
    FOREIGN KEY (`Utilisateur_identifiant`)
    REFERENCES `mydb`.`Utilisateur` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utilisateur_has_Reseau_Reseau1`
    FOREIGN KEY (`Reseau_idReseau`)
    REFERENCES `mydb`.`Reseau` (`idReseau`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Personne_has_categorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Personne_has_categorie` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Personne_has_categorie` (
  `Personne_identifiant` VARCHAR(30) NOT NULL,
  `categorie_categorie_id` INT NOT NULL,
  PRIMARY KEY (`Personne_identifiant`, `categorie_categorie_id`),
  INDEX `fk_Personne_has_categorie_categorie1_idx` (`categorie_categorie_id` ASC),
  INDEX `fk_Personne_has_categorie_Personne1_idx` (`Personne_identifiant` ASC),
  CONSTRAINT `fk_Personne_has_categorie_Personne1`
    FOREIGN KEY (`Personne_identifiant`)
    REFERENCES `mydb`.`Personne` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Personne_has_categorie_categorie1`
    FOREIGN KEY (`categorie_categorie_id`)
    REFERENCES `mydb`.`categorie` (`categorie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Commercant_has_categorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Commercant_has_categorie` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Commercant_has_categorie` (
  `Commercant_identifiant` VARCHAR(30) NOT NULL,
  `categorie_categorie_id` INT NOT NULL,
  PRIMARY KEY (`Commercant_identifiant`, `categorie_categorie_id`),
  INDEX `fk_Commercant_has_categorie_categorie1_idx` (`categorie_categorie_id` ASC),
  INDEX `fk_Commercant_has_categorie_Commercant1_idx` (`Commercant_identifiant` ASC),
  CONSTRAINT `fk_Commercant_has_categorie_Commercant1`
    FOREIGN KEY (`Commercant_identifiant`)
    REFERENCES `mydb`.`Commercant` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Commercant_has_categorie_categorie1`
    FOREIGN KEY (`categorie_categorie_id`)
    REFERENCES `mydb`.`categorie` (`categorie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Notification` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Notification` (
  `Commercant_identifiant` VARCHAR(30) NOT NULL,
  `Date` DATE NULL,
  `Texte` VARCHAR(255) NULL,
  `multimedia` VARCHAR(45) NULL COMMENT 'Image/videos A voir le type\n',
  `DestinataireNotifs` ENUM('Ville', 'Corresp_Cate', 'Reseaux', 'Reseau') NULL,
  `Reseau_idReseau` INT NULL DEFAULT NULL,
  `categorie_categorie_id` INT NOT NULL,
  PRIMARY KEY (`Commercant_identifiant`, `categorie_categorie_id`),
  INDEX `fk_Personne_has_Commercant_Commercant1_idx` (`Commercant_identifiant` ASC),
  INDEX `fk_Notification_Reseau1_idx` (`Reseau_idReseau` ASC),
  INDEX `fk_Notification_categorie1_idx` (`categorie_categorie_id` ASC),
  CONSTRAINT `fk_Personne_has_Commercant_Commercant1`
    FOREIGN KEY (`Commercant_identifiant`)
    REFERENCES `mydb`.`Commercant` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_Reseau1`
    FOREIGN KEY (`Reseau_idReseau`)
    REFERENCES `mydb`.`Reseau` (`idReseau`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_categorie1`
    FOREIGN KEY (`categorie_categorie_id`)
    REFERENCES `mydb`.`categorie` (`categorie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Personne_has_Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Personne_has_Notification` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Personne_has_Notification` (
  `Personne_identifiant` VARCHAR(30) NOT NULL,
  `Notification_Commercant_identifiant` VARCHAR(30) NOT NULL,
  `Envoye` TINYINT NULL,
  PRIMARY KEY (`Personne_identifiant`, `Notification_Commercant_identifiant`),
  INDEX `fk_Personne_has_Notification_Notification1_idx` (`Notification_Commercant_identifiant` ASC),
  INDEX `fk_Personne_has_Notification_Personne1_idx` (`Personne_identifiant` ASC),
  CONSTRAINT `fk_Personne_has_Notification_Personne1`
    FOREIGN KEY (`Personne_identifiant`)
    REFERENCES `mydb`.`Personne` (`identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Personne_has_Notification_Notification1`
    FOREIGN KEY (`Notification_Commercant_identifiant`)
    REFERENCES `mydb`.`Notification` (`Commercant_identifiant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
