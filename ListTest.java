//Michael Skirpan
//Concurrent Programming
//Homework 2
//Test File for Lists

import java.util.Random;


public class ListTest{
  private final static int THREADS = 8;
  private final static int TOTAL_ITEMS = 10000;
  private final static int PER_THREAD = TOTAL_ITEMS / THREADS;
  int counter = 0;

  
  public static void testList(Lists myList) throws Exception {
    Thread[] thread = new Thread[THREADS];


    System.out.println("test parallel");
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
    System.out.println("The above test ran in: " + totalTime);
  }
  
  static class MyThread extends Thread {
    private Lists testList;

    public MyThread(Lists myList){
      testList = myList; 
    }

    public void run() {
      Random numberGen = new Random();
      int methodTest;
      int testNum;

      for (int i = 0; i < PER_THREAD; i++) {
        methodTest = numberGen.nextInt(3);

        if (methodTest == 0){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.remove(testNum);

        } else if (methodTest == 1){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.add(testNum);

        } else if (methodTest == 2){
          testNum = numberGen.nextInt(TOTAL_ITEMS);
          testList.contains(testNum);
        }
      }
    }
  }


  public static void main(String[] args) throws Exception {

    System.out.println("Trying Coarse Grain Lock");
    CoarseGrain cgl = new CoarseGrain();

    for (int i = 0; i < TOTAL_ITEMS; i++){
      cgl.add(i);
    }
    testList(cgl);

    System.out.println("Trying Fine Grain Lock");
    FineGrain fgl = new FineGrain();

    for (int i = 0; i < TOTAL_ITEMS; i++){
      fgl.add(i);
    }
    testList(fgl);

    System.out.println("Trying Lazy Lock");
    LazyList ll = new LazyList();

    for (int i = 0; i < TOTAL_ITEMS; i++){
      ll.add(i);
    }
    testList(ll);

    System.out.println("Trying Lazy Lock with cleanUp");
    LazyClean lc = new LazyClean();

    for (int i = 0; i < TOTAL_ITEMS; i++){
      lc.add(i);
    }
    testList(lc);
    lc.endTime();
  }

}
