package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private int maxWeight;

    private List<T> parcels = new ArrayList<>();


    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }


    public void addParcel(T parcel) {

        int currentWeight = 0;

        for (T item : parcels) {
            currentWeight += item.getWeight();
        }


        if (currentWeight + parcel.getWeight() > maxWeight) {

            System.out.println(
                    "Нельзя добавить посылку <<"
                            + parcel.getDescription()
                            + ">>. Превышен максимальный вес коробки."
            );

            return;
        }


        parcels.add(parcel);
    }


    public List<T> getAllParcels() {

        return new ArrayList<>(parcels);
    }
}
