#Homework 2

##Problem 1:

1) Files Attached in E-mail Submission  
2) File Attached in E-mail Submission  
3) The following set of trials starts with different probability conditions for each method call and then the terminal output which gives the runtime for each list type.  For each test, I do not time how long it takes to initialize the list, just the methods run after the list is done initializing.

*10,000 Randomly selected Adds, Removes, Contains over 8 threads on a 10,000 item list [with equal probability of each being called]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 205ms

Trying Fine Grain Lock
The above test ran in: 295ms

Trying Lazy Lock
The above test ran in: 61ms

Trying Lazy Lock with cleanUp
The above test ran in: 74ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 203ms

Trying Fine Grain Lock
The above test ran in: 284ms

Trying Lazy Lock
The above test ran in: 60ms

Trying Lazy Lock with cleanUp
The above test ran in: 77ms

**Trial 3**:  

Trying Coarse Grain Lock
The above test ran in: 206ms

Trying Fine Grain Lock
The above test ran in: 288ms

Trying Lazy Lock
The above test ran in: 73ms

Trying Lazy Lock with cleanUp
The above test ran in: 57ms
*10,000 Randomly selected Adds, Removes, Contains over 8 threads on a 10,000 item list [with 50% likelihood of a remove, 25% add, 25% contains]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 206ms

Trying Fine Grain Lock
The above test ran in: 270ms

Trying Lazy Lock
The above test ran in: 62ms

Trying Lazy Lock with cleanUp
The above test ran in: 69ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 203ms

Trying Fine Grain Lock
The above test ran in: 273ms

Trying Lazy Lock
The above test ran in: 59ms

Trying Lazy Lock with cleanUp
The above test ran in: 79ms

**Trial 3**:  

Trying Coarse Grain Lock
The above test ran in: 208ms

Trying Fine Grain Lock
The above test ran in: 272ms

Trying Lazy Lock
The above test ran in: 61ms

Trying Lazy Lock with cleanUp
The above test ran in: 61ms

*10,000 Randomly selected Adds, Removes, Contains over 8 threads on a 10,000 item list [with 25% likelihood of a remove, 25% add, 50% contains]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 205ms

Trying Fine Grain Lock
The above test ran in: 296ms

Trying Lazy Lock
The above test ran in: 69ms

Trying Lazy Lock with cleanUp
The above test ran in: 56ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 205ms

Trying Fine Grain Lock
The above test ran in: 298ms

Trying Lazy Lock
The above test ran in: 64ms

Trying Lazy Lock with cleanUp
The above test ran in: 80ms

**Trial 3**:  
Trying Coarse Grain Lock
The above test ran in: 202ms

Trying Fine Grain Lock
The above test ran in: 301ms

Trying Lazy Lock
The above test ran in: 60ms

Trying Lazy Lock with cleanUp
The above test ran in: 57ms

*10,000 Randomly selected Adds, Removes, Contains over 8 threads on a 10,000 item list [with 75% likelihood of a remove, 15% add, 10% contains]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 199ms

Trying Fine Grain Lock
The above test ran in: 240ms

Trying Lazy Lock
The above test ran in: 58ms

Trying Lazy Lock with cleanUp
The above test ran in: 56ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 199ms

Trying Fine Grain Lock
The above test ran in: 242ms

Trying Lazy Lock
The above test ran in: 61ms

Trying Lazy Lock with cleanUp
The above test ran in: 55ms

**Trial 3**:  
Trying Coarse Grain Lock
The above test ran in: 196ms

Trying Fine Grain Lock
The above test ran in: 242ms

Trying Lazy Lock
The above test ran in: 60ms

Trying Lazy Lock with cleanUp
The above test ran in: 61ms

*20,000 Randomly selected Adds, Removes, Contains over 20 threads on a 20,000 item list [with equal probability of each method]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 753ms

Trying Fine Grain Lock
The above test ran in: 1160ms

Trying Lazy Lock
The above test ran in: 224ms

Trying Lazy Lock with cleanUp
The above test ran in: 262ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 750ms

Trying Fine Grain Lock
The above test ran in: 1157ms

Trying Lazy Lock
The above test ran in: 239ms

Trying Lazy Lock with cleanUp
The above test ran in: 255ms

**Trial 3**:  

Trying Coarse Grain Lock
The above test ran in: 754ms

Trying Fine Grain Lock
The above test ran in: 1207ms

Trying Lazy Lock
The above test ran in: 234ms

Trying Lazy Lock with cleanUp
The above test ran in: 212ms

*10,000 Randomly selected Adds, Removes, Contains over 15 threads on a 40,000 item list [with equal probability of each method]:*

**Trial 1**:  
Trying Coarse Grain Lock
The above test ran in: 211ms

Trying Fine Grain Lock
The above test ran in: 439ms

Trying Lazy Lock
The above test ran in: 73ms

Trying Lazy Lock with cleanUp
The above test ran in: 76ms

**Trial 2**:  

Trying Coarse Grain Lock
The above test ran in: 208ms

Trying Fine Grain Lock
The above test ran in: 406ms

Trying Lazy Lock
The above test ran in: 72ms

Trying Lazy Lock with cleanUp
The above test ran in: 73ms

**Trial 3**:  

Trying Coarse Grain Lock
The above test ran in: 210ms

Trying Fine Grain Lock
The above test ran in: 439ms

Trying Lazy Lock
The above test ran in: 76ms

Trying Lazy Lock with cleanUp
The above test ran in: 78ms

Corollary: The Lazy Lock and Lazy Lock with cleanUp are the fastest implementations under all conditions.  My guess is that any discrepancy between the two is just a matter of how many removes actually occur at run time; whichever one gets burdened with more removes tends to be less performant.  Between the fine grain and coarse grain lock, the coarse grain is more performant on all measures and under no test condition did they begin to converge significantly.

4) A possible method (that is similar to the method proposed in the homework) would be to use a large set of integers and run a known number of adds and removes, in a predetermined order, onto a list from this set.  Our correctness condition could be that we sum up all of the integers in order at the end of the list and then, run it again sequentially to see if the same numbers ended up in the list.  This would be different than checking the order - which may or may not be preserved since correctness implies linearizability of the calls and returns - though we can see if we get the same sum in both cases.  You would likely need to run this a number of times since there are many possible combinations that lead to the same number; though, the probability of this gets small the bigger the list is.  

This, and really any, correctness testing condition has a difficulty since linearizabilty does not necessitate any end result that would be obtained from a particular sequential run.  Yes, any linearizable set of calls should be able to be replicated sequentially, however, we can't just compare 2 lists since the first could be correct yet give us different results than the sequential run of the same operations.  It may be tempting to do print statements or save your sequence of events in some other fashion; however, this will come at the cost of significant slowdowns that may actually correct an implantation that is not working.  For instance, this happened to me on the last homework where I was getting the correct solution and once I removed my debug print statements, the speed up of the program actually broke the concurrent locking structure.  

The only true test is to actually use your data structure for known use cases (e.g., a set of computations, obtaining real-time user data, etc.) and to see if you get the expected result.  From my understanding, even well-tested concurrent structures reach unexpected states over long periods of real-world use.

##Problem 2

There logic is flawed because when you assume that your only concern is whether or not the item has been marked; however, the structure of the list can also change in unfortunate ways whether or not it is due to a remove.

Take the example that in a list of 4 items-

[3] ---> [5] ---> [7] ---> [9]

you get two calls from threads 1 and 2, respectively:  
T1: remove(7)  
T2: add(6)

say that thread 1's remove(7) runs until it has pred = 5 and curr = 7  
however, it is overtaken by thread 2 just before it acquires its locks on 5 and 7, and is forced to wait.  
Now thread 2's add(6) succeeds and our list looks like this:

[3] ---> [5] ---> [6] ---> [7] ---> [9]

When thread 1 run acquires its locks on 5 and 7, then removes 7; it will succeed because neither 5 nor 7 has been marked; however, it will effectively remove 6 from the list by changing 5's next pointer to 9 as it tries to remove 7.  After thread 1 completes, the list will look like this:

[3] ---> [5] ---> [9]

And these two nodes will be unlinked, with 7 as a marked node:

 [6] ---> [7]
