package com.task.tracker

import com.task.tracker.enums.TaskStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.Instant
import java.util.*

@Serializable
data class Task(
    @Contextual val id: UUID,
    var description: String,
    var status: TaskStatus,
    @Contextual val createdAt: Instant,
    @Contextual var updatedAt: Instant?
)
// Create a custom serializer for UUID
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}

// Create a custom serializer for Instant
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}

// Create a Json configuration with contextual serializers
val json = Json {
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)  // Register UUID serializer
        contextual(Instant::class, InstantSerializer)  // Register Instant serializer
    }
    prettyPrint = true
}
