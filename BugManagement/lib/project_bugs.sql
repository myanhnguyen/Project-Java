-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bugs`
--

DROP TABLE IF EXISTS `bugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bugs` (
  `ID` varchar(45) DEFAULT NULL,
  `project` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `do_nghiem_trong` varchar(45) DEFAULT NULL,
  `do_uu_tien` varchar(45) DEFAULT NULL,
  `phan_loai` varchar(45) DEFAULT NULL,
  `dev` varchar(45) DEFAULT NULL,
  `due_date` varchar(45) DEFAULT NULL,
  `person` varchar(45) DEFAULT NULL,
  `start_date` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bugs`
--

LOCK TABLES `bugs` WRITE;
/*!40000 ALTER TABLE `bugs` DISABLE KEYS */;
INSERT INTO `bugs` VALUES ('a','a','','New','Nghiêm trọng','High','Deffect','','08/12/2022','Manh','06/12/2022'),('b','b','','Open','Nghiêm trọng','High','Bug','Thủy','08/12/2022','Manh','06/12/2022'),('c','c','','Rejected','Cao','Medium','Deffect','Thủy','14/12/2022','Manh','06/12/2022'),('d','d','','Duplicate','Nghiêm trọng','High','Bug','Thủy','15/12/2022','Manh','06/12/2022'),('e','e','','Deferred','Nghiêm trọng','High','Bug','Thủy','15/12/2022','Manh','06/12/2022'),('g','g','','Assigned','Nghiêm trọng','High','Bug','Thủy','15/12/2022','Manh','06/12/2022'),('h','h','','Fix','Nghiêm trọng','High','Bug','Thủy','15/12/2022','Manh','06/12/2022'),('f','f','','Re-testing','Nghiêm trọng','High','Bug','Manh','15/12/2022','Manh','06/12/2022'),('j','j','','Closed','Nghiêm trọng','High','Bug','Manh','15/12/2022','Manh','06/12/2022'),('k','k','','Re-opened','Nghiêm trọng','High','Bug','Manh','15/12/2022','Manh','06/12/2022');
/*!40000 ALTER TABLE `bugs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-08 11:01:03
