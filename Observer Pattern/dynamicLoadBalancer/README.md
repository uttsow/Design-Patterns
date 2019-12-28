# CSX42: Assignment 3

## Name: Uttsow

---

#### The following are instructions on how to compile and run the project. As well as a description and Data Structure justifications.

#### Note: build.xml is present in coursesRegistration/src folder.

---

## Instruction to clean:

```console
ant -buildfile dynamicLoadBalancer/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

---

## Instruction to compile:

---

```console
ant -buildfile dynamicLoadBalancer/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

---

## Instruction to run:

---

```console
ant -buildfile dynamicLoadBalancer/src/build.xml run -Darg0="input.txt" -Darg1="output.txt"
```

Note: Arguments accept the absolute path of the files.

---

## Description:

---

This program implements a load balancer using the Observer pattern. The subject is the cluster and it has 2 different observer, a load balancer and a service manager. The load balancer is in charge of retireving the URL and a list of host machines the service is on when a REQUEST command is given. The Service Manager stores the mapping of a service to the hostnames of all the machines that host it. Each machine can host multiple service and 1 service can be on multiple machines.

The program takes in an input of all the difference method calls and outputs a file with the result

Some functionalities are:

- SERVICE_OP\_\_ADD_SERVICE <service name> <URL> <hosti, hostj, ... hostr>
- SERVICE_OP\_\_REMOVE_SERVICE <service name>
- SERVICE_OP\_\_ADD_INSTANCE <service name> <host name>
- SERVICE_OP\_\_REMOVE_INSTANCE <service name> <host name>

- CLUSTER_OP\_\_SCALE_UP <hostname of new machine>
- CLUSTER_OP\_\_SCALE_DOWN <hostname of machine to remove>
- REQUEST <Service name>

---

## Academic Honesty statement:

---

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 10/20/19

---

## Data Structure Explanation:

---

- Array: This was used to split the input course list for each student and course information so it can be parsed properly.

  - Time complexity: O(n)
  - Space complexity: O(n)

- HashMap: Used for various purposes throughout the program. They were used in the cluster file to store the mapping of host names to machines. They were used in loadbalancer to store the mapping of service name to service managers. Finally they were also used in Machine file to store the mapping of Service name to Service objects
  - Time Complexity: O(1) for insert and look up
- ArrayList: ArrayList was used to store all the machine host names in the Service Manager for the particular service name
  - Time complexity: O(n) for finding items, O(1) for adding items
  - Space complexity: O(n)

---

## Citation

---

https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java

https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way/1277904

https://docs.oracle.com/javase/tutorial/reflect/member/methodInvocation.html

http://www.avajava.com/tutorials/lessons/how-do-i-call-a-method-using-reflection.html

https://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas

## Assumptions

---

##### Note: I printed out a message for each step: If file reading was successful, was it the program able to parse the input, was creating the object successful,...etc. I thought this would be helpful in keeping track of the program and if everything was going to plan.

- I tried to implement the round robin technique for printing and retrieving hostname but was not successful and couldn't find any useful online resources to help me. The program still works, just doesn't have round robin for retrieval. Couldn't figure it out for the life of me.
