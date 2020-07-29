import java.util.*;

/**
 * 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных слов,
 * из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи.
 * С помощью метода get() искать номер телефона по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 * Желательно как можно меньше добавлять своего, чего нет в задании
 * (т.е. не надо в телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес),
 * делать взаимодействие с пользователем через консоль и т.д.). Консоль желательно не использовать (в том числе Scanner),
 * тестировать просто из метода main() прописывая add() и get().
 */


public class Main {
    public static String[] data;
    public static Map<String, Integer> food;
    public static HashSet<String> set;

    public static void main(String[] args) {
        data = new String[]{"Apple", "Potato", "Watermelon", "Apple", "Orange", "Orange", "Banana", "Peach", "Apple",
                "Tomato", "Grape", "Grape", "Melon", "Strawberry", "Apple"};
        findNumberOfRepeats();
        createUniqueArray();
        System.out.println("\n***************\n");
        printMap();

        System.out.println("\n***************\n");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "12353");
        phoneBook.add("Abramov ", "3452");
        phoneBook.add("Ivanov", "12353");
        phoneBook.add("Petrov", "12335");
        phoneBook.add("Egorov", "34643");
        phoneBook.add("Petrov", "44567");
        phoneBook.add("Petrov", "4732");
        phoneBook.add("Petrov", "4732");

        phoneBook.printBook();

        System.out.println();

        String name = "Petrov";

        System.out.println(name + ": " + phoneBook.get(name));
    }

    public static void createUniqueArray() {
        HashSet<String> uniques = new HashSet<>();
        HashSet<String> duplicates = new HashSet<>();

        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (!uniques.add(data[i])) {
                    duplicates.add(data[i]);
                }
            }
            uniques.removeAll(duplicates);

            for (String str : uniques) {
                System.out.println(str);
            }
        } else {
            System.out.println("Empty data");
        }
    }

    public static void findNumberOfRepeats() {
        food = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            if (food.containsKey(data[i])) {
                food.put(data[i], food.get(data[i]) + 1);
            } else {
                food.put(data[i], 1);
            }
        }
    }

    public static void printMap() {
        for (Map.Entry map : food.entrySet()) {
            System.out.println(map.getKey() + ": " + map.getValue());
        }
    }
}
