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
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import net.dv8tion.jda.api.JDA;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AutoSlashAdder extends SlashCommandHandler {

    public AutoSlashAdder(JDA jda) {
        super(jda);

        registerSlashCommands(loadSlashCommands()
                .stream()
                .map(clazz -> {
                    try {
                        return clazz.getConstructor().newInstance();
                    } catch (Exception e) {
                        Main.logger.error("Failed to instantiate class: " + clazz.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        sendSlashCommands();
    }

    private List<Class<? extends ISlashCommand>> loadSlashCommands() {
        try (ScanResult result = new ClassGraph().enableClassInfo().scan()) {
            return new ArrayList<>(result.getAllClasses()
                    .filter(classInfo -> classInfo.implementsInterface(ISlashCommand.class))
                    .loadClasses(ISlashCommand.class));
        }
    }
}