package Domain;

public class GoForBlackjackStrategy implements StrategyInterface {

    @Override
    public boolean wantCard(Player player) {
        return player.getCardTotal() < 21;
    }
}