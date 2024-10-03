package ru.nsu.lyskov;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс игры блэкджек. Описывает правила игры, инициализирует нужные данные,
 * реализует интерфейс, предоставляющий методы для управления пользователю.
 */
public class Blackjack implements GameInterface {
    /**
     * cardValues, cardSuits - массивы строковых названий
     * ценностей и мастей соответствующих карт.
     */
    private final String[] cardValues;
    private final String[] cardSuits;
    private final ResourceBundle resourceBundle;

    /**
     * Основные сущности игры: колода карт, игроки (на данном этапе
     * разработки только дилер и пользователь софта).
     */
    private final Deck deck = new Deck();
    private final LinkedList<AbstractPlayer> players = new LinkedList<>();
    private final Dealer dealer = new Dealer();
    private final User user = new User();

    /**
     * Номер играемого раунда, количество побед пользователя,
     * количество побед дилера, строка вывода.
     */
    private int round = 1;
    private int userScore = 0;
    private int dealerScore = 0;
    private String gameOutputBuffer = "";

    /**
     * Конструктор, инициализирует локализацию.
     * Добавляет минимальный набор игроков: пользователя и дилера.
     *
     * @param localeName Название локали, на которой необходимо запустить
     *                   игру. По умолчанию en.
     */
    public Blackjack(String localeName) {
        Locale locale = Locale.forLanguageTag(localeName);
        resourceBundle = ResourceBundle.getBundle("localization", locale);

        cardValues = new String[]{ "",
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

        cardSuits = new String[]{ "",
                resourceBundle.getString("Clubs"),
                resourceBundle.getString("Diamonds"),
                resourceBundle.getString("Hearts"),
                resourceBundle.getString("Spades")
        };

        players.add(user);
        players.add(dealer);
    }

    /**
     * Запуск игры: Приветствие, добавление колоды, её тасовка.
     */
    private void startGame() {
        gameOutputBuffer += resourceBundle.getString("Greeting") + "\n";
        deck.addFullDeck();
        deck.shuffle();
    }

    /**
     * Начало раунда: Вывод номера раунда, начальная раздача карт.
     */
    private void startRound() {
        gameOutputBuffer += resourceBundle.getString("Round") + " " + round + "\n";

        user.takeCard(deck.removeCard(true));
        dealer.takeCard(deck.removeCard(true));
        user.takeCard(deck.removeCard(true));
        dealer.takeCard(deck.removeCard(false));

        gameOutputBuffer += resourceBundle.getString("TheCardsAreDealt") + "\n";
        playersHandsOut();
        gameOutputBuffer += "\n" + resourceBundle.getString("YourMove") + "\n"
                + resourceBundle.getString("EnterHint");
    }

    /**
     * Процедура реакции игры в зависимости от введённой пользователем строки.
     *
     * @param decision Строка, введённая пользователем.
     *                 "0" означает конец хода пользователя,
     *                 "1" означает взятие очередной карты.
     */
    private void move(String decision) {
        outer:
        switch (decision) {
            /*
              Если пользователь заканчивает ход, ходит дилер. Его первый
              ход - открытие закрытой карты (второй). Далее, пока сумма его очков
              менее 17, дилер берёт по карте.
             */
            case "0":
                gameOutputBuffer += "\n" + resourceBundle.getString("DealersMove") + "\n"
                        + resourceBundle.getString("DealerOpenClosedCard") + " ";
                Card openedCard = dealer.openClosedCard();
                gameOutputBuffer += cardToString(openedCard) + "\n";
                playersHandsOut();
                if (checkGameOver(false)) {
                    break;
                }
                while (dealer.getScore() < 17) {
                    gameOutputBuffer += "\n" + resourceBundle.getString("DealerOpenCard") + " ";
                    Card takenCard = deck.removeCard(true);
                    dealer.takeCard(takenCard);
                    gameOutputBuffer += cardToString(takenCard) + "\n";
                    playersHandsOut();
                    if (checkGameOver(false)) {
                        break outer;
                    }
                }
                /*
                  Когда дилер взял карт суммарно на не менее 17 очков, но не случилось перебора или
                  блэкджека, раунд всё равно необходимо завершить и сделать вывод о победителе
                  на основании того, кто набрал больше очков.
                 */
                if (checkGameOver(true)) {
                    break;
                }
                break;
            /*
              Обработка случая, когда пользователь берёт следующую карту.
              */
            case "1":
                Card takenCard = deck.removeCard(true);
                user.takeCard(takenCard);
                gameOutputBuffer += resourceBundle.getString("YouOpenedACard")
                        + " " + cardToString(takenCard) + "\n";
                playersHandsOut();
                if (checkGameOver(false)) {
                    break;
                }
                gameOutputBuffer += "\n" + resourceBundle.getString("EnterHint");
                break;
            default:
                throw new RuntimeException("Wrong input");
        }
    }

    /**
     * Проверка окончания раунда: анализ количества очков
     * у игроков, старт нового раунда.
     *
     * @param forceStop Флаг, указывающий на необходимость закончить раунд,
     *                  даже если никто не набрал 21 или более очков. Происходит
     *                  в ситуации, когда дилер в свой ход набрал >= 17 и <= 20 очков.
     * @return Возвращает true, если раунд завершён.
     */
    private boolean checkGameOver(boolean forceStop) {
        if (user.getScore() >= 21
                || (dealer.getScore() >= 21 && dealer.isCardOpened())
                || forceStop) {
            boolean youWon = false;
            boolean dealerWon = false;

            if (user.getScore() == 21 && (!dealer.isCardOpened() || dealer.getScore() != 21)) {
                youWon = true;
            } else if (dealer.getScore() == 21 && user.getScore() != 21) {
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
            for (AbstractPlayer player : players) {
                player.foldCards();
            }
            startRound();

            return true;
        }
        return false;
    }

    /**
     * Функция, выводящая в буферную строку о доступной пользователю
     * информации о картах на руках игроков.
     */
    private void playersHandsOut() {
        for (AbstractPlayer player : players) {
            if (player instanceof User) {
                gameOutputBuffer += "\t" + resourceBundle.getString("YourCards");
            } else if (player instanceof Dealer) {
                gameOutputBuffer += "\t" + resourceBundle.getString("DealersCards");
            }
            gameOutputBuffer += " " + handToString(player) + "\n";
        }
    }

    /**
     * Функция, возвращающая текст вывода игры и очищающая буферную строку.
     *
     * @return Возвращает текст вывода игры.
     */
    private String flushOutput() {
        String temp = gameOutputBuffer;
        gameOutputBuffer = "";
        return temp;
    }

    /**
     * Функция, возвращающая строку с доступной пользователю
     * информацией о руке конкретного игрока.
     *
     * @param player Игрок, информацию о руке которого нужно получить.
     * @return Строка с доступной пользователю информацией о картах игрока.
     */
    private String handToString(AbstractPlayer player) {
        boolean hasClosedCard = false;
        String string = "[";
        for (Card card : player.getHand()) {
            string += cardToString(card) + ", ";
            if (!card.isOpen()) {
                hasClosedCard = true;
            }
        }
        string = string.substring(0, string.length() - 2);
        string += "]";
        if (!hasClosedCard) {
            string += " -> " + player.getScore();
        }
        return string;
    }

    /**
     * Функция, возвращающая информацию о карте в строковом виде.
     *
     * @param card Карта, информацию о которой необходимо получить.
     * @return Возвращает локализованную строку с информацией о карте.
     */
    private String cardToString(Card card) {
        String string = "";
        if (card.isOpen()) {
            string += cardValues[card.getValue()] + " "
                    + cardSuits[card.getSuit()] + " ("
                    + card.getCardScore() + ")";
        } else {
            string += resourceBundle.getString("ClosedCard");
        }
        return string;
    }

    /**
     * Функция, возвращающая информацию о счёте игры.
     *
     * @return Возвращает строку с информацией о счёте игры.
     */
    private String gameScoreToString() {
        String string = userScore + ":" + dealerScore + " ";
        if (userScore > dealerScore) {
            string += resourceBundle.getString("YourFavor");
        } else if (userScore < dealerScore) {
            string += resourceBundle.getString("NotYourFavor");
        } else {
            string += resourceBundle.getString("DrawFavor");
        }
        string += "\n\n";
        return string;
    }

    /**
     * Инициализация игры: добавление игроков, первой колоды, тасовка.
     * Старт первого раунда.
     */
    @Override
    public void gameInit() {
        startGame();
        startRound();
    }

    /**
     * Действие пользователя. Ход на основании полученной от него строки.
     *
     * @param input Строка с выбором игрока, закончить ход или продолжить.
     *              ("0" и "1" соответственно).
     */
    @Override
    public void gameInput(String input) {
        move(input);
    }

    /**
     * Вывод происходящего в игре через консоль.
     */
    @Override
    public void gameOutput() {
        System.out.println(flushOutput());
    }
}
