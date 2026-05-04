open terminal
>hive
hive> create database if not exists studentdb;
hive> use studentdb;

hive> create table student_int(name varchar(20), marks int , roll_no int) row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

hive> load data local inpath 'student_data.csv' overwrite into table student_int; // ye csv given hogi load karni padegi

hive> select * from student_int;

hive> select name , marks from student_int order by marks desc limit 5;

hive> select AVG(marks) from student_int; // average marks of students

hive> SELECT name , marks,
    > CASE
    >     WHEN marks >= 90 THEN 'A'
    >     WHEN marks >= 80 THEN 'B'
    >     WHEN marks >= 70 THEN 'C'
    >     ELSE 'F'
    > END AS grade
    > FROM student_int; // grading students based on marks

hive> Select name , marks from student_int where marks > (select AVG(marks) from student_int); // students with above average marks

hive> select name , marks from student_int where marks < 40; // students with marks less than 40

hive> select name , marks, rank() over (order by marks desc) as rank from student_int; // ranking students based on marks