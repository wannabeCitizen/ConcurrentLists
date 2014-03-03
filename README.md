Here you'll find a variety of linked list implementations meant to be used
in shared memory environments between threads.  I've implemented a list that 
uses coarse grain locking, fine grain locking, and lazy locking. 

Coarse grain lists lock the entire list when any other thread is doing an operation, fine grain only locks nodes, and lazy lists locks just the nodes that are having actions taken on them (i.e., add or remove).
