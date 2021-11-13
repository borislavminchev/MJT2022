package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.gift.Priceable;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;

public class DefaultPackingService<T extends Priceable> implements PackingService<T>{
    @Override
    public Gift<T> pack(Person<?> sender, Person<?> receiver, T item) {
        return null;
    }

    @Override
    public Gift<T> pack(Person<?> sender, Person<?> receiver, T... items) {
        return null;
    }

    @Override
    public Collection<T> unpack(Gift<T> gift) {
        return null;
    }
}
