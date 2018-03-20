package com.github.kahlkn.artoria.entity;

import java.util.List;

public class Person {
    private String name;
    private Integer age;
    private Integer sex;
    private Double height;
    private Double weight;
    private List<Person> kinsfolk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public List<Person> getKinsfolk() {
        return kinsfolk;
    }

    public void setKinsfolk(List<Person> kinsfolk) {
        this.kinsfolk = kinsfolk;
    }
}
