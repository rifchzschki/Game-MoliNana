package com.if2210.app.model;

public class PlayerModel {
    private FieldModel field;
    private ActiveDeckModel activeDeck;
    private DeckModel deck;
    private int money;

    public FieldModel getField() {
        return field;
    }

    public void setField(FieldModel field) {
        this.field = field;
    }

    public ActiveDeckModel getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(ActiveDeckModel activeDeck) {
        this.activeDeck = activeDeck;
    }

    public DeckModel getDeck() {
        return deck;
    }

    public void setDeck(DeckModel deck) {
        this.deck = deck;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
