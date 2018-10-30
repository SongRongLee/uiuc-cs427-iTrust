/*Inserting prior pregnancies*/
INSERT INTO pregnancyrecords(
ID,
PatientID,
Date_delivery,
num_weeks_pregnant,
num_hours_labor, 
delivery_type, 
YOC)
VALUES (
1,
1,
'2012-01-07',
41,
14,
'caesarean section',
2011
)ON DUPLICATE KEY UPDATE ID = ID;

INSERT INTO pregnancyrecords(
ID,
PatientID,
Date_delivery,
num_weeks_pregnant,
num_hours_labor, 
delivery_type, 
YOC)
VALUES (
2,
1,
'2010-05-07',
38,
12,
'vaginal delivery',
2009
)ON DUPLICATE KEY UPDATE ID = ID;

/*Inserting obstetrics records*/
INSERT INTO obstetricsrecords(
ID,
PatientID,
LMP,
number_of_weeks_pregnant,
created_on)
VALUES (
1,
1,
'2018-08-25',
11,
'2018-09-07'
)ON DUPLICATE KEY UPDATE ID = ID;

INSERT INTO obstetricsrecords(
ID,
PatientID,
LMP,
number_of_weeks_pregnant,
created_on)
VALUES (
2,
1,
'2018-09-23',
15,
'2018-10-08'
)ON DUPLICATE KEY UPDATE ID = ID;
