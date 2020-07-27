import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {
    private Map<String, HashSet<String>> phoneBook = new TreeMap<>();
    HashSet<String> phones;

    public void add(String name, String number) {
        if (phoneBook.containsKey(name)) {
            phoneBook.get(name).add(number);
        } else {
            phones = new HashSet<>();
            phones.add(number);
            phoneBook.put(name, phones);
        }
    }

    public HashSet<String> get(String name) {
        return phoneBook.get(name);
    }

    public void printBook() {
        if (!phoneBook.isEmpty()) {
            for (Map.Entry map : phoneBook.entrySet()) {
                System.out.printf("%-10s|%s\n", map.getKey(), map.getValue());
            }
        } else {
            System.out.println("Phone book is empty");
        }
    }
}
