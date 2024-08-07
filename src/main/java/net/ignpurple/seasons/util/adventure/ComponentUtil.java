package net.ignpurple.seasons.util.adventure;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComponentUtil {
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static String serializeMiniString(String string) {
        return LEGACY_COMPONENT_SERIALIZER.serialize(MINI_MESSAGE.deserialize(string));
    }

    public static BaseComponent serializeMiniStringToTextComponent(String string) {
        return TextComponent.fromLegacy(LEGACY_COMPONENT_SERIALIZER.serialize(MINI_MESSAGE.deserialize(string)));
    }
}
