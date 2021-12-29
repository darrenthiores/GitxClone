package com.icebeal.gitxclone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icebeal.gitxclone.databinding.FragmentSearchBinding
import com.icebeal.gitxclone.ui.adapter.UserAdapter
import com.icebeal.gitxclone.ui.viewModel.UserViewModel

class SearchFragment : Fragment() {

    private var _binding:FragmentSearchBinding? = null
    private val binding get() = _binding

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding?.rvSearch?.adapter = userAdapter
        binding?.rvSearch?.layoutManager = LinearLayoutManager(activity)

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {

                empty(false)

                if(query.isEmpty()){

                    empty(true)

                } else {

                    userViewModel.searchUser(query)

                    showLoading(true)
                    empty(false)

                }

                return false

            }

            override fun onQueryTextChange(newText: String): Boolean {

                empty(false)

                return false

            }

        })

        userViewModel.getUser().observe(viewLifecycleOwner, {

            if(it != null){

                userAdapter.setData(it)

                showLoading(false)

            }

        })

        userAdapter.setOnItemClick(object : UserAdapter.OnItemClick{

            override fun onItemClicked(username: String) {

                val toDetailActivity = SearchFragmentDirections.actionNavigationSearchToDetailActivity()
                toDetailActivity.username = username

                findNavController().navigate(toDetailActivity)

            }

        })

    }

    private fun showLoading(state:Boolean){

        if(state){

            binding?.loading?.visibility = View.VISIBLE

        } else {

            binding?.loading?.visibility = View.GONE

        }

    }

    private fun empty(state: Boolean){

        if(state){

            binding?.imgEmpty?.visibility = View.VISIBLE

        } else {

            binding?.imgEmpty?.visibility = View.GONE

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}