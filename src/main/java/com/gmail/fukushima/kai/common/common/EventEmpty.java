package com.gmail.fukushima.kai.common.common;


public class EventEmpty extends Event {
	@Override
	public void runEvent() {
		UtilitiesProgramming.printDebugMessage("Run Empty Event. This is not a valid NPC.", new Exception());
	}
}
