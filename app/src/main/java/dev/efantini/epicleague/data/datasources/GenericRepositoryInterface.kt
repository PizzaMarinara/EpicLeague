package dev.efantini.epicleague.data.datasources

interface GenericRepositoryInterface<T> {
    suspend fun getItems(): List<T>
    suspend fun putItems(items: List<T>)
    suspend fun deleteItems(items: List<T>)
    suspend fun getElementById(id: Long): T?
}
