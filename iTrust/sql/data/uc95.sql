INSERT INTO icdcode (code, name, is_chronic) VALUES
('D6851', 'Factor V Leiden mutation', 1),
('O211', 'Hyperemesis gravidarum with metabolic disturbance', 1),
('E039', 'Hypothyroidism, unspecified', 1),
('E10', 'Type 1 diabetes mellitus', 0), 
('E11', 'Type 2 diabetes mellitus', 0), 
('E08', 'Diabetes mellitus due to underlying condition', 1),
('C50', 'Malignant neoplasm of breast', 0),
('C7A', 'Malignant neuroendocrine tumors', 0), 
('C15', 'Malignant neoplasm of esophagus', 0),
('A568', 'STD', 0)
ON duplicate key update code=code;

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
'2018-10-01',
4,
'2018-10-30'
)ON DUPLICATE KEY UPDATE ID = ID;

/*Insert office visit*/
INSERT INTO officevisit (
	patientMID, 
	visitDate, 
	locationID, 
	apptTypeID, 
	weight, 
	height,
	blood_pressure,
	household_smoking_status,
	patient_smoking_status,
	hdl,
	ldl,
	triglyceride) 
VALUES (1, DATE(NOW()-INTERVAL 1 WEEK), 1, 1, 170, 70, '100/68', 1, 4, 45, 81, 105);

set @ov = LAST_INSERT_ID();

/*Insert diagnoses*/
INSERT INTO diagnosis (visitId, icdCode)
VALUES (@ov, 'D6851'), (@ov, 'O211'), (@ov, 'E039'), (@ov, 'E10'), (@ov, 'C50'), (@ov, 'A568');

/*Insert Allergies*/
INSERT INTO allergies (PatientID, Description, FirstFound, Code)
VALUES (1, 'Penicillin', '2018-10-01', 'Z88');

