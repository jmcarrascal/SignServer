/*
SQLyog Community v10.2 
MySQL - 5.1.71-community : Database - signserver
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`signserver` /*!40100 DEFAULT CHARACTER SET latin1 */;

/*Data for the table `certificadoconfiable` */

LOCK TABLES `certificadoconfiable` WRITE;

insert  into `certificadoconfiable`(`idCertificadoConfiable`,`certificado`,`descrip`) values (1,'0��0���0\r	*�H��\r\00K10	UAR1)0\'U\n Infraestructura de Firma Digital10UAC Raíz0\r101025151056Z\r201022151056Z0�L10	UAR1)0\'U Ciudad Autónoma de Buenos Aires1301U*Subsecretaría de Tecnologías de Gestión1)0\'U Secretaría de Gestión Pública1907U0Oficina Nacional de Tecnologías de Información1*0(U\n!Jefatura de Gabinete de Ministros10UCUIT 30680604572100.U\'Autoridad Certificante de Firma Digital0�\"0\r	*�H��\r\0�\00�\n�\0�˵�L)K��[��ϧ�Z�zjJGo�O�g�����Ы�F]� @�Q�^�I��2>ը5Ƀ��ʉ��ڶf����������$�J/{V>�J�\\��;�AÝ�^�٭	�B���H�H��\np�zvS����E���L��w�K������\n���\"<+�*��(�3Ft\"xR�ҟ�?��g���E\Zɂ�x��Ԙ��ڈ��H�|;͖�\r�O%���T��}QBsG�~����;kH�ͳ�_\r\0ND�����lPJM��0.�j,��4\nSH1�tw�Rڥ�0��x�9�����;ʟ>�}���5����ܾ�﵈گn����I7�fb��͠e[���s�@p/�X8%�����*��f�����~�*����B��^�/��t��,�n?�[�#�����^qN�%\'�/w�h��WNE�I�拚s�_{�����Syg>��s6�=�]�<>#,�4\0���Q�M��g¯(�KJ��L�qAO�	��V`����!�#s��q\0���0��0U�0�\00U0U#0�]�<1��C[%`kG��t��d0U��.lpl3�\Zm$̛�?���0YUR0P0&�$�\"� http://acraiz.cdp1.gov.ar/ca.crl0&�$�\"� http://acraiz.cdp2.gov.ar/ca.crl0��U ��0��0��` \00��0(+http://acraiz.gov.ar/cps.pdf0r+0f\ZdLey 25.506 - Infraestructura de Firma Digital de la Republica Argentina, Autoridad Certificante Raiz07++0)0\'+0�http://acraiz.gov.ar/ca.crt0\r	*�H��\r\0�\0\Z�B������[b5�j��W��HzP��̍ch�;��ZA���=�]�a�f;ZDv�`T:�pә\Z���Zq>��yF�QU\Z�Ȍ����A������Z�r\\�W�s8�.�v ��k\Z��A>�;��؛����b���A1G���&��^1EJ|4� ��\\i\\]g;!��\0�67V>Q�H���׌(\Z_\\:ì������mF��j�6�x\\7]O\r�#.�8��3�y��� \\��ST�U�H��ږ_��\\@�4�}d�c	�l���,�\'��CY+�9�:�\rOI�jF�5.|��ޯ��A�[����̓��R#�����*ߗ�e�8J;r�t���\Z//_�P�DZ�`(,�ʭV8Uf�5r�:\\	��Q�����u������Q���\Zm?�Q>�3b�芣զ�3]L�@�&��\r��n�n���ѷ�(��+J.)�Ǯ����g��vk��}K���b%wU��|�%�v����>���fvx����0�l}����j��XFsS GD	8�!yG~u�p.иKD�X�','Certificado Intermedio ONTI');

UNLOCK TABLES;

/*Data for the table `firmante` */

LOCK TABLES `firmante` WRITE;

insert  into `firmante`(`idFirmante`,`locacion`,`passwordKeyStore`,`passwordTSA`,`razon`,`ultimoAcceso`,`urlTSA`,`usaTSA`,`userTSA`,`rutaKeyStore`) values (1,'Buenos Aires','123456','jmc1159','Ventanilla Electronica',NULL,'http://tsatest2.digistamp.com/tsa','','11295','c://ks3.p12');

UNLOCK TABLES;

/*Data for the table `mensaje` */

LOCK TABLES `mensaje` WRITE;

insert  into `mensaje`(`idMensaje`,`descrip`) values (0,'OK'),(1,'Formato de Documento PDF Invalido'),(2,'El documento no se encuentra firmado digitalmente'),(3,'No se pudo validar las firmas del documento'),(4,'Alguna de las firmas del documento no esta generarda a partir de un certificado digital vÃ¡lido'),(5,'No se recibió ningún documento'),(6,'Error al invocar la TSA'),(7,'Error Interno del sistema al realizar el sello del documento'),(8,'Error al procesar el certificado Root de confianza asociado al usuario'),(9,'Error al no encontrar el certificado del firmante'),(10,'Error en la autenticacion'),(34,'werwr'),(42,'Aver como Funca'),(6575,'asasdasda'),(121212,'Jolla'),(23324324,'ssdfsfsdsdfsd');

UNLOCK TABLES;

/*Data for the table `parametrizacion` */

LOCK TABLES `parametrizacion` WRITE;

insert  into `parametrizacion`(`idParametrizacion`,`descrip`,`valor`) values (10,'Usa proxy','false'),(11,'Url Proxy','10.3.8.1'),(12,'Puerto Proxy','80');

UNLOCK TABLES;

/*Data for the table `usuario` */

LOCK TABLES `usuario` WRITE;

insert  into `usuario`(`idUsuario`,`activo`,`password`,`ultimoAcceso`,`userName`) values (1,'','user',NULL,'user');

UNLOCK TABLES;

/*Data for the table `usuariocertificado` */

LOCK TABLES `usuariocertificado` WRITE;

insert  into `usuariocertificado`(`idUsuarioCertificado`,`idCertificadoConfiable`,`idUsuario`) values (1,1,1);

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
