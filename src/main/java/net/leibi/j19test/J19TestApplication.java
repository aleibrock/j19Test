package net.leibi.j19test;

import static java.lang.System.nanoTime;

public class J19TestApplication {

  private static final int MILLISECONDS_PER_SECOND = 1000;
  private static final long NANOSECONDS_PER_SECOND = 1_000_000;

  public static void main(String[] args) throws InterruptedException {

    long start = nanoTime();
    VirtualThreads.runnables();
    long runnables = nanoTime();
    VirtualThreads.threads();
    long threads = nanoTime();
    VirtualThreads.virtual();
    long virtualthreads = nanoTime();

    Thread.sleep(1000);

    System.out.println(String.format("Total time: %d", millisecondsBetween(start, virtualthreads)));
    System.out.println(String.format("Runnables time: %d", millisecondsBetween(start,runnables)));
    System.out.println(String.format("Threads time: %d", millisecondsBetween(runnables, threads)));
    System.out.println(String.format("VThreads time: %d", millisecondsBetween(threads,virtualthreads)));
  }

  static long millisecondsBetween(long start, long end) {
    return MILLISECONDS_PER_SECOND * ((end - start) / NANOSECONDS_PER_SECOND);
  }

}
