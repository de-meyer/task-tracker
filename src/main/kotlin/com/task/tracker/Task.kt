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

@Serializable
data class Task(
    val id: Int,
    var description: String,
    var status: TaskStatus,
    @Contextual val createdAt: Instant, // Use @Contextual to serialize Instant
    @Contextual var updatedAt: Instant? // Use @Contextual to serialize Instant
)
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
        contextual(Instant::class, InstantSerializer)  // Register Instant serializer
    }
    prettyPrint = true
}
