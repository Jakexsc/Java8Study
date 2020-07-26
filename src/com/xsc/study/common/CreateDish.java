package com.xsc.study.common;

import com.xsc.study.pojo.Dish;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jakexsc
 * @date : 2020-07-26 14:06
 **/
public class CreateDish {
    public static List<Dish> getListDish() {
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
        return menus;
    }
}
