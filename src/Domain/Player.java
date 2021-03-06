package Domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public StrategyInterface strat;
    private final int ID;
    private List<Integer> cards = new ArrayList<>();
    private int sum;
    private int treshold;
    private int wins = 0;
    private int face = 0;
    private boolean isDealer = false;
    private static final SecureRandom randomNumber = new SecureRandom();
    private double chips = 0;
    private double bet;
    private Shoe shoe;

    public Player(int ID, int strategy, Shoe shoe) {
        this.ID = ID;
        this.shoe = shoe;
        setStrategy(strategy);

    }

    public Player(int ID, int treshold, int strategy, Shoe shoe) {
        this.ID = ID;
        this.treshold = treshold;
        this.shoe = shoe;
        setStrategy(strategy);

    }

    public Player(int ID, boolean isDealer) {
        this.ID = ID;
        this.isDealer = isDealer;
        setStrategy(20);
    }

    private void setStrategy(int strategy) {
        if (!isDealer()) {
            switch (strategy) {
                case 1:
                    this.strat = new StrategyTreshold();
                    break;
                case 2:
                    this.strat = new StrategyGoForBlackjack();
                    break;
                case 3:
                    this.strat = new StrategyNeverHit();
                    break;
                case 4:
                    this.strat = new StrategyBasic();
                    break;
                case 5:
                    this.strat = new StrategyLearning();
                    break;
                case 6:
                    this.strat = new StrategyAdvancedLearning(shoe);
                    break;
                default:
                    this.strat = new StrategyDefault();
                    break;
            }
        } else {
            this.strat = new StrategyDefault();
        }
    }

    public int getTreshold() {
        int treshReturn;
        if (treshold < 0) {
            treshReturn = Math.abs(treshold) + randomNumber.nextInt(21 - treshold);
        } else {
            treshReturn = treshold;
        }
        return treshReturn;
    }

    public List<Integer> getCards() {
        return cards;
    }

    public boolean wantCard() {
        if (getCardTotal() < 21) {
            return strat.wantCard(this);
        } else {
            return false;
        }
    }

    public void addCard(int card) {
        cards.add(card);
    }

    public int getCardTotal() {
        sum = 0;
        int aantalAzen = 0;
        for (int waarde : cards) {
            if (waarde == 1) {
                sum += 11;
                aantalAzen++;
            } else {
                sum += waarde;
            }
        }
        while (aantalAzen > 0 && sum > 21) {
            sum -= 10;
            aantalAzen--;
        }
        return sum;
        /*
         for (int waarde : cards) {
         if (waarde == 1) {
         sum += 11;
         } else {
         sum += waarde;
         }
         }
         if (sum > 21) {
         sum = 0;
         for (int waarde : cards) {
         sum += waarde;
         }
         return sum;
         } else {
         return sum;
         }
         */
    }

    public void nextRound() {
        sum = 0;
        cards.clear();
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void isWinner() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }

    public int getID() {
        return ID;
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public void setBet(Shoe shoe) {
        if (shoe.getTrueCount() > 0) {
            bet = shoe.getTrueCount();
        } else {
            bet = 0;
        }
        chips = chips - bet;
    }

    public double getBet() {
        return bet;
    }

    public void addChips(double wonChips) {
        chips = chips + wonChips;
        bet = 0;
    }

    public void loseChips() {
        bet = 0;
    }

    public double getChips() {
        return chips;
    }

}
