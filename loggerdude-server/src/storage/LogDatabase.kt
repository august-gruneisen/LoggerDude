package com.augustg.storage

import com.augustg.Env
import com.augustg.model.FormattedLog
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

/**
 * Singleton object wrapping a database connection
 */
object LogDatabase {

    private val collection: MongoCollection<FormattedLog>

    init {
        // get remote data collection
        val mongoClient: MongoClient = KMongo.createClient(Env.connectionString)
        val database: MongoDatabase = mongoClient.getDatabase(Env.databaseName)
        collection = database.getCollection<FormattedLog>(if (Env.DEBUG) Env.collectionNameDebug else Env.collectionName)
    }

    /**
     * Inserts new logs into the collection
     * @param newLogs
     * @return boolean if the insertion was acknowledged
     */
    fun insertLogs(newLogs: List<FormattedLog>) = collection.insertMany(newLogs)
        .wasAcknowledged()

    /**
     * Finds all logs in the collection
     * @return all logs
     */
    fun findLogs(): List<FormattedLog> = collection.find().toList()

    /**
     * Drops the entire collection
     */
    fun dropAll()= collection.drop()
}