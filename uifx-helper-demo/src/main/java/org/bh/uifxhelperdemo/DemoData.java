package org.bh.uifxhelperdemo;

import java.util.ArrayList;
import java.util.List;

public class DemoData {

    public static List<Person> getRandomPerson(int count) {
        List<Person> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Person p = new Person("name" + i, (int) (Math.random() * 45));
            p.setDeleted((int) (Math.random() * 45) % 3 == 0);
            result.add(p);
        }

        return result;
    }
}
