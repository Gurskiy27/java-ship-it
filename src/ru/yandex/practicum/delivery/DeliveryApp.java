package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {


    private static final Scanner scanner = new Scanner(System.in);


    private static List<Parcel> allParcels = new ArrayList<>();


    private static List<Trackable> trackableParcels = new ArrayList<>();


    private static ParcelBox<StandardParcel> standardBox =
            new ParcelBox<>(100);


    private static ParcelBox<FragileParcel> fragileBox =
            new ParcelBox<>(100);


    private static ParcelBox<PerishableParcel> perishableBox =
            new ParcelBox<>(100);


    public static void main(String[] args) {

        boolean running = true;


        while (running) {

            showMenu();

            int choice = Integer.parseInt(scanner.nextLine());


            switch (choice) {

                case 1:
                    addParcel();
                    break;


                case 2:
                    sendParcels();
                    break;


                case 3:
                    calculateCosts();
                    break;


                case 4:
                    trackParcels();
                    break;


                case 5:
                    showBoxContent();
                    break;


                case 0:
                    running = false;
                    break;


                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }


    private static void showMenu() {

        System.out.println();
        System.out.println("Выберите действие:");

        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить посылки");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }


    private static void addParcel() {


        System.out.println(
                "Выберите тип посылки:"
        );

        System.out.println(
                "1 — стандартная"
        );

        System.out.println(
                "2 — хрупкая"
        );

        System.out.println(
                "3 — скоропортящаяся"
        );


        int type = Integer.parseInt(scanner.nextLine());


        System.out.println("Введите описание:");

        String description = scanner.nextLine();


        System.out.println("Введите вес:");

        int weight = Integer.parseInt(scanner.nextLine());


        System.out.println("Введите адрес:");

        String address = scanner.nextLine();


        System.out.println("Введите день отправки:");

        int sendDay = Integer.parseInt(scanner.nextLine());


        switch (type) {


            case 1:

                StandardParcel standardParcel =
                        new StandardParcel(
                                description,
                                weight,
                                address,
                                sendDay
                        );


                allParcels.add(standardParcel);

                standardBox.addParcel(standardParcel);

                break;


            case 2:

                FragileParcel fragileParcel =
                        new FragileParcel(
                                description,
                                weight,
                                address,
                                sendDay
                        );


                allParcels.add(fragileParcel);

                fragileBox.addParcel(fragileParcel);


                trackableParcels.add(fragileParcel);

                break;


            case 3:

                System.out.println(
                        "Введите срок хранения:"
                );


                int timeToLive =
                        Integer.parseInt(scanner.nextLine());


                PerishableParcel perishableParcel =
                        new PerishableParcel(
                                description,
                                weight,
                                address,
                                sendDay,
                                timeToLive
                        );


                allParcels.add(perishableParcel);

                perishableBox.addParcel(perishableParcel);

                break;


            default:

                System.out.println(
                        "Такого типа посылки нет."
                );
        }
    }


    private static void sendParcels() {


        for (Parcel parcel : allParcels) {

            parcel.packageItem();

            parcel.deliver();

        }
    }


    private static void calculateCosts() {


        int totalCost = 0;


        for (Parcel parcel : allParcels) {

            totalCost += parcel.calculateDeliveryCost();

        }


        System.out.println(
                "Общая стоимость доставки: "
                        + totalCost
        );
    }


    private static void trackParcels() {


        if (trackableParcels.isEmpty()) {

            System.out.println(
                    "Нет посылок для отслеживания."
            );

            return;
        }


        System.out.println(
                "Введите новое местоположение:"
        );


        String location = scanner.nextLine();


        for (Trackable parcel : trackableParcels) {

            parcel.reportStatus(location);

        }
    }


    private static void showBoxContent() {


        System.out.println(
                "Выберите коробку:"
        );

        System.out.println(
                "1 — стандартные посылки"
        );

        System.out.println(
                "2 — хрупкие посылки"
        );

        System.out.println(
                "3 — скоропортящиеся посылки"
        );


        int choice =
                Integer.parseInt(scanner.nextLine());


        switch (choice) {


            case 1:

                showParcels(
                        standardBox.getAllParcels()
                );

                break;


            case 2:

                showParcels(
                        fragileBox.getAllParcels()
                );

                break;


            case 3:

                showParcels(
                        perishableBox.getAllParcels()
                );

                break;


            default:

                System.out.println(
                        "Такой коробки нет."
                );
        }
    }


    private static void showParcels(List<? extends Parcel> parcels) {


        if (parcels.isEmpty()) {

            System.out.println(
                    "Коробка пустая."
            );

            return;
        }


        System.out.println(
                "Содержимое коробки:"
        );


        for (Parcel parcel : parcels) {

            System.out.println(
                    "- " + parcel.getDescription()
            );
        }
    }
}