package com.xsc.study.stream;


import com.xsc.study.pojo.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

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
        System.out.println(menu1);


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

        // flatMap测试 flatMap 让流扁平化 => 映射
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

        // 测试reduce => 归约
        Integer reduce1 = numbers.stream().reduce(0, (a, b) -> a + b);
        Optional<Integer> reduce = numbers.stream().reduce(Integer::sum);
        System.out.println(reduce);

        /**
         * 付诸实践
         *
         * (1)找出2011年发生的所有交易，并按交易额排序
         * (2)交易员都在哪些城市工作过
         * (3)查找所有来自于剑桥的交易员,并按姓名排序
         * (4)返回所有交易员的姓名字符串,并按姓名排序
         * (5)有没有交易员是在米兰工作的
         * (6)打印生活在剑桥的交易员的所有交易额
         * (7)所有交易中,最高的交易额是多少?
         * (8)找到交易额最小的交易
         */
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 700),
                new Transaction(mario, 2012, 710),
                new Transaction(alan, 2012, 950)
        );
        // (1)找出2011年发生的所有交易，并按交易额排序
        List<Transaction> testTransaction1 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(testTransaction1);

        //(2)交易员都在哪些城市工作过
        Set<String> testTransaction2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet());
        System.out.println(testTransaction2);

        // (3)查找所有来自于剑桥的交易员,并按姓名排序
        List<String> testTransaction3 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(toList());
        System.out.println(testTransaction3);

        // (4)返回所有交易员的姓名字符串,并按姓名排序
        String testTransaction4 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining());
        System.out.println(testTransaction4);

        // (5)有没有交易员是在米兰工作的
        boolean testTransaction5 = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(testTransaction5);

        // (6)打印生活在剑桥的交易员的所有交易额
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getValue())
                .forEach(System.out::println);
        // (7)所有交易中,最高的交易额是多少?
        Optional<Transaction> testTransaction7 = transactions.stream()
                .max(comparing(Transaction::getValue));
        System.out.println(testTransaction7);

        // (8)找到交易额最小的交易
        Optional<Transaction> testTransaction8 = transactions.stream()
                .min(comparing(Transaction::getValue));
        System.out.println(testTransaction8);

        // 映射成int类 避免装箱操作
        int mapIntSum = menus.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println(mapIntSum);

        // IntStream.rangeClosed实践统计出1-100的偶数数量
        long count = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println(count);
    }

}
