# CSX42: Assignment 2

## Name: Uttsow

---

#### The following are instructions on how to compile and run the project. As well as a description and Data Structure justifications.

#### Note: build.xml is present in coursesRegistration/src folder.

---

## Instruction to clean:

```console
ant -buildfile studentCoursePlanner/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

---

## Instruction to compile:

---

```console
ant -buildfile studentCoursePlanner/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

---

## Instruction to run:

---

```console
ant -buildfile studentCoursePlanner/src/build.xml run -Darg0="input.txt" -Darg1="output.txt"
```

Note: Arguments accept the absolute path of the files.

---

## Description:

This program aims to use State Pattern design to implement course planning. A input file is given in the format:
<studentID (1000-9999)>: <course> <course> <course> ... <course>
and this program determines if given the student's courses prefernce, if the student will be eligible to graduate. It tracks the number of semesters it will take and the order of courses to take in order to graduate within those semester as well as the number of state changes that occurred. A state pattern design file is also given to help understand the design logic of this program

---

---

## Academic Honesty statement:

---

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 9/29/19

---

## Data Structure Explanation:

---

1. Array: This was used to split the input from the file into individual strings to add it onto the queue for course planning process
   - Time complexity: O(n)
   - Space complexity: O(n)

2.HashMap: Hashmaps were used to do error checking with the course prefs. They were to used to check if all courses were valid and if there were any duplicates. They were also used for GroupFive to see if the course was included in this state. It allows for quick look up time

    - Time Complexity: O(1) for insert and look up

3.Queue: Queue's were used for the actual group processing algorithm. Each of the preferred courses were first sorted and then added on to a queue. if a course could be taken, it was popped off the queue and added to a result queue in order to preserve the order. If the course couldn't be taken, it was added to a waitlist queue and then after completing the semester, the elements in the waitlist queue were added back onto the main queue to restart the program.

      - Time Complexity: 0(1) insert and delete
      - Space Complexity: O(N).

---

## Citation

---

https://stackoverflow.com/questions/22009900/fast-way-to-load-all-alphabetic-characters-to-a-hashmap

https://stackoverflow.com/questions/14361556/remove-all-special-characters-in-java

https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java

## Assumptions

---

##### Note: I printed out a message for each step: If file reading was successful, was it the program able to parse the input, was creating the object successful,...etc. I thought this would be helpful in keeping track of the program and if everything was going to plan. Also I made it so that when you run the program, the result is written to file and printed on stdout.

1. If the student doesn't graduate, we only print out a message saying (s)he does not graduate but also include the state change in addition to making semesters 0.

2. I did not implement the CoursePlannerStateI for Context class because i didn't think it was necessary and the slides did not do it either.
