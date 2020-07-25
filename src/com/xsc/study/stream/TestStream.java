package com.xsc.study.stream;


import com.xsc.study.pojo.Dish;

import java.util.Arrays;
import java.util.List;

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
        List<String> menu1 = menus.parallelStream()
                // 筛选卡路里大于300的菜单
                .filter(dish -> dish.getCalories() > 300)
                // 取出菜名
                .map(Dish::getName)
                // 只选出头三个
                .limit(3)
                // 放入集合
                .collect(toList());
        List<String> menu2 = menus.parallelStream()
                // 筛选卡路里大于300的菜单
                .filter(dish -> dish.getCalories() > 300)
                // 取出菜名
                .map(Dish::getName)
                // 忽略2个
                .skip(2)
                // 放入集合
                .collect(toList());
        System.out.println(menu1);
        System.out.println(menu2);
    }
}
