select DId,Name,Specialisation from doctor inner join Role on DId=DoctorID
where  Did NOT IN ( select DoctorID from Role
where Specialisation="Clinical Nutrition");
