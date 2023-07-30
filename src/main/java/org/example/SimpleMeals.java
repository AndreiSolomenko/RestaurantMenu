package org.example;

import javax.persistence.*;

@Entity
@Table(name="SimpleMeals")
public class SimpleMeals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="myid")
    private long id;

    @Column(nullable = false)
    private String mealsName;

    private int price;

    private double weight;

    private boolean discount;

    public SimpleMeals() {}

    public SimpleMeals(String mealsName, int price, double weight, boolean discount) {
        this.mealsName = mealsName;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMealsName() {
        return mealsName;
    }

    public void setMealsName(String mealsName) {
        this.mealsName = mealsName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean getDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SimpleMeals{" +
                "id=" + id +
                ", meals name='" + mealsName + '\'' +
                ", price=" + price + '\'' +
                ", weight=" + weight + '\'' +
                ", discount=" + discount +
                '}';
    }

}
