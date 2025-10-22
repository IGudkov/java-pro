package ru.inno.java.pro.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.inno.java.pro.task2.enums.Post.ANALYST;
import static ru.inno.java.pro.task2.enums.Post.BOSS;
import static ru.inno.java.pro.task2.enums.Post.ENGINEER;
import static ru.inno.java.pro.task2.enums.Post.TESTER;

public class App {
  public static void main(String[] args) {
    System.out.println("------------------------------");

    List<Integer> integers = new ArrayList<>(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13));
    System.out.printf("Список целых чисел: %s%n%n", integers);

    int thirdLargest = findThirdLargest(integers);
    System.out.printf("3-е наибольшее число: %s%n%n", thirdLargest);

    int uniqueThirdLargest = findUniqueThirdLargest(integers);
    System.out.printf("3-е наибольшее уникальное число: %s%n%n", uniqueThirdLargest);

    System.out.println("------------------------------");

    List<Employee> employees = Arrays.asList(
        new Employee("Александр", 45, BOSS),
        new Employee("Иван", 30, ENGINEER),
        new Employee("Мария", 25, TESTER),
        new Employee("Виталий", 35, ENGINEER),
        new Employee("Глория", 23, ANALYST),
        new Employee("Кирилл", 33, ENGINEER),
        new Employee("Елена", 28, TESTER),
        new Employee("Тимур", 37, ENGINEER),
        new Employee("Виктория", 29, ANALYST),
        new Employee("Алексей", 31, ENGINEER)
    );

    System.out.println("Список сотрудников:");
    employees.forEach(System.out::println);
    System.out.println();

    List<String> mostAdultThreeNameEngineers = findMostAdultThreeNameEngineers(employees);
    System.out.printf("Список имен 3 самых старших инженеров в порядке убывания возраста: %s%n%n", mostAdultThreeNameEngineers);

    double averageAgeEngineers = getAverageAgeEngineers(employees);
    System.out.printf("Средний возраст инженеров: %.2f%n%n", averageAgeEngineers);

    System.out.println("------------------------------");

    List<String> words = new ArrayList<>(Arrays.asList("собака", "кошка", "попугай", "слон"));
    System.out.printf("Список слов: %s%n%n", words);

    String longestWord = getLongestWord(words);
    System.out.printf("Самое длинное слово: %s%n%n", longestWord);

    System.out.println("------------------------------");

    String wordsString = "собака кошка собака кошка попугай кошка слон попугай собака кошка";
    System.out.printf("Набор слов в нижнем регистре, разделенных пробелом: %s%n%n", wordsString);

    Map<String,Integer> mapGroupByCount = getMapGroupByCount(wordsString);
    System.out.printf("Хеш-мапа с парами: слово - сколько раз оно встречается во входной строке: %s%n%n", mapGroupByCount);

    System.out.println("------------------------------");

    List<String> wordsToSort = new ArrayList<>(Arrays.asList("собака", "кошка", "попугай", "слон", "крошка", "кружка"));
    System.out.printf("Список слов для сортировки по длине и алфавиту: %s%n%n", wordsToSort);
    List<String> sortedWords = sortWordsByLengthAndAlphabetically(wordsToSort);
    System.out.printf("Список слов в порядке увеличения длины и алфавитном порядке: %s%n%n", sortedWords);

    System.out.println("------------------------------");

    String[] arrayStrings = {
        "пришла осень листья падают вокруг",
        "новый год к нам мчится",
        "потом весна и ручьи зажурчат",
        "затем долгожданное и теплое лето"
    };
    System.out.printf("Массив строк: %s%n%n", Arrays.asList(arrayStrings));

    String longestWordInArray = findLongestWordInArray(arrayStrings);
    System.out.printf("Самое длинное слово в массиве строк: %s%n", longestWordInArray);
  }

  private static int findThirdLargest(List<Integer> ints) {
    return ints.stream()
        .sorted(Comparator.reverseOrder())
        .skip(2)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("3-е наибольшее число не найдено"));
  }

  private static int findUniqueThirdLargest(List<Integer> ints) {
    return ints.stream()
        .distinct()
        .sorted(Comparator.reverseOrder())
        .skip(2)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("3-е наибольшее уникальное число не найдено"));
  }

  private static List<String> findMostAdultThreeNameEngineers(List<Employee> employees) {
    return employees.stream()
        .filter(a -> ENGINEER.equals(a.getPost()))
        .sorted(Comparator.comparingInt(Employee::getAge).reversed())
        .limit(3)
        .map(Employee::getName)
        .toList();
  }

  private static double getAverageAgeEngineers(List<Employee> employees) {
    return employees.stream()
        .filter(a -> ENGINEER.equals(a.getPost()))
        .mapToInt(Employee::getAge)
        .average()
        .orElseThrow(() -> new RuntimeException("Средний возраст инженеров не может быть рассчитан"));
  }

  private static String getLongestWord(List<String> words) {
    return words.stream()
        .max(Comparator.comparingInt(String::length))
        .orElseThrow(() -> new RuntimeException("Самое длинное слово не найдено"));
  }

  private static Map<String, Integer> getMapGroupByCount(String setOfWords) {
    return Arrays.stream(setOfWords.split("\\s+"))
        .collect(Collectors.toMap(word -> word, value -> 1, Integer::sum));
  }

  private static List<String> sortWordsByLengthAndAlphabetically(List<String> words) {
    return words.stream()
        .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
        .toList();
  }

  private static String findLongestWordInArray(String[] arrayString) {
    return Arrays.stream(arrayString)
        .flatMap(s -> Arrays.stream(s.split("\\s+")))
        .max(Comparator.comparingInt(String::length))
        .orElseThrow(() -> new RuntimeException("Самое длинное слово не найдено"));
  }
}
