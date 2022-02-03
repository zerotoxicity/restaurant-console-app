OUT := out

run:
	rm -rf out
	javac -cp src/main/java/rrpss src/main/java/rrpss/*.java -d $(OUT)
	java -cp $(OUT) Main
