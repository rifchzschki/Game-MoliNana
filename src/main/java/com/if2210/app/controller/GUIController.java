package com.if2210.app.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import javafx.scene.Group;
import com.if2210.app.model.CardModel;



public class GUIController {
    public static final String BLANK_IMAGE = "/com/if2210/app/assets/blank.png";

    @FXML
    private Group activeDeckGroup;

    @FXML
    private Group fieldCardGroup;

    public List<AnchorPane> activeDecks = new ArrayList<>();
    public List<AnchorPane> fieldCards = new ArrayList<>();

    @FXML
    public void initialize() {
        initializeDecks(activeDeckGroup, activeDecks, 6);
        initializeDecks(fieldCardGroup, fieldCards, 20);

        setupDragAndDrop();
    }

    private void initializeDecks(Group deckGroup, List<AnchorPane> decks, int count) {
        for (int i = 0; i < count; i++) {
            try {
                AnchorPane anchorPane = (AnchorPane) deckGroup.getChildren().get(i);
                if (!anchorPane.getChildren().isEmpty()) {
                    AnchorPane deck = (AnchorPane) anchorPane.getChildren().get(0);
                    decks.add(deck);
                    CardModel cardData = new CardModel("", "", BLANK_IMAGE);
                    updateCard(deck, cardData);
                }
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    private void setupDragAndDrop() {
        for (AnchorPane activeDeck : activeDecks) {
            setDragDetected(activeDeck);
        }

        for (AnchorPane activeCardField : fieldCards) {
            setDragDetected(activeCardField);
            setDragOver(activeCardField);
            setDragDropped(activeCardField);
        }
    }

    private void setDragDetected(AnchorPane deck) {
        deck.setOnDragDetected(event -> {
            System.out.println("Drag Detected");
            Dragboard dragboard = deck.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(deck.getId());
            dragboard.setContent(content);
            event.consume();
        });
    }

    private void setDragOver(AnchorPane deck) {
        deck.setOnDragOver(event -> {
            if (event.getGestureSource() != deck && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
    }

    private void setDragDropped(AnchorPane targetCard) {
        targetCard.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String sourceCardId = dragboard.getString();
                System.out.println("Dropped from " + sourceCardId + " to " + targetCard.getId());
                AnchorPane sourceCard = findDeckById(sourceCardId);
                if (sourceCard != null) {
                    CardModel sourceCardData = (CardModel) sourceCard.getUserData();
                    if (!sourceCardData.getImage().equals(BLANK_IMAGE)) {
                        if (targetCard.getUserData() != null) {
                            CardModel targetCardData = (CardModel) targetCard.getUserData();
                            updateCard(sourceCard, targetCardData);
                            updateCard(targetCard, sourceCardData);
                            success = true;
                        }
                    }
                    else {
                        System.err.println("Empty Card");
                    }
                }
                else {
                    System.err.println("Source ActiveDeck not found");
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public AnchorPane findDeckById(String id) {
        for (AnchorPane deck : activeDecks) {
            if (deck.getId().equals(id)) {
                return deck;
            }
        }
        for (AnchorPane deck : fieldCards) {
            if (deck.getId().equals(id)) {
                return deck;
            }
        }
        return null;
    }

    public void updateCard(AnchorPane card, CardModel cardData) {
        card.setUserData(new CardModel(cardData.getColor(), cardData.getName(), cardData.getImage()));

        ImageView imageView = (ImageView) card.getChildren().get(0);
        Image image = new Image(getClass().getResourceAsStream(cardData.getImage()));
        imageView.setImage(image != null ? image : new Image(BLANK_IMAGE)); // Use blank image if resource not found
        Label label = (Label) card.getChildren().get(1);
        label.setText(cardData.getName());

        card.setStyle(null);
        // Update AnchorPane background color based on the color attribute of the card model
        String color = cardData.getColor();
        if (color != null && !color.isEmpty()) {
            card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 7.7px;");
        }
    }

    public void clearCard(AnchorPane source) {
        CardModel emptyData = new CardModel("", "", BLANK_IMAGE);
        source.setUserData(emptyData);
        updateCard(source, emptyData);
    }
}