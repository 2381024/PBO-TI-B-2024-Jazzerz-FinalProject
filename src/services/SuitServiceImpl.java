package services;

import entities.Suit;
import repositories.SuitRepository;

import java.util.List;

public class SuitServiceImpl implements SuitService {
    private final SuitRepository suitRepository;

    public SuitServiceImpl(SuitRepository suitRepository) {
        this.suitRepository = suitRepository;
    }

    @Override
    public List<Suit> getAvailableSuits() {
        return suitRepository.getAllSuits();
    }

    @Override
    public void updateSuit(Suit suit) {
        suitRepository.updateSuit(suit);
    }

    @Override
    public Suit getSuitById(int id) {
        return suitRepository.getSuitById(id);
    }
}
