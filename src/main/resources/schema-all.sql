DROP TABLE IF EXISTS people;

CREATE TABLE "people" (
	"person_id"	INTEGER NOT NULL,
	first_name VARCHAR(20),
    last_name VARCHAR(20),
	PRIMARY KEY("person_id" AUTOINCREMENT)
);
