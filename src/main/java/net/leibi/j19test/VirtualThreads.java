package net.leibi.j19test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
class RunnableDemo implements Runnable {

  private final String threadName;
  private Thread t;

  RunnableDemo(String name) {
    threadName = name;
    log.info("Creating " + threadName);
  }

  public void run() {
    running(log, threadName);
  }

  static void running(Logger log, String threadName) {
    log.info("Running " + threadName);
    try {
      for (int i = 4; i > 0; i--) {
        log.info("Thread: " + threadName + ", " + i);
        // Let the thread sleep for a while.
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      log.info("Thread " + threadName + " interrupted.");
    }
    log.info("Thread " + threadName + " exiting.");
  }

  public void start() {
    log.info("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }
}


@Slf4j
class ThreadDemo extends Thread {

  private Thread t;
  private String threadName;

  ThreadDemo(String name) {
    threadName = name;
    log.info("Creating " + threadName);
  }

  @Override
  public void run() {
    RunnableDemo.running(log, threadName);
  }

  @Override
  public void start() {
    log.info("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }
}


@Slf4j
public class VirtualThreads {

  public static final int THREADS = 10_000;

  public static void runnablesTest() {

    for (int i = 0; i < THREADS; i++) {
      RunnableDemo r1 = new RunnableDemo("Runnable-" + i);
      r1.start();
    }
  }

  public static void threadsTest() {

    for (int i = 0; i < THREADS; i++) {
      ThreadDemo r1 = new ThreadDemo("Thread-" + i);
      r1.start();
    }
  }

  public static void virtualThreadsTest() {
    for (int i = 0; i < THREADS; i++) {
      Thread.ofVirtual().start(new RunnableDemo("VirtualRunnable" + i));
    }
  }

  public void executors(){
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      var future1 = executor.submit(() -> fetchURL(new URL("https://www.heise.de")));
      var future2 = executor.submit(() -> fetchURL(new URL("https://www.infrontfinance.com")));
      log.info(future2.get());
      log.info(future1.get());
    } catch (ExecutionException | InterruptedException e) {
     log.info("Fail",e);
    }
  }

  String fetchURL(URL url) throws IOException {
    try (var in = url.openStream()) {
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

}


