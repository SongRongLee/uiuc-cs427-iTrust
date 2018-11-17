INSERT INTO icdcode (code, name, is_chronic) VALUES
('D6851', 'Factor V Leiden mutation', 1),
('O211', 'Hyperemesis gravidarum with metabolic disturbance', 1),
('E039', 'Hypothyroidism, unspecified', 1),
('E10', 'Type 1 diabetes mellitus', 0), 
('E11', 'Type 2 diabetes mellitus', 0), 
('E08', 'Diabetes mellitus due to underlying condition', 1),
('C50', 'Malignant neoplasm of breast', 0),
('C7A', 'Malignant neuroendocrine tumors', 0), 
('C15', 'Malignant neoplasm of esophagus', 0)
ON duplicate key update code=code;
