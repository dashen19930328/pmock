/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.21-log : Database - pmockserver
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pmockserver` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `pmockserver`;

/*Table structure for table `caseconfig` */

DROP TABLE IF EXISTS `caseconfig`;

CREATE TABLE `caseconfig` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '通过类名作为case名字',
  `caseText` text COLLATE utf8_bin NOT NULL COMMENT 'case的内容',
  `scriptType` varchar(20) COLLATE utf8_bin DEFAULT 'groovy' COMMENT 'case脚本语言类型',
  `version` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '版本',
  `userId` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '配置人id',
  `userName` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '配置人名字',
  `systemCode` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '系统编码',
  `systemName` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '系统名字',
  `status` int(5) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_className` (`className`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='测试脚本配置';

/*Data for the table `caseconfig` */

insert  into `caseconfig`(`id`,`className`,`caseText`,`scriptType`,`version`,`userId`,`userName`,`systemCode`,`systemName`,`status`,`ext1`,`ext2`,`updateDate`,`createdDate`) values (1,'PersonBusinessDao','import groovy.json.JsonSlurper\r\n\r\n//mock condition\r\ndef queryPersonMap(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'aop\':{\'name\':\'map aop\'}}\"\r\n}\r\n\r\ndef queryShopping(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'person\':{\'name\':\'shopping\'}}\"\r\n}\r\n\r\ndef queryShoppingResult(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'desc\':\'查询血拼\',\'flag\':true,\'value\':{\'desc\':\'血拼的人\',\'person\':{\'name\':\'泛型result\'}}}\"\r\n}\r\n\r\ndef queryAge(paraObj) {\r\n    if (paraObj.name == \'queryAge\')\r\n        return \"{\'name\':\'test123\',\'age\':789}\"\r\n}\r\n\r\ndef queryPersonList(personQuery) {\r\n    if (personQuery==null)\r\n        return \"[{\'name\':\'Jim\',\'age\':15,\'sex\':1},{\'name\':\'Tom\',\'age\':5,\'sex\':1},{\'name\':\'Mike\',\'age\':12,\'sex\':1},{\'name\':\'Bruce\',\'age\':28,\'sex\':1},{\'name\':\'Lily\',\'age\':13,\'sex\':1},{\'name\':\'Hair\',\'age\':15,\'sex\':1}]\";\r\n    else if (  personQuery.name == \'test\')\r\n        return \"[{\'name\':\'list\'}]\";\r\n    else   if (personQuery.sex == 1)\r\n        return \"[{\'name\':\'Jim\',\'age\':15,\'sex\':1},\" +\r\n                \"{\'name\':\'Tom\',\'age\':5,\'sex\':1},\" +\r\n                \"{\'name\':\'Mike\',\'age\':12,\'sex\':1},\" +\r\n                \"{\'name\':\'Bruce\',\'age\':28,\'sex\':1},\" +\r\n                \"{\'name\':\'Lily\',\'age\':13,\'sex\':1},\" +\r\n                \"{\'name\':\'Hair\',\'age\':15,\'sex\':1}]\";\r\n}\r\n//纯粹为了做复杂的输入条件测试\r\ndef queryComplexShopping(shoppingResult, query) {\r\n    if (shoppingResult!=null && query!=null){\r\n        if(query.sex==1){\r\n            if (shoppingResult.value.desc==\'test\'){\r\n                if (shoppingResult.value.person.age>1){\r\n                    return \"{\'name\':\'test123\',\'age\':789}\"\r\n                }\r\n            }\r\n        }\r\n    }\r\n}\r\ndef queryString(string){\r\n    println string;\r\n    return \"queryString\";\r\n}\r\n\r\n\r\n\r\n\r\ndef queryCount(count){\r\n    println count;\r\n    return count+1;\r\n}\r\n\r\n\r\ndef queryCountInter(count){\r\n    println count;\r\n    return \"9\";\r\n}\r\npublic static void main(def args){\r\n    def jsonSlurper = new JsonSlurper();\r\n    def map = jsonSlurper.parseText(\'{\"name\":\"queryAge\"}\');\r\n    println queryAge(map) ;\r\n    println queryString(\'ss\');\r\n}','groovy','',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-19 12:47:35','2018-06-18 21:03:26'),(2,'JsTest','function execute(s1, s2){\r\n  return s1 + \"-\"+s2;\r\n}','javascript','',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-19 14:41:53','2018-06-18 21:05:12'),(3,'PythonTest','def printme( str ):\r\n   \"python==\"\r\n   print str\r\n   return \"net python return\"','python','',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-19 14:42:33','2018-06-18 21:06:07'),(4,'RubyTest','def test(a1 , a2 )\r\n   puts \"ruby  #{a1}\"\r\n   puts \"ruby #{a2}\"\r\n   return a1+\"_\"+a2\r\nend','ruby','',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-19 14:43:22','2018-06-18 21:06:54'),(5,'PersonBusinessDaoImpl','import groovy.json.JsonSlurper\r\n\r\n//mock condition\r\ndef queryPersonMap(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'aop\':{\'name\':\'map aop\'}}\"\r\n}\r\n\r\ndef queryShopping(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'person\':{\'name\':\'shopping\'}}\"\r\n}\r\n\r\ndef queryShoppingResult(paraObj) {\r\n    if (paraObj.name == \'test\')\r\n        return \"{\'desc\':\'查询血拼\',\'flag\':true,\'value\':{\'desc\':\'血拼的人\',\'person\':{\'name\':\'泛型result\'}}}\"\r\n}\r\n\r\n\r\ndef queryPersonList(personQuery) {\r\n    if (personQuery==null)\r\n        return \"[{\'name\':\'Jim\',\'age\':15,\'sex\':1},{\'name\':\'Tom\',\'age\':5,\'sex\':1},{\'name\':\'Mike\',\'age\':12,\'sex\':1},{\'name\':\'Bruce\',\'age\':28,\'sex\':1},{\'name\':\'Lily\',\'age\':13,\'sex\':1},{\'name\':\'Hair\',\'age\':15,\'sex\':1}]\";\r\n    else if (  personQuery.name == \'test\')\r\n        return \"[{\'name\':\'list\'}]\";\r\n    else   if (personQuery.sex == 1)\r\n        return \"[{\'name\':\'Jim\',\'age\':15,\'sex\':1},{\'name\':\'Tom\',\'age\':5,\'sex\':1},{\'name\':\'Mike\',\'age\':12,\'sex\':1},{\'name\':\'Bruce\',\'age\':28,\'sex\':1},{\'name\':\'Lily\',\'age\':13,\'sex\':1},{\'name\':\'Hair\',\'age\':15,\'sex\':1}]\";\r\n}\r\n//纯粹为了做复杂的输入条件测试\r\ndef queryComplexShopping(shoppingResult, query) {\r\n    if (shoppingResult!=null && query!=null){\r\n        if(query.sex==1){\r\n            if (shoppingResult.value.desc==\'test\'){\r\n                if (shoppingResult.value.person.age>1){\r\n                    return \"{\'name\':\'test123\',\'age\':789}\"\r\n                }\r\n            }\r\n        }\r\n    }\r\n}\r\n\r\ndef queryString(string){\r\n    println string;\r\n    return \"queryString\";\r\n}\r\n\r\n\r\ndef queryCount(count){\r\n    println count;\r\n    return 9+count;\r\n}\r\n\r\ndef queryCountInter(count){\r\n    println count;\r\n    return \"9\";\r\n}\r\n\r\ndef queryAge(paraObj) {\r\n    if (paraObj.name == \'queryAge\')\r\n        return \"{\'name\':\'test123\',\'age\':789}\"\r\n}\r\n\r\nstatic   main(def args){\r\n\r\n}','groovy','',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-21 10:06:37','2018-06-18 23:38:53'),(6,'SpringTest','import com.jd.jr.pmock.server.dao.CaseConfigMapper\r\nimport org.springframework.web.context.ContextLoader\r\nimport org.springframework.web.context.WebApplicationContext\r\nimport org.apache.commons.dbcp.BasicDataSource;\r\n  \r\n    def getBean(){\r\n        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();\r\n        BasicDataSource basicDataSource = (BasicDataSource) applicationContext.getBean(\"DataSource\");\r\n        print basicDataSource;\r\n        basicDataSource.getcon\r\n        return basicDataSource;\r\n    }\r\n\r\n    static   main(def args){\r\n\r\n    }\r\n ','groovy','baina',NULL,NULL,'baina',NULL,NULL,NULL,NULL,'2018-06-22 18:11:15','2018-06-22 17:06:33');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
