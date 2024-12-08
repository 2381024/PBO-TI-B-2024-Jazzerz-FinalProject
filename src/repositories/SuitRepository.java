package repositories;

import entities.Suit;
import java.util.List;

public interface SuitRepository {
    List<Suit> getAllSuits();
    void updateSuit(Suit suit);
    Suit getSuitById(int id);
}
