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

    private final ItemListPanel parent;

    private JLabel imageLabel;

    private JPanel innerPanel;
    private JLabel nameLabel;
    private JLabel amountPpuLabel;
    private JLabel priceLabel;
    private JPopupMenu rightClickMenu;

    public ItemStackPanel(ItemListPanel parent, CalcItemStack itemStack) {
        this.parent = parent;
        this.itemStack = itemStack;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorScheme.LIGHT_GRAY_COLOR, 1, true));
        setMaximumSize(new Dimension(500, 60));

        rightClickMenu = new JPopupMenu();
        setComponentPopupMenu(rightClickMenu);

        JMenuItem setQuantityItem = new JMenuItem("Set Quantity");
        setQuantityItem.addActionListener(e -> parent.getCalcPanel().getPlugin().getCalcItemSearch().triggerNewQuantity(this));
        rightClickMenu.add(setQuantityItem);

        JMenuItem setPpuItem = new JMenuItem("Set Price Per Item");
        setPpuItem.addActionListener(e -> parent.getCalcPanel().getPlugin().getCalcItemSearch().triggerNewPpu(this));
        rightClickMenu.add(setPpuItem);

        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(e -> parent.removeItem(this));
        rightClickMenu.add(removeItem);

        imageLabel = new JLabel();
        itemStack.getItemIcon().addTo(imageLabel);
        add(imageLabel, BorderLayout.WEST);

        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(3, 1));
        add(innerPanel, BorderLayout.CENTER);

        nameLabel = new JLabel("", JLabel.CENTER);
        nameLabel.setForeground(Color.white);
        innerPanel.add(nameLabel);

        amountPpuLabel = new JLabel("", JLabel.RIGHT);
        amountPpuLabel.setForeground(Color.white);
        innerPanel.add(amountPpuLabel);

        priceLabel = new JLabel("", JLabel.RIGHT);
        priceLabel.setForeground(Color.white);
        innerPanel.add(priceLabel);

        recalculate();
    }

    public float getValue() {
        return itemStack.getValue();
    }

    public void setItemStack(CalcItemStack newItemStack) {
        this.itemStack = newItemStack;
        recalculate();
    }

    public void recalculate() {
        SwingUtilities.invokeLater(() -> {
            itemStack.getItemIcon().addTo(imageLabel);
            nameLabel.setText(itemStack.getItemName());
            amountPpuLabel.setText(ProfitCalcPlugin.DECIMAL_FORMAT.format(itemStack.getPpu()) + "gp x" + ProfitCalcPlugin.DECIMAL_FORMAT.format(itemStack.getAmount()));
            priceLabel.setText("Total: " + ProfitCalcPlugin.DECIMAL_FORMAT.format(itemStack.getValue()) + "gp");

            repaint();
            revalidate();

            parent.recalculate();
        });
    }

}
