select * from details;

-- select * from details where age = (select max(age) from details); 
-- select * from details where age = (select min(age) from details);
-- select avg(hours) from details;
-- select sum(rate) from details where gender = "F";
-- select sum(rate) from details where gender = "M";
-- select avg(age), department from details group by department;
-- select avg(age), position from details group by position;
-- select department, count(case when gender = 'F' then 1 end) as f, count(case when gender = 'M' then 1 end)as m from details group by department
-- select firstName, lastName, (rate * hours) as wage from details order by wage desc limit 3
