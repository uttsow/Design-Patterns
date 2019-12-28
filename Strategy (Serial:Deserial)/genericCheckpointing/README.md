# CSX42: Assignment 5

## Name: Uttsow

---

##PLEASE READ ASSUMPTIONS!!

#### The following are instructions on how to compile and run the project. As well as a description and Data Structure justifications.

#### Note: build.xml is present in genericcheckpointing/src folder.

---

## Instruction to clean:

```console
ant -buildfile genericCheckpointing/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

---

## Instruction to compile:

---

```console
ant -buildfile genericCheckpointing/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

---

## Instruction to run:

---

```console
ant -buildfile genericCheckpointing/src/build.xml run -Darg0="deserser" -Darg1="MyAllTypes2.txt" -Darg2="checkpointFileCheck.txt" -Darg3="5"
```

Note: Last argument is the Debugger level for MyLogger; You can choose from 0-5
Note: Arguments accept the absolute path of the files.

---

## Instruction to tarzip:

---

```console
ant -buildfile genericCheckpointing/src/build.xml tarzip
```

Description: tarzips the directory

---

## Description:

---

This program utilizes dynamic proxy and java reflection to serialize and deserialize objects from a given text file. The program initially reads
the psuedo xml file and invokes a handler to invoke a deserialization
process by utilizing the strategy pattern. The file is read line by line,
the properly parsed and then using java reflection, it returns a newly
created SerializableObject object to be stored in an array. The
serializtion process occurs after the deserialization process ends.
The array is iterated through and the SerializableObject objects are then written back into a "checkpointFileCheck.txt" file to be matched with
the initial checkpoint file to see if it is the same thing.
A logger is used for debugging.

Debug information:

    - 0: No output should be printed. Only error messages should be printed (for example, message from a catch clause before exiting).
    - 1: Only the search results that have matches should be printed
    - 2: Constructor called
    - 3: When any method is called
    - 4: When invoke is called from handler
    - 5: When you want to print deserialized objects

---

## Academic Honesty statement:

---

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 12/2/19

---

## Data Structure Explanation:

---

- Array: This was used to store the splitted sentences from the file
  - Time complexity: O(n)
  - Space complexity: O(n)
- ArrayList: ArrayLists were used as Values for the LinkedHashMap so that one key can have multiple values. They are also used to print result to file
  - Time complexity: O(n) for finding items, O(1) for adding items
  - Space complexity: O(n)

---

## Citation

---

https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java

https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way/1277904

https://www.mkyong.com/java/java-how-to-overrides-equals-and-hashcode/

https://stackoverflow.com/questions/39672094/how-to-remove-all-special-character-in-a-string-except-dot-and-comma/39672126

https://examples.javacodegeeks.com/java-basics/exceptions/java-lang-reflect-invocationtargetexception-how-to-handle-invocation-target-exception/

https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java

## Assumptions

---

##IMPORTANT!
####The inner package name is "genericcheckpointing" as stated by the TA in Piazza. I changed the names from "genericCheckpointing" to "genericcheckpointing" in the "MyAllTypes2.txt" file!!!!

- The files are well formatted and contains no errors
- The file format stays the same
- naming conventions do not change
- I didnt have time to handle the case for boolean when serializing so it will print out in all cases for MyAllTypesFirst
- I did not print out the default constructor values when i serialized unless they were set by the original deserialization process. Madhu said this was fine and not to change what i had. (I know this is not how it works in the real world).
