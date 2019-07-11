select * from Package P where NOT EXISTS (
select Did from doctor D where Did NOT IN (
select DoctorID from Patient P1 where P.`Package ID`=P1.PackageID));