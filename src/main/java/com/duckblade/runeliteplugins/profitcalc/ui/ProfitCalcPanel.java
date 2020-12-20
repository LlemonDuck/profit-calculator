package com.duckblade.runeliteplugins.profitcalc.ui;

import com.duckblade.runeliteplugins.profitcalc.CalcItemStack;
import com.duckblade.runeliteplugins.profitcalc.ProfitCalcPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

@Singleton
public class ProfitCalcPanel extends PluginPanel {

    private HeaderPanel headerPanel;
    private ItemListPanel inputPanel;
    private ItemListPanel outputPanel;

    private JPanel itemListPanel;
    private JLabel profitLabel;

    private long profit;

    @Inject
    public ProfitCalcPanel(ItemListPanel inputPanel, ItemListPanel outputPanel) {
        super(false);
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        headerPanel = new HeaderPanel(this);
        add(headerPanel, BorderLayout.NORTH);

        itemListPanel = new JPanel();
        itemListPanel.setLayout(new GridLayout(2, 1));
        itemListPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        add(itemListPanel, BorderLayout.CENTER);

        inputPanel.setInputMode(true);
        outputPanel.setInputMode(false);
        itemListPanel.add(inputPanel);
        itemListPanel.add(outputPanel);

        this.profit = 0;
        profitLabel = new JLabel("Profit: 0gp", JLabel.RIGHT);
        profitLabel.setForeground(Color.white);
        add(profitLabel, BorderLayout.SOUTH);
    }

    public void reset() {
        SwingUtilities.invokeLater(() -> {
            inputPanel.reset();
            outputPanel.reset();

            this.profit = 0;
            profitLabel.setText("Profit: 0gp");

            repaint();
            revalidate();
        });
    }

    public void addItemFromSearch(boolean inputMode, CalcItemStack itemStack) {
        ItemListPanel target = inputMode ? inputPanel : outputPanel;

        SwingUtilities.invokeLater(() -> {
            profit += ((long) itemStack.getAmount() * itemStack.getPpu()) * (inputMode ? -1 : 1);
            profitLabel.setText("Profit: " + ProfitCalcPlugin.DECIMAL_FORMAT.format(profit) + "gp");

            target.addItemFromSearch(itemStack);
            repaint();
            revalidate();
        });
    }
}
