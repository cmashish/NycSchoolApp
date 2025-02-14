package com.example.saisampleapp.data.model.repository

import com.example.saisampleapp.data.model.SATScores
import com.example.saisampleapp.data.model.School
import com.example.saisampleapp.di.RetrofitInstance
import javax.inject.Inject

class SchoolRepository @Inject constructor() {

    private val api = RetrofitInstance.api

    suspend fun getSchools():List<School>{
        return api.getSchools()
    }

    suspend fun getSatScores(dbn:String) : List<SATScores>{
        return api.getSATScore(dbn)
    }
}