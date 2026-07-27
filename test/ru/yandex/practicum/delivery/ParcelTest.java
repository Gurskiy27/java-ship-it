package ru.yandex.practicum.delivery;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {


    // ===== Проверка стоимости доставки =====


    @Test
    void shouldCalculateStandardParcelCost() {

        StandardParcel parcel =
                new StandardParcel(
                        "Книга",
                        10,
                        "Москва",
                        5
                );


        assertEquals(
                20,
                parcel.calculateDeliveryCost()
        );
    }


    @Test
    void shouldCalculateFragileParcelCost() {

        FragileParcel parcel =
                new FragileParcel(
                        "Ваза",
                        5,
                        "Омск",
                        3
                );


        assertEquals(
                20,
                parcel.calculateDeliveryCost()
        );
    }


    @Test
    void shouldCalculatePerishableParcelCost() {

        PerishableParcel parcel =
                new PerishableParcel(
                        "Торт",
                        8,
                        "Казань",
                        10,
                        5
                );


        assertEquals(
                24,
                parcel.calculateDeliveryCost()
        );
    }


    // ===== Проверка isExpired =====


    @Test
    void shouldReturnFalseWhenParcelIsFresh() {

        PerishableParcel parcel =
                new PerishableParcel(
                        "Пирог",
                        2,
                        "Томск",
                        10,
                        5
                );


        // 10 + 5 = 15
        // текущий день 15 => ещё не испортилась

        assertFalse(
                parcel.isExpired(15)
        );
    }


    @Test
    void shouldReturnTrueWhenParcelIsExpired() {

        PerishableParcel parcel =
                new PerishableParcel(
                        "Пирог",
                        2,
                        "Томск",
                        10,
                        5
                );


        // 10 + 5 = 15
        // текущий день 16 => испортилась

        assertTrue(
                parcel.isExpired(16)
        );
    }


    @Test
    void shouldReturnFalseOnExpirationBoundary() {

        PerishableParcel parcel =
                new PerishableParcel(
                        "Молоко",
                        3,
                        "Москва",
                        20,
                        7
                );


        // 20 + 7 = 27
        // граница: день 27 ещё нормально

        assertFalse(
                parcel.isExpired(27)
        );
    }


    // ===== Проверка ParcelBox =====


    @Test
    void shouldAddParcelWhenWeightIsAllowed() {


        ParcelBox<StandardParcel> box =
                new ParcelBox<>(20);


        StandardParcel parcel =
                new StandardParcel(
                        "Документы",
                        10,
                        "Москва",
                        1
                );


        box.addParcel(parcel);


        assertEquals(
                1,
                box.getAllParcels().size()
        );
    }


    @Test
    void shouldNotAddParcelWhenWeightExceeded() {


        ParcelBox<StandardParcel> box =
                new ParcelBox<>(10);


        StandardParcel first =
                new StandardParcel(
                        "Телефон",
                        7,
                        "Москва",
                        1
                );


        StandardParcel second =
                new StandardParcel(
                        "Ноутбук",
                        5,
                        "Москва",
                        1
                );


        box.addParcel(first);

        box.addParcel(second);


        assertEquals(
                1,
                box.getAllParcels().size()
        );
    }


    @Test
    void shouldAddParcelWhenWeightEqualsMaximum() {


        ParcelBox<FragileParcel> box =
                new ParcelBox<>(10);


        FragileParcel parcel =
                new FragileParcel(
                        "Стекло",
                        10,
                        "Омск",
                        2
                );


        box.addParcel(parcel);


        assertEquals(
                1,
                box.getAllParcels().size()
        );
    }
}
