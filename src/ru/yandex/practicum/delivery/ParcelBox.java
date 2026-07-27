package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private int maxWeight;
    private int currentWeight;
    private List<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
    }

    public void addParcel(T parcel) {

        if (currentWeight + parcel.getWeight() > maxWeight) {
            System.out.println(
                    "Нельзя добавить посылку <<"
                            + parcel.getDescription()
                            + ">>. Превышен максимальный вес коробки."
            );
            return;
        }

        parcels.add(parcel);
        currentWeight += parcel.getWeight();
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }
}