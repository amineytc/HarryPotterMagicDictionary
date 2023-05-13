package com.example.harrypottermagicdictionary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypottermagicdictionary.adaptor.WordRecyclerAdapter
import com.example.harrypottermagicdictionary.databinding.FragmentWordListBinding
import com.example.harrypottermagicdictionary.viewmodel.WordListViewModel


class WordListFragment : Fragment() {

    private var _binding:FragmentWordListBinding?=null
    private val binding get() = _binding!!

    private lateinit var viewModel : WordListViewModel
    private val recyclerWordAdapter = WordRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentWordListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@WordListFragment)[WordListViewModel::class.java]
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerWordAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.progressBar.visibility=View.VISIBLE
            binding.textView3.visibility=View.GONE
            binding.recyclerView.visibility=View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing=false
        }
        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.words.observe(viewLifecycleOwner, Observer {words ->
            words?.let {
                binding.recyclerView.visibility=View.VISIBLE
                recyclerWordAdapter.wordListUpdate(words)
            }
        })

        viewModel.wordsError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if(it){
                    binding.textView3.visibility=View.VISIBLE
                    binding.recyclerView.visibility=View.GONE
                }else{
                    binding.textView3.visibility = View.GONE
                }
            }
        })

        viewModel.wordsLoading.observe(viewLifecycleOwner, Observer {load ->
            load?.let {
                if(it){
                    binding.recyclerView.visibility = View.GONE
                    binding.textView3.visibility = View.GONE
                    binding.progressBar.visibility=View.VISIBLE
                }else{
                    binding.progressBar.visibility=View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}