package com.github.acasper1.entrancebot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class TrackScheduler implements AudioLoadResultHandler {

    private final AudioPlayer player;

    public TrackScheduler(final AudioPlayer player) {
        this.player = player;
    }

    @Override
    public void trackLoaded(final AudioTrack track) {
        this.player.playTrack(track);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        // multiple AudioTracks from some playlist
    }

    @Override
    public void noMatches() {
        // no audio to extract
    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        // could not parse audio
    }
}
