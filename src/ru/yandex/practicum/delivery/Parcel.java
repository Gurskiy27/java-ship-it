package ru.yandex.practicum.delivery;

public abstract class Parcel {

    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    protected void packageParcel() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    public abstract void packageItem();

    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    public int calculateDeliveryCost() {
        return weight * getBaseCost();
    }

    protected abstract int getBaseCost();

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getSendDay() {
        return sendDay;
    }
}
