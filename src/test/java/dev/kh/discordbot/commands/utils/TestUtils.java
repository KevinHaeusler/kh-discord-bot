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

package dev.kh.discordbot.commands.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.ImageProxy;

import static org.mockito.Mockito.when;

public class TestUtils {

    public static void setCommonVariables(JDA jda, User user, SlashCommandInteractionEvent event, ReplyCallbackAction mockReply) {
        when(user.getAvatar()).thenReturn(new ImageProxy(""));
        when(user.getName()).thenReturn("TestUser");
        when(event.getUser()).thenReturn(user);

        long mockDiscordId = 123456789L;

        when(event.getUser().getIdLong()).thenReturn(mockDiscordId);
        when(event.getJDA()).thenReturn(jda);

        when(event.deferReply()).thenReturn(mockReply);
    }
}