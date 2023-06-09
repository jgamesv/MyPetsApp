package com.example.mypets.domain.repository

import com.example.mypets.domain.model.BaseResponse
import com.example.mypets.domain.model.Pet
import com.example.mypets.domain.model.PetMiss
import com.example.mypets.domain.model.RequestAdoption
import com.example.mypets.domain.model.User
import okhttp3.MultipartBody

interface PetsRepository {
    suspend fun login (email: String, pass: String):Int
    suspend fun register(user: User):Int
    suspend fun getPets(): List<Pet>?
    suspend fun getPet(id:Int): Pet?
    suspend fun deleteUser(id: Int): BaseResponse?
    suspend fun getUser(): User?
    suspend fun filter(type: String): List<Pet>?
    suspend fun logout ()
    suspend fun getTypes(): List<String>?
    suspend fun updateUser(user: User): BaseResponse?
    suspend fun addComplaint(image: MultipartBody.Part, text: String): Int
    suspend fun getComplaint(): List<PetMiss>?
    suspend fun changeAvatar(image: MultipartBody.Part): Int
    suspend fun adoptionRequest(requestAdoption: RequestAdoption): BaseResponse
    suspend fun validRequest(id:Int): Boolean?
}