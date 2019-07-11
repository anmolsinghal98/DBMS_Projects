select * from Prescription_DietPlan natural join Patient P1  where 
P1.PatientID IN (select PatientID from Package P
where P.`Package ID`=P1.PackageID AND Duration>3);