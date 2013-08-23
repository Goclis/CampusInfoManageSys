-- ---------------------------------
-- Via https://github.com/xindervella/VirtualCampus/blob/master/SQL/SQL%20Create%20Script.sql
-- ---------------------------------

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `xindervella_VirtualCampus` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `xindervella_VirtualCampus` ;

-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcClass`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcClass` (
  `cClassID` INT(9) NOT NULL COMMENT '班级ID' ,
  `cMajor` VARCHAR(9) NOT NULL COMMENT '专业' ,
  `cClass` INT(2) NOT NULL COMMENT '班级' ,
  `cNumberOfMember` INT(3) NOT NULL COMMENT '人数' ,
  PRIMARY KEY (`cClassID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcCourse`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcCourse` (
  `cID` INT(9) NOT NULL COMMENT '课程代码' ,
  `cName` VARCHAR(10) NOT NULL COMMENT '课程名' ,
  `cTeacherID` VARCHAR(10) NOT NULL COMMENT '任课老师' ,
  `cCredit` INT(11) NOT NULL COMMENT '学分' ,
  PRIMARY KEY (`cID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcStudent`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcStudent` (
  `sCard` INT(9) NOT NULL DEFAULT '0' COMMENT '一卡通号' ,
  `sID` VARCHAR(10) NULL DEFAULT NULL COMMENT '学号' ,
  `sName` VARCHAR(10) NULL DEFAULT NULL COMMENT '姓名' ,
  `sSex` ENUM('male','female') NULL DEFAULT 'male' COMMENT '性别' ,
  `sClassID` INT(9) NULL DEFAULT NULL COMMENT '班级ID' ,
  `sClass` INT(11) NOT NULL COMMENT '班级' ,
  `sGrade` INT(4) NOT NULL COMMENT '年级' ,
  PRIMARY KEY (`sCard`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcStudentCourse`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcStudentCourse` (
  `scID` INT(9) NOT NULL COMMENT '课程代码' ,
  `scStudentID` INT(9) NULL DEFAULT NULL COMMENT '学生学号' ,
  `scClassID` INT(9) NULL DEFAULT NULL COMMENT '班级ID' ,
  PRIMARY KEY (`scID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcTeacher`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcTeacher` (
  `tCard` INT(9) NOT NULL DEFAULT '0' COMMENT '一卡通号' ,
  `tID` VARCHAR(10) NULL DEFAULT NULL COMMENT '学号' ,
  `tName` VARCHAR(10) NULL DEFAULT NULL COMMENT '姓名' ,
  `tSex` ENUM('male','female') NULL DEFAULT 'male' COMMENT '性别' ,
  `sClassID` INT(11) NOT NULL COMMENT '班级' ,
  PRIMARY KEY (`tCard`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `xindervella_VirtualCampus`.`vcUser`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `xindervella_VirtualCampus`.`vcUser` (
  `uID` INT(9) NOT NULL DEFAULT '0' ,
  `uPwd` VARCHAR(8) NOT NULL ,
  `uRole` ENUM('student','teacher','other','admin') NOT NULL DEFAULT 'student' ,
  PRIMARY KEY (`uID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `xindervella_VirtualCampus` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;