select * from Patient P
where P.PackageID IN (
select `Package ID` from Package P1 where
P.PackageID=P1.`Package ID` AND P1.Type="WL");