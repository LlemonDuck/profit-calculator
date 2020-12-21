package com.duckblade.runeliteplugins.profitcalc;

import com.duckblade.runeliteplugins.profitcalc.ui.ItemStackPanel;
import net.runelite.api.GameState;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.chatbox.ChatboxItemSearch;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.util.AsyncBufferedImage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.util.function.Consumer;

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
                    clientThread.invokeLater(() -> triggerAmountInput(callbackAddItem(itemManager.canonicalize(i), inputMode)));
                }).build();
    }

    public void triggerNewQuantity(ItemStackPanel itemStackPanel) {
        triggerAmountInput(newAmount -> {
            CalcItemStack oldStack = itemStackPanel.getItemStack();
            CalcItemStack newStack = new CalcItemStack(oldStack.getItemName(), oldStack.getItemIcon(), oldStack.getPpu(), newAmount);
            itemStackPanel.setItemStack(newStack);
        });
    }

    public void triggerNewPpu(ItemStackPanel itemStackPanel) {
        triggerAmountInput(newPpu -> {
            CalcItemStack oldStack = itemStackPanel.getItemStack();
            CalcItemStack newStack = new CalcItemStack(oldStack.getItemName(), oldStack.getItemIcon(), newPpu, oldStack.getAmount());
            itemStackPanel.setItemStack(newStack);
        });
    }

    private Consumer<Float> callbackAddItem(int itemId, boolean inputMode) {
        return amount -> clientThread.invokeLater(() -> {
            int ppu = itemManager.getItemPrice(itemId);
            String itemName = itemManager.getItemComposition(itemId).getName();
            AsyncBufferedImage itemImage = itemManager.getImage(itemId);

            CalcItemStack itemStack = new CalcItemStack(itemName, itemImage, ppu, amount);
            plugin.getCalcPanel().addItemFromSearch(inputMode, itemStack);
        });
    }

    public void triggerAmountInput(Consumer<Float> callback) {
        chatboxPanelManager.openTextInput("Enter Amount")
                .addCharValidator(c -> (c >= '0' && c <= '9') || (c == '.'))
                .onDone(amountStrP -> {
                    String amountStr = amountStrP;
                    if (amountStr.length() > 10) amountStr = amountStr.substring(0, 10);
                    float amount = Float.parseFloat(amountStr);
                    callback.accept(amount);
                }).build();
    }

}
