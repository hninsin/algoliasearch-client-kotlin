package com.algolia.search.model.search

import com.algolia.search.helper.and
import com.algolia.search.model.Raw
import kotlinx.serialization.*
import kotlinx.serialization.internal.FloatSerializer


@Serializable(Polygon.Companion::class)
public data class Polygon(
    val point1: Point,
    val point2: Point,
    val point3: Point,
    private val points: List<Point>
) : Raw<List<Float>> {

    public constructor(point1: Point, point2: Point, point3: Point, vararg points: Point) : this(
        point1,
        point2,
        point3,
        points.toList()
    )

    public operator fun get(index: Int): Point {
        return when (index) {
            0 -> point1
            1 -> point2
            2 -> point3
            else -> points[index - 3]
        }
    }

    override val raw = listOf(
        *point1.raw.toTypedArray(),
        *point2.raw.toTypedArray(),
        *point3.raw.toTypedArray(),
        *points.flatMap { it.raw }.toTypedArray()
    )

    internal companion object : KSerializer<Polygon> {

        private val serializer = FloatSerializer.list

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: Polygon) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): Polygon {
            val floats = serializer.deserialize(decoder)

            return Polygon(
                floats[0] and floats[1],
                floats[2] and floats[3],
                floats[4] and floats[5],
                (6 until floats.size step 2).map {
                    floats[it] and floats[it + 1]
                }
            )
        }
    }
}