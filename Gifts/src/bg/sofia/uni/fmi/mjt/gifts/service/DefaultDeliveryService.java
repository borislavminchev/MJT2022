package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

public class DefaultDeliveryService implements DeliveryService {
    @Override
    public void send(Person<?> receiver, Gift<?> gift) {
        if (receiver == null || gift == null) {
            throw new IllegalArgumentException("Error. Receiver or gift is null.");
        }

        if (!receiver.equals(gift.getReceiver())) {
            throw new WrongReceiverException("@receiver is different from the receiver of the gift");
        }

        receiver.receiveGift(gift);
    }
}
