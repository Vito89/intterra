For demonstrate the project sufficiently from the root to the project call the command with the following parameters:
gradlew run -PappArgs="aaa bbb aaa"

How do you know it works?
Class iterator inlet receives a collection Further, it passes through the filling card values, where the key is used as a collection element, and as the value - the number of elements encountered to the same value in the collection.
Then, this collection sort, using an inner class to override methods "compare" and converted into an array.
The array itself is the "top" elements. It returns and above will be printed.

How does it scale?
As a variant, we can make custom size top - the collection, for example:
"new FrequencyTop (12) .printTop"
wherein the method printTop made static.
It is also possible to arrange the output to a OutputStream, for example:
"new FrequencyTop (12) .printTop (elements, System.out);".