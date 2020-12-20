package com.duckblade.runeliteplugins.profitcalc.ui;

import com.duckblade.runeliteplugins.profitcalc.CalcItemStack;
import com.duckblade.runeliteplugins.profitcalc.ProfitCalcPlugin;
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;

public class ItemStackPanel extends JPanel {

    @Getter
    private CalcItemStack itemStack;

    private JLabel imageLabel;

    private JPanel innerPanel;
    private JLabel nameAmountLabel;
    private JLabel priceLabel;

    public ItemStackPanel(CalcItemStack itemStack) {
        this.itemStack = itemStack;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorScheme.LIGHT_GRAY_COLOR, 1, true));
        setMaximumSize(new Dimension(500, 40));

        imageLabel = new JLabel();
        itemStack.getItemIcon().addTo(imageLabel);
        add(imageLabel, BorderLayout.WEST);

        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(2, 1));
        add(innerPanel, BorderLayout.CENTER);

        nameAmountLabel = new JLabel(itemStack.getItemName() + " x" + itemStack.getAmount());
        nameAmountLabel.setForeground(Color.white);
        innerPanel.add(nameAmountLabel);

        priceLabel = new JLabel(ProfitCalcPlugin.DECIMAL_FORMAT.format((long) itemStack.getPpu() * itemStack.getAmount()) + "gp");
        priceLabel.setForeground(Color.white);
        innerPanel.add(priceLabel);
    }

}
