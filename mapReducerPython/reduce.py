import sys
query_words = set(word.lower() for word in sys.argv[1:])

current_word = None
count = 0
results = {}

for line in sys.stdin:
	word , num = line.strip().split("\t")
	word = word.lower()
	num = int(num)
	if word == current_word:
		count += num
	else:
		if current_word:
			if query_words:
				results[current_word] = count
			else:
				print(current_word + "\t" + str(count))
		current_word = word
		count = num

if current_word:
	if query_words:
		results[current_word] = count
	else:
		print(current_word + "\t" + str(count))

if query_words:
	for word in query_words:
		print(word + "\t" + str(results.get(word , 0)))