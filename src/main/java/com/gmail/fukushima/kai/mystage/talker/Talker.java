package com.gmail.fukushima.kai.mystage.talker;

import java.util.ArrayList;
import java.util.List;

import com.gmail.fukushima.kai.common.common.Sentence;

public class Talker {
	public String name;
	public Integer id;
	public List<Sentence> listSentence = new ArrayList<Sentence>();
	public void printDebug() {
		System.out.println(" [Debug Talker]" + name);
		System.out.println("  ID: " + id);
	}
}