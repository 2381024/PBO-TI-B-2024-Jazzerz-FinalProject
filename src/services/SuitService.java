package services;

import entities.Suit;

import java.util.List;

public interface SuitService {
    List<Suit> getAvailableSuits();
    void updateSuit(Suit suit);
    Suit getSuitById(int id);
}
