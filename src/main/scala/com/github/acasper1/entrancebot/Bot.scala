package com.github.acasper1.entrancebot

import discord4j.core.{DiscordClient, DiscordClientBuilder}
import org.slf4j.{Logger, LoggerFactory}

object Bot extends App {

  val logger: Logger = LoggerFactory.getLogger(this.getClass.getName)

  // TODO: use a more robust method to handle bot configuration
  logger.info("Initializing bot...")
  val discordToken = args(0)
  val discordClient: DiscordClient = DiscordClientBuilder.create(discordToken).build()
//  discordClient.login().block()
}
