insert into regions (name)
values
	('Australia and Oceania'), -- 1
	('Asia'), -- 2
	('America'), -- 3
	('Africa'), -- 4
	('Europe'), -- 5
	('Eurasia'); -- 6

insert into countries (name, rating, region)
values
    ('Kazakhstan', 3.0, 6),
    ('Russia', 3.0, 6),
    ('Belarus', 2.0, 5),
    ('Ukraine', 3.5, 5),
    ('Kyrgyzstan', 3.2, 6);