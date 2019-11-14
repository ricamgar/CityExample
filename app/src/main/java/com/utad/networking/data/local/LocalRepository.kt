package com.utad.networking.data.local

import com.utad.networking.model.User

interface LocalRepository {
    suspend fun getLoggedUser(): User?
    suspend fun setLoggedUser(user: User)
    suspend fun deleteLoggedUser()
}
