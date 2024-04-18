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

package dev.kh.discordbot.commands;

import dev.kh.discordbot.commands.interfaces.ISlashCommand;
import dev.kh.discordbot.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlashCommandHandler {
    private final Map<String, ISlashCommand> slashCommands = new HashMap<>();
    private final List<CommandData> registeredCommands = new ArrayList<>();
    private final JDA jda;

    protected SlashCommandHandler(JDA jda) {
        this.jda = jda;
    }

    private void addSlashCommand(ISlashCommand slashCommand) {
        if (slashCommand.getName().isBlank()) {
            throw new IllegalArgumentException("Command name cannot be blank");
        }

        if (slashCommands.containsKey(slashCommand.getName())) {
            throw new IllegalArgumentException("Command with name " + slashCommand.getName() + " already exists");
        }

        slashCommands.put(slashCommand.getName(), slashCommand);

        if (!slashCommand.isGlobal()) {
            CommandData commandData = new CommandDataImpl(slashCommand.getName(), slashCommand.getDescription())
                    .addOptions(slashCommand.getOptions())
                    .setGuildOnly(true);

            registeredCommands.add(commandData);
        } else {
            CommandData commandData = new CommandDataImpl(slashCommand.getName(), slashCommand.getDescription())
                    .addOptions(slashCommand.getOptions())
                    .setGuildOnly(false);

            registeredCommands.add(commandData);
        }
    }

    protected void registerSlashCommands(List<ISlashCommand> slashCommands) {
        slashCommands.forEach(this::addSlashCommand);
    }

    protected void sendSlashCommands() {
        registeredCommands.forEach(commandData -> jda.upsertCommand(commandData).queue());

        deleteOutdatedSlashCommands();
    }

    private void deleteOutdatedSlashCommands() {
        List<Command> commands = jda.retrieveCommands().complete();

        commands.forEach(command -> {
            if (!slashCommands.containsKey(command.getName())) {
                jda.deleteCommandById(command.getId()).queue();
            }
        });
    }

    public void onSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (!slashCommands.containsKey(commandName)) {
            Main.logger.error("Command with name " + commandName + " does not exist");
            return;
        }

        ISlashCommand command = slashCommands.get(commandName);

        command.onSlashCommandInteractionEvent(event);
    }
}