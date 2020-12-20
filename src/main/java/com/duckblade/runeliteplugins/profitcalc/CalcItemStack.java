package com.duckblade.runeliteplugins.profitcalc;

import lombok.Data;
import net.runelite.client.util.AsyncBufferedImage;

@Data
public class CalcItemStack {

    private final String itemName;
    private final AsyncBufferedImage itemIcon;
    private final int ppu;
    private final int amount;

}
