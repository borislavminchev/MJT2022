package bg.sofia.uni.fmi.mjt.gifts.person;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;

import java.util.*;

public class DefaultPerson<I> implements Person<I> {
    I id;
    List<Gift<?>> receivedGifts;

    public DefaultPerson(I id) {
        this.id = id;
        this.receivedGifts = new ArrayList<>();
    }

    @Override
    public Collection<Gift<?>> getNMostExpensiveReceivedGifts(int n) {
        if (n == 0) {
            return List.copyOf(new ArrayList<>());
        } else if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative");
        } else if (n >= this.receivedGifts.size()) {
            return List.copyOf(this.receivedGifts);
        }

        List<Gift<?>> nMostExpensive = new ArrayList<>();

        Collections.sort(this.receivedGifts, Collections.reverseOrder(new Comparator<Gift<?>>() {
            @Override
            public int compare(Gift<?> o1, Gift<?> o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        }));

        for (int i = 0; i < n; i++) {
            nMostExpensive.add((Gift<?>) this.receivedGifts.toArray()[i]);
        }
        return List.copyOf(nMostExpensive);
    }

    @Override
    public Collection<Gift<?>> getGiftsBy(Person<?> person) {
        Collection<Gift<?>> giftsByPerson = new ArrayList<>();

        for (Gift<?> gift : this.receivedGifts) {
            if (gift.getSender().equals(person)) {
                giftsByPerson.add(gift);
            }
        }

        return List.copyOf(giftsByPerson);
    }

    @Override
    public I getId() {
        return this.id;
    }

    @Override
    public void receiveGift(Gift<?> gift) {
        if (gift == null) {
            throw new IllegalArgumentException("Gift ti be received is null");
        }
        if (!gift.getReceiver().equals(this)) {
            throw new WrongReceiverException("Receiver of gift mismatch");
        }

        this.receivedGifts.add(gift);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultPerson)) return false;
        DefaultPerson<?> that = (DefaultPerson<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
