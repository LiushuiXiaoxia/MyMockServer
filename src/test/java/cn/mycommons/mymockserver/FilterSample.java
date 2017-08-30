package cn.mycommons.mymockserver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * FilterTest <br/>
 * Created by Leon on 2017-08-29.
 */
public class FilterSample {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Optional<Integer> first = list.stream()
                .filter(integer -> integer % 2 == 0)
                .filter(integer -> integer % 3 == 0)
                .findFirst();

        first.ifPresent(System.out::println);
    }
}