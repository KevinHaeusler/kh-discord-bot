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

package dev.kh.discordbot.commands.fun;

import dev.kh.discordbot.commands.interfaces.ISlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand implements ISlashCommand {
     
    @Override
    public void onSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
        long restPing = event.getJDA().getRestPing().complete();
        long gatewayPing = event.getJDA().getGatewayPing();

        event.reply("Rest Ping: " + restPing + "ms\nGateway Ping: " + gatewayPing + "ms").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Shows the bot's ping.";
    }
}