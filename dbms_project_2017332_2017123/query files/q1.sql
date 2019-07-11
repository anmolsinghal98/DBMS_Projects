select * from Patient P where( 
select count(*) from PatientAllergies A where A.PatientID= P.PatientID group by PatientID) >= ALL(
(select count(*) from PatientAllergies group by PatientID));


