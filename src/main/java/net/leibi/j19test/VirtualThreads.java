package net.leibi.j19test;

class RunnableDemo implements Runnable {

  private final String threadName;
  private Thread t;

  RunnableDemo(String name) {
    threadName = name;
    System.out.println("Creating " + threadName);
  }

  public void run() {
    System.out.println("Running " + threadName);
    try {
      for (int i = 4; i > 0; i--) {
        System.out.println("Thread: " + threadName + ", " + i);
        // Let the thread sleep for a while.
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      System.out.println("Thread " + threadName + " interrupted.");
    }
    System.out.println("Thread " + threadName + " exiting.");
  }

  public void start() {
    System.out.println("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }
}


class ThreadDemo extends Thread {

  private Thread t;
  private String threadName;

  ThreadDemo(String name) {
    threadName = name;
    System.out.println("Creating " + threadName);
  }

  public void run() {
    System.out.println("Running " + threadName);
    try {
      for (int i = 4; i > 0; i--) {
        System.out.println("Thread: " + threadName + ", " + i);
        // Let the thread sleep for a while.
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      System.out.println("Thread " + threadName + " interrupted.");
    }
    System.out.println("Thread " + threadName + " exiting.");
  }

  public void start() {
    System.out.println("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }
}


public class VirtualThreads {

  public static final int THREADS = 10_000;

  public static void runnables() {

    for (int i = 0; i < THREADS; i++) {
      RunnableDemo R1 = new RunnableDemo("Runnable-" + i);
      R1.start();
    }
  }

  public static void threads() {

    for (int i = 0; i < THREADS; i++) {
      ThreadDemo R1 = new ThreadDemo("Thread-" + i);
      R1.start();
    }
  }

  public static void virtual() {
    for (int i = 0; i < THREADS; i++) {
      Thread.ofVirtual().start(new RunnableDemo("VirtualRunnable" + i));
    }
  }

}
