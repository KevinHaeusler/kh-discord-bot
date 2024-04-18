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

package dev.kh.discordbot.commands.interfaces;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collections;
import java.util.List;

public interface ISlashCommand {

    void onSlashCommandInteractionEvent(SlashCommandInteractionEvent event);

    String getName();

    String getDescription();

    default List<OptionData> getOptions() {
        return Collections.emptyList();
    }

    /**
     * Whether the command can appear in dm's or not
     *
     * @return true if the command can appear in dm's
     */
    default boolean isGlobal() {
        return true;
    }
}