package com.timplifier.rickandmorty.data.repositories

import com.timplifier.rickandmorty.base.BaseRepository
import com.timplifier.rickandmorty.data.local.db.daos.CharacterDao
import com.timplifier.rickandmorty.data.remote.apiservices.CharactersApiService

class CharacterRepository(
    private val charactersApiService: CharactersApiService,
    private val characterDao: CharacterDao
) : BaseRepository() {

    fun fetchCharacters(page: Int) = sendRequest(

        { charactersApiService.fetchCharacters(page) },

        { characters ->
            characterDao.insertCharacters(*characters.results.toTypedArray())
        }
    )


    fun fetchSingleCharacter(id: Int) = sendRequest {
        charactersApiService.fetchSingleCharacter(id)
    }


    fun getCharacters() = sendRequest {

        characterDao.getCharacters()
    }


}
