package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage.PathStage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta;

public class QuestionSentence extends Sentence{
	private String question = "";
	private List<String> answers;

	public static Optional<QuestionSentence> create(ConfigurationSection section, List<Integer> index) {
		if(section.isString(PathStage.Q.toString()) && section.isList(PathStage.A.toString())) {
			QuestionSentence instance = new QuestionSentence();
			instance.question = section.getString(PathStage.Q.name());
			instance.answers = section.getStringList(PathStage.A.name());
			instance.id = index.get(index.size() - 1);
			return Optional.of(instance);
		}
		return Optional.empty();
	}

	@Override
	public void print(Player player) {
		player.sendMessage(question);
	}

	public Function<String, Boolean> validate = (answer) ->
		answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer));

	@Override
	public void playEffect(Player player) {
		Effects.playSound(player, SoundMeta.Scene.APPEAR);
	}

	public String getQuestion() {
		return question;
	}
}
