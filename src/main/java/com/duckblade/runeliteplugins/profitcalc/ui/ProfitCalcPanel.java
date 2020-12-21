package com.duckblade.runeliteplugins.profitcalc.ui;

import com.duckblade.runeliteplugins.profitcalc.CalcItemStack;
import com.duckblade.runeliteplugins.profitcalc.ProfitCalcPlugin;
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;

@Singleton
public class ProfitCalcPanel extends PluginPanel {

    @Getter
    private final ProfitCalcPlugin plugin;

    private HeaderPanel headerPanel;
    private ItemListPanel inputPanel;
    private ItemListPanel outputPanel;

    private JPanel itemListPanel;
    private JLabel profitLabel;

    private float profit;

    public ProfitCalcPanel(ProfitCalcPlugin parent) {
        super(false);
        this.plugin = parent;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        headerPanel = new HeaderPanel(this);
        add(headerPanel, BorderLayout.NORTH);

        itemListPanel = new JPanel();
        itemListPanel.setLayout(new GridLayout(2, 1));
        itemListPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        add(itemListPanel, BorderLayout.CENTER);

        this.inputPanel = new ItemListPanel(this);
        inputPanel.setInputMode(true);
        itemListPanel.add(inputPanel);

        this.outputPanel = new ItemListPanel(this);
        outputPanel.setInputMode(false);
        itemListPanel.add(outputPanel);

        this.profit = 0;
        profitLabel = new JLabel("Profit: 0gp", JLabel.RIGHT);
        profitLabel.setForeground(Color.white);
        add(profitLabel, BorderLayout.SOUTH);
    }

    public void reset() {
        inputPanel.reset();
        outputPanel.reset();
    }

    public void addItemFromSearch(boolean inputMode, CalcItemStack itemStack) {
        SwingUtilities.invokeLater(() -> {
            ItemListPanel target = inputMode ? inputPanel : outputPanel;
            target.addItemFromSearch(itemStack);
            recalculate();
        });
    }

    public void recalculate() {
        SwingUtilities.invokeLater(() -> {
            profit = outputPanel.getValue() - inputPanel.getValue();
            profitLabel.setText("Profit: " + ProfitCalcPlugin.DECIMAL_FORMAT.format(profit) + "gp");
            repaint();
            revalidate();
        });
    }
}
