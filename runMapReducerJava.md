

create project in eclipse 

file -> java project -> give name WordCount -> finish

right click WordCount -> new -> package -> give name WordCount -> finish

again right click on WordCount -> new -> class -> give name WordCount -> include main function -> finish 

write code in WordCount 


right click on project again -> build path -> add externmaal lib -> usr -> lib -> Hadoop -> hadoop-common-2.6.0-cdh5.4.2.jar 
then again link -> usr -> lib -> Hadoop-0.20-mapreduce -> hadoop-core-2.6.0.mr1-cdh5.4.2.jar

export 
right click on project -> export -> java -> jar -> brouse -> WordCount.jar -> next -> next -> finish 




Hadoop:-

open terminal 

Hadoop fs -put Desktop/Input/file_name.txt alias.txt  

Hadoop fs -ls

Hadoop jar jar_file_name.jar WordCount.WordCount alias.txt Dir5(where to store output)
			     (package , public class name)


Hadoop fs -cat Dir5/part-r-00000
