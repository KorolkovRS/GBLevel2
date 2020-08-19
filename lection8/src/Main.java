public class Main {
    public static void main(String[] args) {
        TwoLinkedList list = new TwoLinkedList();
        System.out.println("size = " + list.getSize());

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        System.out.println("size = " + list.getSize());
        System.out.println(list);

        list.add(3, "10");
        System.out.println(list);
        System.out.println("size = " + list.getSize());

        TwoLinkedList.Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            System.out.println(value);
        }

        list.remove(3);
        System.out.println(list);
        System.out.println("size = " + list.getSize());

        System.out.println("Node index 3 = " + list.get(3));
    }
}
