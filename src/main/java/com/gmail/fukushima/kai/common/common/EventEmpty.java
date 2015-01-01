package com.gmail.fukushima.kai.common.common;

import com.gmail.fukushima.kai.utilities.utilities.Event;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;


public class EventEmpty extends Event {
	@Override
	public void runEvent() {
		UtilitiesProgramming.printDebugMessage("Run Empty Event. This is not a valid NPC.", new Exception());
	}
}
