package com.example.saisampleapp.data.model.api

import com.example.saisampleapp.data.model.SATScores
import com.example.saisampleapp.data.model.School
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolServices {

    @GET("s3k6-pzi2.json")
    suspend fun getSchools() : List<School>

    @GET("f9bf-2cp4.json")
    suspend fun getSATScore(@Query("dbn") dbn: String): List<SATScores>
}