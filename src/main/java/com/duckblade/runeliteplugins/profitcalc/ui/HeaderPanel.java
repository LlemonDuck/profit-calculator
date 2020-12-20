package com.duckblade.runeliteplugins.profitcalc.ui;

import com.duckblade.runeliteplugins.profitcalc.ProfitCalcPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Singleton
public class HeaderPanel extends JPanel {

    private final ProfitCalcPanel parent;

    private final JLabel titleLabel;
    private final JButton resetButton;

    @Inject
    public HeaderPanel(ProfitCalcPanel parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(1, 0, 10, 0));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        titleLabel = new JLabel("Profit Calculator");
        titleLabel.setForeground(Color.white);
        add(titleLabel, BorderLayout.WEST);

        resetButton = new JButton(new ImageIcon(ImageUtil.getResourceStreamFromClass(ProfitCalcPlugin.class, "/reset.png")));
        resetButton.setBackground(ColorScheme.DARK_GRAY_COLOR);
        resetButton.setBorder(null);
        resetButton.setBorderPainted(false);
        resetButton.setToolTipText("Reset Panel");
        resetButton.addActionListener(e -> parent.reset());
        add(resetButton, BorderLayout.EAST);
    }

}
