package com.duckblade.runeliteplugins.profitcalc;

import com.duckblade.runeliteplugins.profitcalc.ui.ProfitCalcPanel;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.text.DecimalFormat;

@Slf4j
@PluginDescriptor(
        name = "Profit Calculator"
)
public class ProfitCalcPlugin extends Plugin {
    @Inject
    @Getter
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    @Getter
    private ProfitCalcPanel calcPanel;

    private NavigationButton navButton;

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###,###");

    @Override
    protected void startUp() throws Exception {
        log.info("ProfitCalc started!");

        navButton = NavigationButton.builder()
                .tooltip("Profit Calculator")
                .icon(ImageUtil.getResourceStreamFromClass(getClass(), "/profit.png"))
                .priority(3)
                .panel(calcPanel)
                .build();
        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception {
        clientToolbar.removeNavigation(navButton);
        log.info("ProfitCalc stopped!");
    }

}
