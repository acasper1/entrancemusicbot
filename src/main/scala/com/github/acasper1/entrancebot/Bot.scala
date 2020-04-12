package com.github.acasper1.entrancebot

import discord4j.core.{DiscordClient, DiscordClientBuilder}


object Bot extends App {
  val discordToken = ""
  val discordClient: DiscordClient = DiscordClientBuilder.create(discordToken).build()
}
