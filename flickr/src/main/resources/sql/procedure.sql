DELIMITER $$

USE `rss`$$

DROP PROCEDURE IF EXISTS `queryTime`$$
/*在当前时间进行相加减*/
CREATE PROCEDURE `queryTime`(IN TYPE CHAR(2),IN num INT,IN dtime DATETIME)
BEGIN
IF TYPE='H' THEN
SELECT DATE_ADD(dtime,INTERVAL num HOUR);
ELSEIF TYPE='M' THEN
SELECT DATE_ADD(dtime,INTERVAL num MINUTE );
ELSE
SELECT DATE_ADD(dtime,INTERVAL num SECOND );
END IF;
END$$

DELIMITER ;

------------------------------

DELIMITER $$

USE `rss`$$

DROP PROCEDURE IF EXISTS `queryinfo`$$

CREATE DEFINER=`root`@`%` PROCEDURE `queryinfo`(IN pageIndex INT,IN pageSize INT)
BEGIN
SELECT * FROM rss LIMIT pageIndex,pageSize;
END$$

DELIMITER ;