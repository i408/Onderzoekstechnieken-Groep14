package Domain;

public class TresholdStrategy implements StrategyInterface {
    
    @Override
    public boolean wantCard(Player player){
        return player.getCardTotal() < player.getTreshold();
    }
}
