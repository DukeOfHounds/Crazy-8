/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazy8;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

/**
 *
 * @author DukeOfHounds
 */
public class Crazy8 extends JFrame implements ActionListener {

    /**
     * *************************************************************
     * Project Number 3 - Comp Sci 182 - Data Structures (w/ Swing) Start Code -
     * Build your program starting with this code Card Game Copyright 2005-2016
     * Christopher C. Ferguson This code may only be used with the permission of
     * Christopher C. Ferguson
     * *************************************************************
     */
    private static int winxpos = 0, winypos = 0;      // place window here

    private JButton drawCardButton, exitButton, playCardButton;
    private CardList dealersHand = new CardList(0);
    private CardList yourHand = new CardList(0);
    private CardList inGame = new CardList(0);
    private CardList deck = null;
    private JPanel northPanel;
    private JTextField cardField;
    private JTextArea outputArea;
    private boolean gameOver = false;

    private MyPanel centerPanel;
    private static JFrame myFrame = null;
    private Random rand = new Random();
    private Exception exception = null;

    ////////////              MAIN      ////////////////////////
    public static void main(String[] args) {
        Crazy8 tpo = new Crazy8();
    }

    ////////////            CONSTRUCTOR   /////////////////////
    public Crazy8() {
        myFrame = this;// need a static variable reference to a JFrame object
        northPanel = new JPanel();
        northPanel.setBackground(Color.gray);
        //^- Panelling of the display window

        drawCardButton = new JButton("Draw Card");
        northPanel.add(drawCardButton);
        drawCardButton.addActionListener(this);
        //^-Draw card Button

        playCardButton = new JButton("Play Card");
        northPanel.add(playCardButton);
        playCardButton.addActionListener(this);
        //^- Button that plays card in the CardField

        northPanel.add(new JLabel("Card: "));
        cardField = new JTextField("", 10);
        northPanel.add(cardField);
        //^-Field to enter an INT for which card you want to play

        exitButton = new JButton("Exit");
        northPanel.add(exitButton);
        exitButton.addActionListener(this);
        //^- Button that Exits the application.

        getContentPane().add("North", northPanel);
        centerPanel = new MyPanel();
        getContentPane().add("Center", centerPanel);
        //^- space where cards appear

        deck = new CardList(52);
        deck.shuffle();
        for (int i = 0; i < 7; i++) {
            dealersHand.insertCard(deck.deal());
            yourHand.insertCard(deck.deal());
        }
        inGame.insertCard(deck.deal());
        //^- basic start condition
        setSize(800, 900);
        setLocation(winxpos, winypos);
        setVisible(true);
        //^-window configuration.
    }

    ////////////   BUTTON CLICKS ///////////////////////////
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            dispose();
            System.exit(0);
        }
        if (e.getSource() == drawCardButton && gameOver == false) {
            yourHand.insertCard(deck.deal());
            dealersTurn();
            repaint();
        }
        if (e.getSource() == playCardButton&& gameOver == false) {
            boolean played = false;
            int cardnum = 0;
            try {//string to int conversion
                cardnum = Integer.parseInt(cardField.getText());
            } catch (Exception ex) {//player protection
                exception = ex;
            }
            if (exception == null && cardnum <= yourHand.getNumCards()
                    && cardnum > 0 && isPlayable(yourHand.getCardAt(cardnum))) {
                //valid number and playable card.
                inGame.insertCard(yourHand.deleteCard(cardnum - 1));
                played = true;
            }
            exception = null;
            if (played) {
                dealersTurn();
            }
            repaint();

        }
    }

    ///////////   Winner?!?!?   ///////////////////////////
    public boolean winner(CardList hand) {
        if (hand.getFirstCard() == null) {
            return true;
        } else {
            return false;
        }
    }

    ///////////   Dealer's Turn //////////////////////////
    public void dealersTurn() {

        Card curr = dealersHand.getFirstCard();
        boolean played = false;
        int cardpos = 0;
        while (curr != null) { //finds first playble card and plays it. 
            if (isPlayable(curr)) {
                inGame.insertCard(dealersHand.deleteCard(cardpos));
                //^- plays a card.
                played = true;
                break;
            }
            cardpos++;
            curr = curr.getNextCard();
        }
        if (played == false) {
            dealersHand.insertCard(deck.deal());
        }

    }

    //////////// is playable? /////////////////////////
    public boolean isPlayable(Card inHand) {
        if (inHand.getCardRank() == 8) {
            return true;
        } else if (inGame.getFirstCard().getSuit() == inHand.getSuit()
                || inGame.getFirstCard().getCardRank() == inHand.getCardRank()) {
            return true;
        } else {
            return false;
        }
    }
// This routine will load an image into memory
//

    public static Image load_picture(String fname) {
        // Create a MediaTracker to inform us when the image has
        // been completely loaded.
        Image image;
        MediaTracker tracker = new MediaTracker(myFrame);

        // getImage() returns immediately.  The image is not
        // actually loaded until it is first used.  We use a
        // MediaTracker to make sure the image is loaded
        // before we try to display it.
        image = myFrame.getToolkit().getImage(fname);

        // Add the image to the MediaTracker so that we can wait
        // for it.
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        if (tracker.isErrorID(0)) {
            image = null;
        }
        return image;
    }
// --------------   end of load_picture ---------------------------

    class MyPanel extends JPanel {

        ////////////    PAINT   ////////////////////////////////
        public void paintComponent(Graphics g) {
            int xpos = 25, ypos = 15;
            if (winner(yourHand)) {
                getContentPane().remove(centerPanel);
                g.drawString("You have Won!!! and the game is "
                        + "over.", xpos, ypos);
                gameOver = true;
            }
            if (winner(dealersHand)) {
                getContentPane().remove(centerPanel);
                g.drawString("The Dealer has won and you have Lost!!! "
                        + "The game is over.", xpos, ypos);
                gameOver = true;
            }
            if (gameOver == false) {
                Card current = dealersHand.getFirstCard();
                g.drawString("Dealer's Hand", xpos, ypos);
                //^- displays above dealer's cards
                ypos += 20;

                while (current != null) {

                    Image tempimage = current.getCardImage();
                    g.drawImage(tempimage, xpos, ypos, this);
                    // note: tempimage member variable must be set BEFORE paint is called
                    xpos += 80;
                    if (xpos > 700) {
                        xpos = 25;
                        ypos += 105;
                    }
                    current = current.getNextCard();

                }
                //^- displays dealer's cards
                xpos = 25;
                ypos += 205;
                Card deckBack = new Card(52);
                g.drawImage(deckBack.getCardImage(), xpos, ypos, this);
                xpos += 80;
                g.drawImage(inGame.getFirstCard().getCardImage(), xpos, ypos, this);
                //^-displays the deck and card in play.

                current = yourHand.getFirstCard();
                xpos = 25;
                ypos += 205;
                g.drawString("Your Hand", xpos, ypos);
                ypos += 20;
                int i = 1;
                while (current != null) {
                    g.drawString("Card " + i + ":", xpos, ypos);
                    ypos += 5;
                    Image tempimage = current.getCardImage();
                    g.drawImage(tempimage, xpos, ypos, this);
                    // note: tempimage member variable must be set BEFORE paint is called
                    ypos -= 5;
                    xpos += 80;
                    if (xpos > 700) {
                        xpos = 25;
                        ypos += 105;
                    }
                    current = current.getNextCard();
                    i++;
                }
            }
        }
    }

}// end of Crazy 8
