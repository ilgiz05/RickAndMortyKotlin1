package com.timplifier.rickandmorty.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timplifier.rickandmorty.R
import com.timplifier.rickandmorty.common.extensions.setImage
import com.timplifier.rickandmorty.data.remote.dtos.character.RickAndMortyCharacter
import com.timplifier.rickandmorty.databinding.ItemCharactersBinding
import com.timplifier.rickandmorty.presentation.ui.adapters.diffutils.CharactersDiffUtil

class CharactersAdapter(
    private val onItemClick: (id: Int) -> Unit
) :
    ListAdapter<RickAndMortyCharacter, CharactersAdapter.CharactersViewHolder>(
        CharactersDiffUtil()
    ) {
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            ItemCharactersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CharactersViewHolder(private val binding: ItemCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(character: RickAndMortyCharacter) {
            binding.apply {
                imCharacter.setImage(character.image)
                tvCharacter.text = character.name
                tvStatus.text = character.status
                tvSpecies.text = character.species
                //      tvLastKnownLocation.text = character.location.toString()
                //     tvFirstLocation.text = character.origin.toString()
                when (character.status) {
                    "Alive" -> {
                        imStatus.setImageResource(R.drawable.alive_status)
                    }
                    "Dead" -> {
                        imStatus.setImageResource(R.drawable.dead_status)
                    }
                    "Unknown" -> {
                        imStatus.setImageResource(R.drawable.unknown_status)
                    }
                }


                root.setOnClickListener {
                    onItemClick(character.id)

                }

            }
        }


    }
}




