package com.duckblade.runeliteplugins.profitcalc;

import lombok.Builder;
import lombok.Data;
import net.runelite.client.util.AsyncBufferedImage;

@Data
@Builder
public class CalcItemStack {

    private final String itemName;
    private final AsyncBufferedImage itemIcon;
    private final float ppu;
    private final float amount;

    public float getValue() {
        return ppu * amount;
    }

}
