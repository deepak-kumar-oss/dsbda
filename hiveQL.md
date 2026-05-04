open terminal

-> hive
hive> create database if not exists testdb;
hive> use testdb;
hive> create table flight(fno int, year int , dest varchar(20) , delay float);
hive> alter table flight rename to airline;
hive> alter table airline add columns (source varchar(20));
hive> drop table airline;
hive> show tables;
hive> desc flight;
<!-- OK 
fno                 int
year                int
dest                string
delay               float
source              string
Time taken: 0.123 seconds, Fetched: 5 row(s) -->
hive> create table airline(fno int, year int , dest varchar(20) , delay float, source varchar(20))
    > row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

hive> insert into airline values(101, 2020, 'NYC', 2.5, 'LA');
hive> insert into airline values(102, 2021, 'LA', 1.0, 'NYC');
hive> select * from airline;

// in other terminal 
> gedit f.txt
101,2022,DC,3.0,NYC
202,2023,NYC,0.5,DC
(save and close the file)

hive> load data local inpath 'f.txt' overwrite into table airline;

hive> select * from airline;
<!-- 101      2020    NYC     2.5     LA
102      2021    LA      1.0     NYC
101      2022    DC      3.0     NYC
202      2023    NYC     0.5     DC -->

hive> select * from airline where delay > 1.0;
<!-- 101      2020    NYC     2.5     LA
101      2022    DC      3.0     NYC -->

hive> select dest, avg(delay) from airline group by dest;
<!-- NYC     1.5
LA      1.0
DC      3.0 -->

hive> create table nflight(fno int, year int , dest varchar(20) , delay float, source varchar(20))
    > row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

hive> insert into nflight values(101, 2020, 'NYC', 2.5, 'LA');
hive> insert into nflight values(102, 2021, 'LA', 1.0, 'NYC');
hive> select a.fno , a.year , a.dest , a.delay , b.source from airline a join nflight b on a.fno = b.fno;
hive> create index flight_idx on table airline (fno) as 'org.apache.hadoop.hive.ql.index.compact.CompactIndexHandler' with deferred rebuild;



// part  2 
steps:- create internal hive table(hive_int)
      - create a data.txt contains daata to be loaded into internal hive table
      - create external hive table(hive_ext)
      - load data from internal hive table(hive_int) to external hive table(hive_ext)
      - check the content of external hive table(hive_ext)
 
> hive 
hive> create database if not exists mydb;
hive> use mydb;
hive> create table hive_int(id int, name varchar(20) , salary float) row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

// other terminal
> gedit data.txt
1, John, 50000
2, Jane, 60000
(save and close the file)

hive> load data local inpath 'data.txt' overwrite into table hive_int;
hive> select * from hive_int;
<!-- 1       John    50000.0
2       Jane    60000.0 -->

hive> create external table hive_ext(id int, name varchar(20) , salary float) row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;
hive> insert into hive_ext select * from hive_int;
hive> select * from hive_ext;
<!-- 1       John    50000.0
2       Jane    60000.0 -->


// part 3 (continue from part 2)
steps:- create internal hive table(hive_int)
      - create a data.txt contains daata to be loaded into internal hive table
      - create Hbase table(hbase_table) with same column family as hive 
      - create external hive table(hive_ext) conneting hbase table
      - load data from internal hive table(hive_int) to external hive table(hive_ext)
      - check the content of external hive table(hive_ext) and hbase table(hbase_table)

>hive
hive> create database if not exists mydb;
hive> use mydb;
hive> show tables;
hive_int
hive_ext


// other terminal
>hbase shell
hbase> create 'hbase_table', 'cf'

// back to hive terminal
hive> create external table hive_ext1(id int, name varchar(20) , salary float) stored by 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' with serdeproperties('hbase.columns.mapping'=':key,cf:name,cf:salary') tblproperties('hbase.table.name'='hbase_table');

hive> insert into hive_ext1 select * from hive_int;

hive> select * from hive_ext1;
<!-- 1       John    50000.0
2       Jane    60000.0 -->

// back to hbase shell
hbase> scan 'hbase_table'
<!-- ROW             COLUMN+CELL
1               column=cf:name, timestamp=..., value=John
                column=cf:salary, timestamp=..., value=50000.0

2               column=cf:name, timestamp=..., value=Jane
                column=cf:salary, timestamp=..., value=60000.0 -->




deepak,12,80
ashu,10,23