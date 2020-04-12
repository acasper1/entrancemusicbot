package com.github.acasper1.entrancebot

import discord4j.core.{DiscordClient, DiscordClientBuilder}
import org.slf4j.{Logger, LoggerFactory}

object Bot extends App {

  val LOGGER: Logger = LoggerFactory.getLogger(this.getClass)

  // TODO: use a more robust method to handle bot configuration
  LOGGER.info("Initializing bot...")
  val discordToken = args(0)
  val discordClient: DiscordClient = DiscordClientBuilder.create(discordToken).build()
//  discordClient.login().block()
}
