-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: rioZoo
-- ------------------------------------------------------
-- Server version	5.6.35

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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` int(11) DEFAULT NULL,
  `matricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `matricula_UNIQUE` (`matricula`),
  KEY `fk_administrador_endereco_idx` (`endereco`),
  CONSTRAINT `fk_administrador_endereco` FOREIGN KEY (`endereco`) REFERENCES `endereco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `especie` varchar(45) NOT NULL,
  `dataNascimento` date NOT NULL,
  `origem` varchar(45) NOT NULL,
  `peso` float NOT NULL,
  `tratadorResponsavel` int(11) NOT NULL,
  `rotina` int(11) NOT NULL,
  `codigo` varchar(45) NOT NULL,
  `sexo` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk04_idx` (`rotina`),
  KEY `fk_animal_tratador_idx` (`tratadorResponsavel`),
  CONSTRAINT `fk04` FOREIGN KEY (`rotina`) REFERENCES `rotina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_tratador` FOREIGN KEY (`tratadorResponsavel`) REFERENCES `tratador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `animal` WRITE;
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `boletimDiario`
--

DROP TABLE IF EXISTS `boletimDiario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boletimDiario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `parecer` varchar(45) NOT NULL,
  `tratadorRespons` int(11) NOT NULL,
  `observacoes` varchar(200) DEFAULT NULL,
  `animal` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cod_UNIQUE` (`id`),
  KEY `fk06_idx` (`animal`),
  KEY `fk_boletim_tratador_idx` (`tratadorRespons`),
  CONSTRAINT `fk06` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_boletim_tratador` FOREIGN KEY (`tratadorRespons`) REFERENCES `tratador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boletimDiario`
--

LOCK TABLES `boletimDiario` WRITE;
/*!40000 ALTER TABLE `boletimDiario` DISABLE KEYS */;
/*!40000 ALTER TABLE `boletimDiario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consulta` (
  `dataConsulta` date NOT NULL,
  `animal` int(11) NOT NULL,
  `veterinario` int(11) NOT NULL,
  `dataAgendamento` date NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk01_idx` (`animal`),
  KEY `fk10_idx` (`veterinario`),
  CONSTRAINT `fk10` FOREIGN KEY (`veterinario`) REFERENCES `veterinario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk11` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consulta`
--

LOCK TABLES `consulta` WRITE;
/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logradouro` varchar(200) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `uf` varchar(45) NOT NULL,
  `cep` varchar(45) NOT NULL,
  `telefone` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipe`
--

DROP TABLE IF EXISTS `equipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipe`
--

LOCK TABLES `equipe` WRITE;
/*!40000 ALTER TABLE `equipe` DISABLE KEYS */;
INSERT INTO `equipe` VALUES (4,'Equipe 1'),(7,'Equipe 3'),(9,'Equipe 4');
/*!40000 ALTER TABLE `equipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicamento`
--

DROP TABLE IF EXISTS `medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `codigo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamento`
--

LOCK TABLES `medicamento` WRITE;
/*!40000 ALTER TABLE `medicamento` DISABLE KEYS */;
INSERT INTO `medicamento` VALUES (2,'medi 1',7456,'med 001'),(5,'medicamento 8',653,'med0008'),(6,'medicamento ',34,'med009');
/*!40000 ALTER TABLE `medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registroClinico`
--

DROP TABLE IF EXISTS `registroClinico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registroClinico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `diagnostico` varchar(45) NOT NULL,
  `observacoes` varchar(100) DEFAULT NULL,
  `vacina` int(11) DEFAULT NULL,
  `veterinario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`id`),
  KEY `fk12_idx` (`animal`),
  KEY `fk13_idx` (`vacina`),
  KEY `fk14_idx` (`veterinario`),
  CONSTRAINT `fk12` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk13` FOREIGN KEY (`vacina`) REFERENCES `vacina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk14` FOREIGN KEY (`veterinario`) REFERENCES `veterinario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registroClinico`
--

LOCK TABLES `registroClinico` WRITE;
/*!40000 ALTER TABLE `registroClinico` DISABLE KEYS */;
/*!40000 ALTER TABLE `registroClinico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rotina`
--

DROP TABLE IF EXISTS `rotina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rotina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataValidade` date NOT NULL,
  `tarefa1` varchar(200) DEFAULT NULL,
  `codigo` varchar(45) NOT NULL,
  `tarefa2` varchar(200) DEFAULT NULL,
  `tarefa3` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rotina`
--

LOCK TABLES `rotina` WRITE;
/*!40000 ALTER TABLE `rotina` DISABLE KEYS */;
INSERT INTO `rotina` VALUES (1,'2017-08-11','DKSJFHJSDFJK','RT01','KJSBVJSFBJKVB','KJNBKJDFNKJBDN'),(2,'2017-12-11','DSJKGKSJDKJ','RT02','KJSBKJGBKJ','KJBSFKJBGK');
/*!40000 ALTER TABLE `rotina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratador`
--

DROP TABLE IF EXISTS `tratador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tratador` (
  `equipe` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `endereco` int(11) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `matricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `matricula_UNIQUE` (`matricula`),
  KEY `fk02_idx` (`id`),
  KEY `fk08_idx` (`equipe`),
  KEY `fk_tratador_endereco_idx` (`endereco`),
  CONSTRAINT `fk08` FOREIGN KEY (`equipe`) REFERENCES `equipe` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tratador_endereco` FOREIGN KEY (`endereco`) REFERENCES `endereco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratador`
--

LOCK TABLES `tratador` WRITE;
/*!40000 ALTER TABLE `tratador` DISABLE KEYS */;
/*!40000 ALTER TABLE `tratador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratadorAnimal`
--

DROP TABLE IF EXISTS `tratadorAnimal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tratadorAnimal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `animal` int(11) NOT NULL,
  `tratador` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk14_idx` (`animal`),
  KEY `fk_tratador_animal_tratador_idx` (`tratador`),
  CONSTRAINT `fk_animal_responsavel` FOREIGN KEY (`animal`) REFERENCES `animal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tratador_animal_tratador` FOREIGN KEY (`tratador`) REFERENCES `tratador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratadorAnimal`
--

LOCK TABLES `tratadorAnimal` WRITE;
/*!40000 ALTER TABLE `tratadorAnimal` DISABLE KEYS */;
/*!40000 ALTER TABLE `tratadorAnimal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacina`
--

DROP TABLE IF EXISTS `vacina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vacina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `codigo` varchar(45) NOT NULL,
  `quantidade` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacina`
--

LOCK TABLES `vacina` WRITE;
/*!40000 ALTER TABLE `vacina` DISABLE KEYS */;
INSERT INTO `vacina` VALUES (1,'Vacina 1','VAC001',10),(3,'vacina 1','vac001',5),(6,'vacina 1','vac001',2),(7,'vacina 1','vac001',2),(8,'teste','vac004',4);
/*!40000 ALTER TABLE `vacina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinario`
--

DROP TABLE IF EXISTS `veterinario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veterinario` (
  `crmv` int(11) NOT NULL,
  `dataEmissao` date NOT NULL,
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` int(11) DEFAULT NULL,
  `matricula` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `crmv_UNIQUE` (`crmv`),
  UNIQUE KEY `matricula_UNIQUE` (`matricula`),
  KEY `fk03_idx` (`id`),
  KEY `fk_veterinario_endereco_idx` (`endereco`),
  CONSTRAINT `fk_veterinario_endereco` FOREIGN KEY (`endereco`) REFERENCES `endereco` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinario`
--

LOCK TABLES `veterinario` WRITE;
/*!40000 ALTER TABLE `veterinario` DISABLE KEYS */;
/*!40000 ALTER TABLE `veterinario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-28 23:07:44
