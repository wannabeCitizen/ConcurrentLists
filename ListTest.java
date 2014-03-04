//Michael Skirpan
//Concurrent Programming
//Homework 2
//Test File for Lists

import java.util.Random;

// Test Class
public class ListTest{
  private final static int THREADS = 15;
  private final static int TOTAL_ITEMS = 10000;
  private final static int LIST_LENGTH = 40000;
  private final static int PER_THREAD = TOTAL_ITEMS / THREADS;
  int counter = 0;

  // This actually initializes and runs the threads, and also times the test
  public static void testList(Lists myList) throws Exception {
    Thread[] thread = new Thread[THREADS];


    long time1 = System.currentTimeMillis();

      for (int i = 0; i < THREADS; i++) {
	  thread[i] = new MyThread(myList);
      }

      for (int i = 0; i < THREADS; i++) {
	  thread[i].start();
      }

      for (int i = 0; i < THREADS; i++) {
	  thread[i].join();
      }

    long time2 = System.currentTimeMillis();
    long totalTime = time2-time1;
    System.out.println("The above test ran in: " + totalTime + "ms \n");
  }
  
  // Class for threads
  static class MyThread extends Thread {
    private Lists testList;

    public MyThread(Lists myList){
      testList = myList; 
    }

    // The task each thread runs, which contains randomly selected adds and removes
    // In this case the probabilities are even, but I changed them around in testing.
    public void run() {
      Random numberGen = new Random();
      float methodTest;
      int testNum;

      for (int i = 0; i < PER_THREAD; i++) {
        methodTest = numberGen.nextFloat();

        if (methodTest <= .33){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.remove(testNum);

        } else if (methodTest > .33 && methodTest <= .66){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.add(testNum);

        } else if (methodTest > .66){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.contains(testNum);
        }
      }
    }
  }

  // Main function, which initializes each list and then calls its test
  public static void main(String[] args) throws Exception {

    System.out.println("Trying Coarse Grain Lock");
    CoarseGrain cgl = new CoarseGrain();

    for (int i = 0; i < LIST_LENGTH; i++){
      cgl.add(i);
    }
    testList(cgl);

    System.out.println("Trying Fine Grain Lock");
    FineGrain fgl = new FineGrain();

    for (int i = 0; i < LIST_LENGTH; i++){
      fgl.add(i);
    }
    testList(fgl);

    System.out.println("Trying Lazy Lock");
    LazyList ll = new LazyList();

    for (int i = 0; i < LIST_LENGTH; i++){
      ll.add(i);
    }
    testList(ll);

    System.out.println("Trying Lazy Lock with cleanUp");
    LazyClean lc = new LazyClean();

    for (int i = 0; i < LIST_LENGTH; i++){
      lc.add(i);
    }
    testList(lc);
    lc.endTime();
  }

}
