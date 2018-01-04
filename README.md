# Numbers
A little tool for converting number strings to their textual component.

## Building
Maven please!  

```
$ mvn clean package
```

## Running

The jar itself is executable and has no external dependencies other than the JRE.  For example:
```
$ java -jar target/numbers.jar 548.123 1000001 123 123.000 --999,999,999 0 -0 a123 1
Five hundred forty eight thousand one hundred twenty three
One million one
One hundred twenty three
One hundred twenty three thousand
Minus nine hundred ninety nine million nine hundred ninety nine thousand nine hundred ninety nine
Zero
Minus zero
Error:  Cannot parse a123, invalid number
One
```

For other details, please consult the comments within the code.
