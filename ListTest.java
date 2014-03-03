//Michael Skirpan
//Concurrent Programming
//Homework 2
//Test File for Lists

import java.util.Random;


public class ListTest{
  private final static int THREADS = 8;
  private final static int TOTAL_ITEMS = 10000;
  private final static int COUNT = 10000;
  private final static int PER_THREAD = COUNT / THREADS;
  Thread[] thread = new Thread[THREADS];
  int counter = 0;

  
  public void testList(Lists myList) throws Exception {
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
  
  class MyThread extends Thread {
    private Lists testList;

    public void MyThread(Lists myList){
      testList = myList; 
    }

    public void run() {
      numberGen = new Random();

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


  public static void main(String[] args) {
    System.out.println("Trying Coarse Grain Lock");
    CourseGrain cgl = new CourseGrain();
    for (i = 0; i < TOTAL_ITEMS; i++){
      cgl.add(i);
    }
    testList(cgl);

    System.out.println("Trying Fine Grain Lock");
    FineGrain fgl = new FineGrain();
    for (i = 0; i < TOTAL_ITEMS; i++){
      fgl.add(i);
    }
    testList(fgl);

    System.out.println("Trying Lazy Lock");
    LazyList ll = new LazyList();
    for (i = 0; i < TOTAL_ITEMS; i++){
      ll.add(i);
    }
    testList(ll);

    System.out.println("Trying Lazy Lock with cleanUp");
    LazyClean lc = new LazyClean();
    for (i = 0; i < TOTAL_ITEMS; i++){
      lc.add(i);
    }
    testList(lc);


    
}
