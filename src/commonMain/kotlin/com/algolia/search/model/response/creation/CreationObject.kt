package com.algolia.search.model.response.creation

import com.algolia.search.endpoint.EndpointAdvanced
import com.algolia.search.model.ClientDate
import com.algolia.search.model.ObjectID
import com.algolia.search.model.task.Task
import com.algolia.search.model.task.TaskID
import com.algolia.search.serialize.KeyCreatedAt
import com.algolia.search.serialize.KeyObjectID
import com.algolia.search.serialize.KeyTaskID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreationObject(
    /**
     * The date at which the record has been created.
     */
    @SerialName(KeyCreatedAt) val createdAt: ClientDate,
    /**
     * The [TaskID] which can be used with the [EndpointAdvanced.waitTask] method.
     */
    @SerialName(KeyTaskID) override val taskID: TaskID,
    /**
     * The inserted record [ObjectID]
     */
    @SerialName(KeyObjectID) val objectID: ObjectID
) : Task
