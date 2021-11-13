package bg.sofia.uni.fmi.mjt.gifts.gift;

import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;

public class BirthdayGift <T extends Priceable> implements Gift<T> {
    public BirthdayGift(Person<?> sender, Person<?> receiver, Collection<T> items) {}

    @Override
    public Person<?> getSender() {
        return null;
    }

    @Override
    public Person<?> getReceiver() {
        return null;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public void addItem(T t) {

    }

    @Override
    public boolean removeItem(T t) {
        return false;
    }

    @Override
    public Collection<T> getItems() {
        return null;
    }

    @Override
    public T getMostExpensiveItem() {
        return null;
    }
}
