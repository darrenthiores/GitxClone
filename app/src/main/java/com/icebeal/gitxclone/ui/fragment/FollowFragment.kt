package com.icebeal.gitxclone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.icebeal.gitxclone.databinding.FragmentFollowBinding
import com.icebeal.gitxclone.ui.adapter.UserAdapter
import com.icebeal.gitxclone.ui.viewModel.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding:FragmentFollowBinding? = null
    private val binding get() = _binding

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFollowBinding.inflate(inflater, container, false)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val index = arguments?.getInt(INDEX, 0)
        val username = arguments?.getString(USERNAME)

        val userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding?.rvFollow?.adapter = userAdapter
        binding?.rvFollow?.layoutManager = LinearLayoutManager(activity)

        when(index){

            0 -> {

                detailViewModel.getFollower(username.toString())

                detailViewModel.userFollower().observe(viewLifecycleOwner, {

                    if(it!=null){

                        userAdapter.setData(it)

                    }

                    if(it.isEmpty()){

                        binding?.imgEmpty?.visibility = View.VISIBLE

                    }

                })

            }

            1 -> {

                detailViewModel.getFollowing(username.toString())

                detailViewModel.userFollowing().observe(viewLifecycleOwner, {

                    if(it!=null){

                        userAdapter.setData(it)

                    }

                    if(it.isEmpty()){

                        binding?.imgEmpty?.visibility = View.VISIBLE

                    }

                })

            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val INDEX = "index"
        private const val USERNAME = "username"

        @JvmStatic
        fun newInstance(index: Int, username: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(INDEX, index)
                    putString(USERNAME, username)
                }
            }
    }
}