package net.leibi.j19test;

import static java.lang.System.nanoTime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class J19TestApplication {

  private static final int MILLISECONDS_PER_SECOND = 1000;
  private static final long NANOSECONDS_PER_SECOND = 1_000_000;

  public static void main(String[] args) throws InterruptedException {

    long start = nanoTime();
    VirtualThreads.runnablesTest();
    long runnables = nanoTime();
    VirtualThreads.threadsTest();
    long threads = nanoTime();
    VirtualThreads.virtualThreadsTest();
    long virtualthreads = nanoTime();

    Thread.sleep(1000);

    log.info("Total time: {}", millisecondsBetween(start, virtualthreads));
    log.info("Runnables time: {}", millisecondsBetween(start, runnables));
    log.info("Threads time: {}", millisecondsBetween(runnables, threads));
    log.info("VThreads time: {}", millisecondsBetween(threads, virtualthreads));

    VirtualThreads virtualThreads = new VirtualThreads();
    virtualThreads.executors();
  }

  static long millisecondsBetween(long start, long end) {
    return MILLISECONDS_PER_SECOND * ((end - start) / NANOSECONDS_PER_SECOND);
  }

}
