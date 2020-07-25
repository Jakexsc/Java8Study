package com.xsc.study.pojo;

/**
 * @author Jakexsc
 * @date : 2020-07-25 20:53
 **/
public class Dish {
    /**
     * 名字
     */
    private final String name;
    /**
     * 素食者
     */
    private final boolean vegetarian;
    /**
     * 卡路里
     */
    private final int calories;
    /**
     * 类型
     */
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }
    public enum Type {
        MEAT, FISH, OTHER
    }

    @Override
    public String toString() {
        return name;
    }
}
