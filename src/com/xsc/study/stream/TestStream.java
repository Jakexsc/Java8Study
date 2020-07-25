package com.xsc.study.stream;


import com.xsc.study.pojo.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Jakexsc
 * @date : 2020-07-25 21:02
 **/
public class TestStream {
    public static void main(String[] args) {
        List<Dish> menus = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        // 第一次测试stream
        List<String> menu1 = menus.parallelStream()
                // 筛选卡路里大于300的菜单
                .filter(dish -> dish.getCalories() > 300)
                // 取出菜名
                .map(Dish::getName)
                // 只选出头三个
                .limit(3)
                // 放入集合
                .collect(toList());
//        System.out.println(menu1);


        // 测试stream skip
        List<String> menu2 = menus.parallelStream()
                // 筛选卡路里大于300的菜单
                .filter(dish -> dish.getCalories() > 300)
                // 取出菜名
                .map(Dish::getName)
                // 忽略2个
                .skip(2)
                // 放入集合
                .collect(toList());
        System.out.println(menu2);


        // 每个菜名的长度
        List<Integer> nameLength = menus.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println(nameLength);

        // flatMap测试 flatMap 让流扁平化
        List<String> words = Arrays.asList("Hello", "world");
        List<String> word = words.stream()
                // 将每个单词转换为由其字母构成的数组
                .map(s -> s.split(""))
                // 为每个字符转换成单个流
                .flatMap(Arrays::stream)
                // 去重
                .distinct()
                // 添加到新的数组
                .collect(toList());
        System.out.println(word);

        /**
         * 返回一个每个数的平方的列表
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> number = numbers.stream()
                .map(i -> i * i)
                .collect(toList());
        System.out.println(number);

        /**
         * 给两个列表 返回一个所有成对的列表
         */
        List<Integer> testNumber1 = Arrays.asList(1, 2, 3);
        List<Integer> testNumber2 = Arrays.asList(4, 5);
        testNumber1.stream()
                .flatMap(i -> testNumber2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(toList());

        /**
         * 在上面的例子上 筛选出能总和被3整除的
         */
        testNumber1.stream()
                .flatMap(i -> testNumber2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());
    }
}
