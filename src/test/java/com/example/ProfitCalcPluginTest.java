package com.example;

import com.duckblade.runeliteplugins.profitcalc.ProfitCalcPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ProfitCalcPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ProfitCalcPlugin.class);
		RuneLite.main(args);
	}
}
