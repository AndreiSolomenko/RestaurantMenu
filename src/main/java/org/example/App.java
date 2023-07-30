package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add meals");
                    System.out.println("2: add random meals");
                    System.out.println("3: view meals");
                    System.out.println("4: view meals of the specified price range");
                    System.out.println("5: view meals with discount");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addMeals(sc);
                            break;
                        case "2":
                            insertRandomMeals(sc);
                            break;
                        case "3":
                            viewMeals();
                            break;
                        case "4":
                            viewMealsSpecifiedPriceRange(sc);
                            break;
                        case "5":
                            viewMealsWithDiscount();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addMeals(Scanner sc) {
        System.out.print("Enter meals name: ");
        String mealsName = sc.nextLine();
        System.out.print("Enter meals price: ");
        String sPrice = sc.nextLine();
        int price = Integer.parseInt(sPrice);
        System.out.print("Enter meals  weight: ");
        String sWeight = sc.nextLine();
        double weight = Double.parseDouble(sWeight);
        System.out.print("Enter is there a discount (true or false): ");
        String sDiscount = sc.nextLine();
        boolean discount = Boolean.parseBoolean(sDiscount);

        em.getTransaction().begin();
        try {
            SimpleMeals c = new SimpleMeals(mealsName, price, weight, discount);
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomMeals(Scanner sc) {
        System.out.print("Enter quantity of meals: ");
        String sCount = sc.nextLine();
        int count = Integer.parseInt(sCount);

        em.getTransaction().begin();
        try {
            for (int i = 0; i < count; i++) {
                SimpleMeals c = new SimpleMeals(randomMealsName(), RND.nextInt(100),
                                                RND.nextDouble(1), RND.nextBoolean());
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewMeals() {
        Query query = em.createQuery("SELECT c FROM SimpleMeals c", SimpleMeals.class);
        List<SimpleMeals> list = (List<SimpleMeals>) query.getResultList();

        for (SimpleMeals c : list)
            System.out.println(c);
    }

    static final String[] MEALS_NAMES = {"Soup", "Meat", "Garnish", "Salad", "Dessert"};
    static final Random RND = new Random();

    static String randomMealsName() {
        return MEALS_NAMES[RND.nextInt(MEALS_NAMES.length)];
    }



    private static void viewMealsSpecifiedPriceRange(Scanner sc) {
        System.out.print("Enter min price: ");
        String sMinPrice = sc.nextLine();
        int minPrice = Integer.parseInt(sMinPrice);

        System.out.print("Enter max price: ");
        String sMaxPrice = sc.nextLine();
        int maxPrice = Integer.parseInt(sMaxPrice);

        Query query = em.createQuery("SELECT x FROM SimpleMeals x WHERE x.price >= :minPrice and x.price <= :maxPrice", SimpleMeals.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);

        List<SimpleMeals> list = (List<SimpleMeals>) query.getResultList();

        for (SimpleMeals c : list)
            System.out.println(c);
    }

    private static void viewMealsWithDiscount() {
        boolean discount = true;
        Query query = em.createQuery("SELECT x FROM SimpleMeals x WHERE x.discount = :discount", SimpleMeals.class);
        query.setParameter("discount", discount);

        List<SimpleMeals> list = (List<SimpleMeals>) query.getResultList();

        for (SimpleMeals c : list)
            System.out.println(c);
    }

}
