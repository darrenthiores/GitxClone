package com.icebeal.gitxclone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icebeal.gitxclone.data.model.User
import com.icebeal.gitxclone.databinding.FragmentFavoriteBinding
import com.icebeal.gitxclone.ui.adapter.UserAdapter
import com.icebeal.gitxclone.ui.viewModel.UserViewModel

class FavoriteFragment : Fragment() {

    private var _binding:FragmentFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding?.rvFavorite?.adapter = userAdapter
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(activity)

        userViewModel.queryAll().observe(viewLifecycleOwner, {

            if(it!=null){

                val arrayList = ArrayList<User>(it)

                userAdapter.setData(arrayList)

            }

            if(it.isEmpty()){

                binding?.imgEmpty?.visibility = View.VISIBLE

            }

        })

        userAdapter.setOnItemClick(object : UserAdapter.OnItemClick{

            override fun onItemClicked(username: String) {

                val toDetailActivity = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity()
                toDetailActivity.username = username

                findNavController().navigate(toDetailActivity)

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}