select * from Patient where DoctorID IN 
 (select Did from doctor, Role where doctor.Country=Patient.Country 
 AND doctor.Did=Role.DoctorID AND Role.Specialisation="Clinical Nutrition");