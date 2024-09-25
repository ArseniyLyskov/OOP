package ru.nsu.lyskov;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Blackjack implements GameInterface {
    private final String[] cardValues;
    private final String[] cardSuits;
    private final ResourceBundle resourceBundle;

    private final Deck deck = new Deck();
    private final LinkedList<AbstractPlayer> players = new LinkedList<>();
    private final Dealer dealer = new Dealer();
    private final User user = new User();

    private int round = 1;
    private int userScore = 0;
    private int dealerScore = 0;
    private String gameOutputBuffer = "";

    public Blackjack(String localeName) {
        Locale locale = Locale.forLanguageTag(localeName);
        resourceBundle = ResourceBundle.getBundle("localization", locale);

        cardValues = new String[]{
                "",
                resourceBundle.getString("Ace"),
                resourceBundle.getString("Two"),
                resourceBundle.getString("Three"),
                resourceBundle.getString("Four"),
                resourceBundle.getString("Five"),
                resourceBundle.getString("Six"),
                resourceBundle.getString("Seven"),
                resourceBundle.getString("Eight"),
                resourceBundle.getString("Nine"),
                resourceBundle.getString("Ten"),
                resourceBundle.getString("Jack"),
                resourceBundle.getString("King"),
                resourceBundle.getString("Queen")
        };

        cardSuits = new String[]{
                "",
                resourceBundle.getString("Clubs"),
                resourceBundle.getString("Diamonds"),
                resourceBundle.getString("Hearts"),
                resourceBundle.getString("Spades")
        };

        players.add(user);
        players.add(dealer);
    }

    private void startGame() {
        gameOutputBuffer += resourceBundle.getString("Greeting") + "\n";
        deck.addFullDeck();
        deck.shuffle();
    }

    private void startRound() {
        gameOutputBuffer += resourceBundle.getString("Round") + " " + round + "\n";

        dealer.takeCard(deck.removeCard(true));
        dealer.takeCard(deck.removeCard(false));
        user.takeCard(deck.removeCard(true));
        user.takeCard(deck.removeCard(true));

        gameOutputBuffer += resourceBundle.getString("TheCardsAreDealt") + "\n";
        playersHandsOut();
        gameOutputBuffer += "\n" + resourceBundle.getString("YourMove") + "\n" +
                resourceBundle.getString("EnterHint");
    }

    private void move(String decision) {
        outer: switch (decision) {
            case "0":
                gameOutputBuffer += "\n" + resourceBundle.getString("DealersMove") + "\n" +
                        resourceBundle.getString("DealerOpenClosedCard") + " ";
                Card openedCard = dealer.openClosedCard();
                gameOutputBuffer += cardToString(openedCard) + "\n";
                playersHandsOut();
                if (checkGameOver(false))
                    break;
                while (dealer.getScore() < 17) {
                    gameOutputBuffer += "\n" + resourceBundle.getString("DealerOpenCard") + " ";
                    Card takenCard = deck.removeCard(true);
                    dealer.takeCard(takenCard);
                    gameOutputBuffer += cardToString(takenCard) + "\n";
                    playersHandsOut();
                    if (checkGameOver(false))
                        break outer;
                }
                if (checkGameOver(true))
                    break;
                break;
            case "1":
                Card takenCard = deck.removeCard(true);
                user.takeCard(takenCard);
                gameOutputBuffer += resourceBundle.getString("YouOpenedACard") +
                        " " + cardToString(takenCard) + "\n";
                playersHandsOut();
                if (checkGameOver(false))
                    break;
                gameOutputBuffer += "\n" + resourceBundle.getString("EnterHint");
                break;
        }
    }

    private boolean checkGameOver(boolean forceStop) {
        if (user.getScore() >= 21 || (dealer.getScore() >= 21 && dealer.isCardOpened()) || forceStop) {
            boolean youWon = false, dealerWon = false;

            if (user.getScore() == 21 && (!dealer.isCardOpened() || dealer.getScore() != 21)) {
                youWon = true;
            } else if (dealer.getScore() == 21 && user.getScore() != 21) {
                dealerWon = true;
            } else if (user.getScore() == 21 && dealer.getScore() == 21) {
                youWon = true;
                dealerWon = true;
            }

            if (user.getScore() < 21 && (!dealer.isCardOpened() || dealer.getScore() > 21)) {
                youWon = true;
            } else if (dealer.getScore() < 21 && user.getScore() > 21) {
                dealerWon = true;
            }

            if (forceStop) {
                if (user.getScore() == dealer.getScore()) {
                    youWon = true;
                    dealerWon = true;
                } else if (user.getScore() > dealer.getScore()) {
                    youWon = true;
                } else if (user.getScore() < dealer.getScore()) {
                    dealerWon = true;
                }
            }

            gameOutputBuffer += "\n";
            if (youWon && dealerWon) {
                gameOutputBuffer += resourceBundle.getString("DrawRound");
                userScore++;
                dealerScore++;
            } else if (youWon) {
                gameOutputBuffer += resourceBundle.getString("YouWon");
                userScore++;
            } else if (dealerWon) {
                gameOutputBuffer += resourceBundle.getString("YouLost");
                dealerScore++;
            }
            gameOutputBuffer += " " + gameScoreToString();

            round++;
            for (AbstractPlayer player : players)
                player.foldCards();
            startRound();

            return true;
        }
        return false;
    }

    private void playersHandsOut() {
        for (AbstractPlayer player : players) {
            if (player instanceof User)
                gameOutputBuffer += "\t" + resourceBundle.getString("YourCards");
            else if (player instanceof Dealer)
                gameOutputBuffer += "\t" + resourceBundle.getString("DealersCards");
            gameOutputBuffer += " " + handToString(player) + "\n";
        }
    }

    private String flushOutput() {
        String temp = gameOutputBuffer;
        gameOutputBuffer = "";
        return temp;
    }

    private String handToString(AbstractPlayer player) {
        boolean hasClosedCard = false;
        String string = "[";
        for (Card card : player.getHand()) {
            string += cardToString(card) + ", ";
            if (!card.isOpen())
                hasClosedCard = true;
        }
        string = string.substring(0, string.length() - 2);
        string += "]";
        if (!hasClosedCard)
            string += " -> " + player.getScore();
        return string;
    }

    private String cardToString(Card card) {
        String string = "";
        if (card.isOpen())
            string += cardValues[card.getValue()] + " " +
                    cardSuits[card.getSuit()] + " (" +
                    card.getCardScore() + ")";
        else
            string += resourceBundle.getString("ClosedCard");
        return string;
    }

    private String gameScoreToString() {
        String string = userScore + ":" + dealerScore + " ";
        if (userScore > dealerScore)
            string += resourceBundle.getString("YourFavor");
        else if (userScore < dealerScore)
            string += resourceBundle.getString("NotYourFavor");
        else
            string += resourceBundle.getString("DrawFavor");
        string += "\n\n";
        return string;
    }

    @Override
    public void gameInit() {
        startGame();
        startRound();
    }

    @Override
    public void gameInput(String input) {
        move(input);
    }

    @Override
    public void gameOutput() {
        System.out.println(flushOutput());
    }
}
