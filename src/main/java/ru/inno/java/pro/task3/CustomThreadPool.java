package ru.inno.java.pro.task3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {
  private final List<Thread> threads = new ArrayList<>();
  private final LinkedList<Runnable> tasks = new LinkedList<>();
  private volatile boolean isShutdown = false;

  public CustomThreadPool(int nThreads) {
    if (nThreads <= 0) {
      String details = String.format("Задано недопустимое количество потоков %d. Количество потоков должно быть > 0", nThreads);
      throw new IllegalArgumentException(details);
    }

    for (int i = 0; i < nThreads; i++) {
      Thread thread = new Thread(() -> {
        while (true) {
          Runnable task;
          synchronized (tasks) {
            while (tasks.isEmpty() && !isShutdown) {
              try {
                tasks.wait();
              } catch (InterruptedException ignored) {
              }
            }

            if (tasks.isEmpty() && isShutdown) {
              break;
            }

            task = tasks.removeFirst();
          }

          try {
            task.run();
          } catch (Throwable t) {
            t.printStackTrace();
          }
        }
      }, "CustomThreadPool-thread-" + i);
      thread.start();
      threads.add(thread);
    }

  }

  public void execute(Runnable task) {
    if (task == null) {
      return;
    }

    synchronized (tasks) {
      if (isShutdown) {
        throw new IllegalStateException("Выполняется завершение работы всех потоков. Задачи больше не принимаются.");
      }

      tasks.addLast(task);
      tasks.notify();
    }
  }

  public void shutdown() {
    synchronized (tasks) {
      isShutdown = true;
      tasks.notifyAll();
    }
  }

  public void awaitTermination() throws InterruptedException {
    for (Thread t : threads) {
      t.join();
    }
  }
}
