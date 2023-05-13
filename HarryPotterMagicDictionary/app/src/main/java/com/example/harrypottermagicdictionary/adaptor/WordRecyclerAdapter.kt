package com.example.harrypottermagicdictionary.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypottermagicdictionary.R
import com.example.harrypottermagicdictionary.databinding.WordRecyclerRowBinding
import com.example.harrypottermagicdictionary.model.Words
import com.example.harrypottermagicdictionary.view.WordListFragmentDirections


class WordRecyclerAdapter(val wordList : ArrayList<Words>): RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder>(){

    class WordViewHolder( var binding:WordRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<WordRecyclerRowBinding>(inflater,R.layout.word_recycler_row,parent,false)
        return WordViewHolder(view)

    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        holder.binding.words = wordList[position]

        holder.itemView.setOnClickListener {
            val action = WordListFragmentDirections.actionWordListFragmentToDetailFragment(
                wordList.get(position).uuid
            )
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun wordListUpdate(newWordList:List<Words>){
        wordList.clear()
        wordList.addAll(newWordList)
        notifyDataSetChanged()

    }






}


