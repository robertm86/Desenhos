CREATE DATABASE  IF NOT EXISTS `desenhos` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `desenhos`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: desenhos
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tipos_formas`
--

DROP TABLE IF EXISTS `tipos_formas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_formas` (
  `id_tipo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `qtd_pontos` tinyint(4) DEFAULT NULL,
  `qtd_escalares` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_formas`
--

LOCK TABLES `tipos_formas` WRITE;
/*!40000 ALTER TABLE `tipos_formas` DISABLE KEYS */;
INSERT INTO `tipos_formas` VALUES (1,'Ponto',1,0),(2,'Linha',2,0),(3,'Retangulo',2,0),(4,'Circulo',1,1),(5,'Triangulo',3,0);
/*!40000 ALTER TABLE `tipos_formas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `desenhos`
--

DROP TABLE IF EXISTS `desenhos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `desenhos` (
  `nome` char(45) NOT NULL,
  `criacao` datetime NOT NULL,
  `alteracao` datetime NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `id_desenho` int(11) NOT NULL AUTO_INCREMENT,
  `formato` int(1) DEFAULT NULL,
  PRIMARY KEY (`id_desenho`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `formas`
--

DROP TABLE IF EXISTS `formas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formas` (
  `id_formas` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) DEFAULT NULL,
  `texto` varchar(300) DEFAULT NULL,
  `binario` varbinary(1000) DEFAULT NULL,
  `desenho` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_formas`),
  KEY `desenho_idx` (`desenho`),
  KEY `tipo_idx` (`tipo`),
  CONSTRAINT `desenho` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `tipo` FOREIGN KEY (`tipo`) REFERENCES `tipos_formas` (`id_tipo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pontos`
--

DROP TABLE IF EXISTS `pontos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pontos` (
  `id_ponto` int(11) NOT NULL AUTO_INCREMENT,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `desenho` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_ponto`),
  KEY `desenho_idx` (`desenho`),
  CONSTRAINT `fk_ponto_desenho` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `linhas`
--

DROP TABLE IF EXISTS `linhas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `linhas` (
  `id_linha` int(11) NOT NULL AUTO_INCREMENT,
  `x_a` double NOT NULL,
  `y_a` double NOT NULL,
  `x_b` double NOT NULL,
  `y_b` double NOT NULL,
  `desenho` int(11) NOT NULL,
  PRIMARY KEY (`id_linha`),
  KEY `fk_desenho_idx` (`desenho`),
  CONSTRAINT `fk_linha_desenho` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `retangulos`
--

DROP TABLE IF EXISTS `retangulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `retangulos` (
  `id_retangulo` int(11) NOT NULL AUTO_INCREMENT,
  `x_a` double NOT NULL,
  `y_a` double NOT NULL,
  `x_b` double NOT NULL,
  `y_b` double NOT NULL,
  `desenho` int(11) NOT NULL,
  PRIMARY KEY (`id_retangulo`),
  KEY `fk_desenho_retangulo_idx` (`desenho`),
  CONSTRAINT `fk_desenho_retangulo` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `circulos`
--

DROP TABLE IF EXISTS `circulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `circulos` (
  `id_circulo` int(11) NOT NULL AUTO_INCREMENT,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `raio` double NOT NULL,
  `desenho` int(11) NOT NULL,
  PRIMARY KEY (`id_circulo`),
  KEY `fk_desenhos_circulo_idx` (`desenho`),
  CONSTRAINT `fk_circulo_desenho` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `triangulos`
--

DROP TABLE IF EXISTS `triangulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `triangulos` (
  `id_triangulo` int(11) NOT NULL AUTO_INCREMENT,
  `x_a` double NOT NULL,
  `y_a` double NOT NULL,
  `x_b` double NOT NULL,
  `y_b` double NOT NULL,
  `x_c` double NOT NULL,
  `y_c` double NOT NULL,
  `desenho` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_triangulo`),
  KEY `fk_desenho_triangulo_idx` (`desenho`),
  CONSTRAINT `fk_desenho_triangulo` FOREIGN KEY (`desenho`) REFERENCES `desenhos` (`id_desenho`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-03 12:57:48
