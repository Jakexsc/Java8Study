package com.xsc.study.stream;

import com.xsc.study.common.Const;
import com.xsc.study.pojo.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.xsc.study.common.CreateDish.getListDish;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

/**
 * @author Jakexsc
 * @date : 2020-07-26 14:04
 **/
public class TestGrouping {

    public enum CaloricLevel {
        DIET, FAT, NORMAL
    }

    public static void main(String[] args) {
        List<Dish> menus = getListDish();
        /**
         * 第一个groupingBy 根据菜的类型分组
         * 第二个groupingBy 根据卡路里分组
         */
        menus.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
            if (dish.getCalories() <= Const.DIET) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= Const.NORMAL) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        }))).forEach((caloricLevel, dishes) -> System.out.println(caloricLevel + "=" + dishes));

        /**
         * 找出素食者和非素食者的菜
         * 实践分区
         * partitioningBy 必须接受一个谓词(一个返回boolean类型的函数) 只可以分为两组 true和false
         */
        Map<Boolean, Map<Dish.Type, List<Dish>>> partitioningBy = menus.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println(partitioningBy);

        /**
         * 找出素食者和非素食中卡路里最高的菜
         * collectingAndThen测试 转换函数返回的类型
         */
        Map<Boolean, Dish> collectingAndThen = menus.stream()
                .collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(comparing(Dish::getCalories)), Optional::get)));
        System.out.println(collectingAndThen);
    }
}
