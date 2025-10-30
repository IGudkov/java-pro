package ru.inno.java.pro.task3;

public class App {
  public static void main(String[] args) throws InterruptedException {
    CustomThreadPool customThreadPool = new CustomThreadPool(5);

    for (int i = 0; i < 25; i++) {
      final int id = i;
      customThreadPool.execute(() -> {
        System.out.printf("%s выполнение задачи %d%n", Thread.currentThread().getName(), id);
        try {
          Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
      });
    }

    System.out.println("Завершение работы всех потоков..");
    customThreadPool.shutdown();

    System.out.println("Ожидание завершения всех потоков..");
    customThreadPool.awaitTermination();

    System.out.println("Работа всех потоков завершена");
  }
}
