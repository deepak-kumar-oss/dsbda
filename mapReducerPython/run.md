-> create 2 python files mapper.py and reduce.py in same folder  
mapper.py
reduce.py

-> then create a input file with some text data to be processed by map reduce program in same folder
write code in mapper.py and reduce.py

run command in terminal ->  cat input.txt | python mapper.py | sort | python reduce.py ( for all words count )
                        -> cat input.txt | python mapper.py | sort | python reduce.py word1 word2 ( for specific words count )