package com.github.acasper1.entrancebot;

import com.github.acasper1.entrancebot.audio.LavaPlayerAudioProvider;
import com.github.acasper1.entrancebot.audio.TrackScheduler;
import com.github.acasper1.entrancebot.command.Command;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    private final DiscordClient client;
    private final AudioPlayerManager playerManager;
    private final LavaPlayerAudioProvider player;
    private final TrackScheduler scheduler;
    private final Map<String, Command> commands;
    private final Map<String, String> userMapping;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Bot(DiscordClient client, AudioPlayerManager playerManager,
               LavaPlayerAudioProvider player, TrackScheduler scheduler) {

        LOGGER.info("Initializing Bot...");
        this.client = client;
        this.playerManager = playerManager;
        this.player = player;
        this.scheduler = scheduler;
        this.commands = new HashMap<>();
        this.userMapping = new HashMap<>();
        this.addDefaultCommands();
        LOGGER.info("Bot initialization complete!");
    }

    public void run() {
        // TODO: Add voice channel join events to event dispatcher
        this.client.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(this.commands.entrySet())
                                .filter(entry -> content.contains("#!" + entry.getKey()))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();
        LOGGER.info("Bot is listening for events!");
    }

    private void addDefaultCommands() {
        LOGGER.info("Adding default commands...");
        LOGGER.debug("Adding #!ping command...");
        // #!ping command - Checks whether the bot is listening
        commands.put(
                "ping",
                event -> event.getMessage().getChannel()
                        .flatMap(channel -> channel.createMessage("Pong!"))
                        .then()
        );

        LOGGER.debug("Adding #!useradd command...");
        // #!useradd command - Allows adding of a user:song-url mapping
        commands.put(
                "useradd",
                event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .map(content -> Arrays.asList(content.split(" ")).get(1))
                        .map(content -> Arrays.asList(content.split(":")))
                        .doOnNext(command -> this.userMapping.put(command.get(0).trim(), command.get(1).trim()))
                        .then()
        );

        LOGGER.debug("Adding #!userdel command...");
        // #!userdel command - Allows removing of a user:song-url mapping
        commands.put(
                "userdel",
                event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .map(content -> Arrays.asList(content.split(" ")).get(1))
                        .doOnNext(user -> this.userMapping.remove(user.trim()))
                        .then()
        );
        LOGGER.info("Adding default commands complete!");
    }


}
