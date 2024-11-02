CREATE DATABASE IF NOT EXISTS Garmin;
USE Garmin;
select * from garminwalkingdatacleaned;


CALL ConvertColumnDt('id', 'INT');
CALL ConvertColumnDt('Distance', 'Double');
CALL ConvertColumnDt('Calories', 'INT');
CALL ConvertColumnDt('Avg HR', 'INT');
CALL ConvertColumnDt('Max HR', 'INT');
CALL ConvertColumnDt('Avg Cadence', 'INT');
CALL ConvertColumnDt('Max Cadence', 'INT');
CALL ConvertColumnDt('Total Ascent', 'INT');
CALL ConvertColumnDt('Total Descent', 'INT');
CALL ConvertColumnDt('Avg Stride Length', 'Double');
CALL ConvertColumnDt('Min Elevation', 'INT');
CALL ConvertColumnDt('Max Elevation', 'INT');

SHOW COLUMNS FROM garminwalkingdatacleaned;









