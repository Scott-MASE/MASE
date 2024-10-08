-- select count(pat_id) as "Number of patients" from patients;
-- select * from patients where town = "Athlone";
-- select drug_name, cost from drugs where cost = 2.95;
-- select drug_name, cost from drugs where cost >= 3.50;
-- select firstname, dateofbirth from patients order by dateofbirth desc limit 1;
-- select firstname, dateofbirth from patients order by dateofbirth asc limit 1;
-- select count(*) from doctors;
-- select firstname, lastname from doctors order by lastname desc;
-- select * from doctors where firstname like '%a';
select speciality, count(case when gender = 'F' then 1 end) as f, count(case when gender = 'M' then 1 end)as m from doctors group by speciality;
