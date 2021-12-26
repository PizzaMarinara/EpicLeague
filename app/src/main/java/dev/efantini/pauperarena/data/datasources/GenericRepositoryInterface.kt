package dev.efantini.pauperarena.data.datasources

interface GenericRepositoryInterface<T> {
    suspend fun getItems(): List<T>
    suspend fun putItems(items: List<T>)
    suspend fun deleteItems(items: List<T>)
}
