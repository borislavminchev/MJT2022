package bg.sofia.uni.fmi.mjt.gifts.gift;

import bg.sofia.uni.fmi.mjt.gifts.person.Person;
import com.sun.source.tree.IfTree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BirthdayGift<T extends Priceable> implements Gift<T> {
    Person<?> sender;
    Person<?> receiver;
    Collection<T> items;

    public BirthdayGift(Person<?> sender, Person<?> receiver, Collection<T> items) {
        this.sender = sender;
        this.receiver = receiver;
        this.items = items;
    }

    @Override
    public Person<?> getSender() {
        return this.sender;
    }

    @Override
    public Person<?> getReceiver() {
        return this.receiver;
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (T item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public void addItem(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Cannot add null in item collections");
        }
        this.items.add(t);
    }

    @Override
    public boolean removeItem(T t) {
        return this.items.remove(t);
    }

    @Override
    public Collection<T> getItems() {
        return List.copyOf(this.items);
    }

    @Override
    public T getMostExpensiveItem() {
        T mostExpensive = null;
        for (T item : this.items) {
            if (mostExpensive == null || mostExpensive.getPrice() <= item.getPrice()) {
                mostExpensive = item;
            }
        }
        return mostExpensive;
    }
}
