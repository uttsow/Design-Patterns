# CSX42: Assignment 4

## Name: Uttsow

---

#### The following are instructions on how to compile and run the project. As well as a description and Data Structure justifications.

#### Note: build.xml is present in troubleshootsearch/src folder.

---

## Instruction to clean:

```console
ant -buildfile troubleShootSearch/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

---

## Instruction to compile:

---

```console
ant -buildfile troubleShootSearch/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

---

## Instruction to run:

---

```console
ant -buildfile troubleShootSearch/src/build.xml run -Darg0="userInput.txt" -Darg1="output.txt" -Darg2="technicalInfo.txt" -Darg3="synonyms.txt" -Darg4="1"
```

Note: Last argument is the Debugger level for MyLogger; You can choose from 0-5
Note: Arguments accept the absolute path of the files.

---

## Instruction to tarzip:

---

```console
ant -buildfile troubleShootSearch/src/build.xml tarzip
```

Description: tarzips the directory

---

## Description:

---

This assignment implements the visitor pattern to perform exact matching, naive stemming match, and semantic match for two elements, MyArraylist and MyTree. There are 5 visitors: - ArrayPopulatorVisitor: visits the element "MyArrayList" and populates the list with all the information from "technicalInfo.txt" - TreePopulatorVisitor: visits the element "MyTree" and populates the Binary Tree with all the information from "technicalInfo.txt" - ExactMatch: Performs the exact matching algorithm - StemMatch: Performs Naive Stemming Match - SemanticMatch: Performs semantic match

##### NOTE: My Naive Stemming Match algorithm only checks for Strings that are a substring of the word in the sentence. The substring must be smaller in length to the word in the sentence. For example, if user input is: "detect", then "detecting" would result in StemMatch but "detect" would NOT! Madhu said i can leave as this.

Debug information:

    - 0: No output should be printed. Only error messages should be printed (for example, message from a catch clause before exiting).
    - 1: Only the search results that have matches should be printed
    - 2: Constructor called
    - 3: Any method other than visit called
    - 4: Accept method called
    - 5: Visit method called

---

## Academic Honesty statement:

---

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 11/15/19

---

## Data Structure Explanation:

---

- LinkedHashMap: Used for various purposes throughout the program. They were used to store the results of each of the matching algorithm in order
  - Time Complexity: O(1) for insert and look up
- HashMap: Used for storing bidirectional Synonyms in SemanticMatch
  - Time Complexity: O(1) for insert and look up
- ArrayList: ArrayLists were used as Values for the LinkedHashMap so that one key can have multiple values. They are also used to print result to file
  - Time complexity: O(n) for finding items, O(1) for adding items
  - Space complexity: O(n)

---

## Citation

---

https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java

https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way/1277904

https://stackoverflow.com/questions/29403319/writing-an-insert-method-for-a-binary-search-tree

## Assumptions

---

- The files are well formatted and contains no errors
- I only check files for empty files
- userInput wont have repeated key/phrases
- Synonyms share a 1-1 relationship. My hashmap has 1 word corresponding to another word. NOT a Set;
- a guarantee that synonyms format will be <word>=<word>
- My interpretation of Naive Stemming Match (check description);
