select * from doctor where doctor.salary >= 
(select avg(salary) from doctor);