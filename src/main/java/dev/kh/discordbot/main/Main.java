/*
 * Copyright 2024 Kevin Häusler, KHDev, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package dev.kh.discordbot.main;

import com.fasterxml.jackson.databind.JsonNode;
import dev.kh.discordbot.commands.AutoSlashAdder;
import dev.kh.discordbot.commands.SlashCommandHandler;
import io.github.realyusufismail.jconfig.JConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Main.
 */
public class Main {
    public static JConfig config = JConfig.builder().build();
    public static Logger logger = LoggerFactory.getLogger(Main.class);
    private static SlashCommandHandler slashCommandHandler;

    public static void main(String[] args) throws InterruptedException {
        JsonNode token = config.get("token");

        if (token == null) {
            throw new RuntimeException("Token not found in config file");
        }

        JDA jda = JDABuilder.createDefault(token.asText())
                .build();

        jda.awaitReady();

        logger.info("Logged in as: {}", jda.getSelfUser().getName());

        try {
            logger.info("Loading all Commands");
            slashCommandHandler = new AutoSlashAdder(jda);
            logger.info("Loaded all Commands");
        } catch (Exception e) {
            logger.error("Failed to initialize slash command handler", e);
        }

        jda.addEventListener(new ListenerAdapter() {
            @Override
            public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
                slashCommandHandler.onSlashCommandInteractionEvent(event);
            }
        });
    }
}