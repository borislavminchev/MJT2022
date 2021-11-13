package bg.sofia.uni.fmi.mjt.gifts.person;

import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;

import java.util.Collection;

public class DefaultPerson<I> implements Person<I>{
    public DefaultPerson(I id) {}

    @Override
    public Collection<Gift<?>> getNMostExpensiveReceivedGifts(int n) {
        return null;
    }

    @Override
    public Collection<Gift<?>> getGiftsBy(Person<?> person) {
        return null;
    }

    @Override
    public I getId() {
        return null;
    }

    @Override
    public void receiveGift(Gift<?> gift) {

    }
}
