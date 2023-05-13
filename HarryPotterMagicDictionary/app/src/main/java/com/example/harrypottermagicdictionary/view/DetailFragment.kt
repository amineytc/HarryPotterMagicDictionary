package com.example.harrypottermagicdictionary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.harrypottermagicdictionary.R
import com.example.harrypottermagicdictionary.databinding.FragmentDetailBinding
import com.example.harrypottermagicdictionary.util.imageDownload
import com.example.harrypottermagicdictionary.util.placeholderMake
import com.example.harrypottermagicdictionary.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel : DetailViewModel
    private var wordId = 0
    private lateinit var dataBinding: FragmentDetailBinding

    private var _binding : FragmentDetailBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            wordId = DetailFragmentArgs.fromBundle(it).wordId

        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.roomDataGet(wordId)

        observeLİveData()
    }

    fun observeLİveData(){
        viewModel.wordLiveData.observe(viewLifecycleOwner, Observer {word ->
            word?.let{

                dataBinding.chooseWord=it

            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}