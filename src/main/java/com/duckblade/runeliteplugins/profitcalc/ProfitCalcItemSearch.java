package com.duckblade.runeliteplugins.profitcalc;

import net.runelite.api.GameState;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.chatbox.ChatboxItemSearch;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.util.AsyncBufferedImage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;

@Singleton
public class ProfitCalcItemSearch {

    @Inject
    private ChatboxItemSearch itemSearch;

    @Inject
    private ChatboxPanelManager chatboxPanelManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ProfitCalcPlugin plugin;

    public void triggerSearch(boolean inputMode) {

        if (plugin.getClient().getGameState() != GameState.LOGGED_IN) {
            JOptionPane.showMessageDialog(plugin.getCalcPanel(), "You must be logged in to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        itemSearch.tooltipText("Add " + (inputMode ? "Input" : "Output"))
                .onItemSelected(i -> {
                    clientThread.invokeLater(() -> triggerAmountInput(itemManager.canonicalize(i), inputMode));
                }).build();
    }

    public void triggerAmountInput(int itemId, boolean inputMode) {
        chatboxPanelManager.openTextInput("Enter Amount")
                .addCharValidator(c -> c >= 48 && c <= 57)
                .onDone(amountStrP -> {
                    clientThread.invokeLater(() -> {
                        String amountStr = amountStrP;
                        if (amountStr.length() > 10) amountStr = amountStr.substring(0, 10);
                        int amount = Integer.parseInt(amountStr);
                        int ppu = itemManager.getItemPrice(itemId);
                        String itemName = itemManager.getItemComposition(itemId).getName();
                        AsyncBufferedImage itemImage = itemManager.getImage(itemId);

                        CalcItemStack itemStack = new CalcItemStack(itemName, itemImage, ppu, amount);
                        plugin.getCalcPanel().addItemFromSearch(inputMode, itemStack);
                    });
                }).build();
    }

}
