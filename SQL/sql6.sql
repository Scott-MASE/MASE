DROP PROCEDURE IF EXISTS  update_department;
DELIMITER //
CREATE PROCEDURE update_department
(
  IN   id_param    INT,
  IN   new_dept_param  VARCHAR(30)
)
BEGIN
  DECLARE sql_error TINYINT DEFAULT FALSE;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    SET sql_error = TRUE;
START TRANSACTION;
UPDATE details
  SET  department = new_dept_param
  WHERE id =  id_param;
  IF sql_error = FALSE THEN
    select "commit";
    COMMIT;
  ELSE
    select"rollback";
    rollback;
  END IF;
END //
delimiter ;
call update_department(2, 'abc');