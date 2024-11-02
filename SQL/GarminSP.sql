USE garmin;

DELIMITER //

CREATE PROCEDURE ConvertColumnDt(
    IN col_name VARCHAR(64),
    IN new_data_type VARCHAR(64)
)
BEGIN
    SET @sql = CONCAT('ALTER TABLE ', 'garminwalkingdatacleaned', ' MODIFY COLUMN `', col_name, '` ', new_data_type, ';');
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
end //

DELIMITER ;