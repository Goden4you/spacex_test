package com.example.spacextest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacextest.LayoutUtils
import com.example.spacextest.R
import com.example.spacextest.SpaceXTestApplication
import kotlinx.android.synthetic.main.mission_fragment.*

class MissionsFragment : Fragment() {
    private lateinit var missionsAdapter: MissionsAdapter

    private val missionsViewModel: MissionsViewModel by viewModels {
        MissionsViewModel.MissionsViewModelFactory(
            ((requireActivity().application) as SpaceXTestApplication).getMissionsUseCase,
            ((requireActivity().application) as SpaceXTestApplication).successedMissionsMapper
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        missionsAdapter = MissionsAdapter(requireContext())

        missionsViewModel.getMissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mission_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        missionsViewModel.missions.observe(viewLifecycleOwner, {
            missionsAdapter.submitUpdate(it)
        })

        missionsViewModel.dataLoading.observe(viewLifecycleOwner, { loading ->
            when (loading) {
                true -> LayoutUtils.crossFade(pbLoading, rvMissions)
                false -> LayoutUtils.crossFade(rvMissions, pbLoading)
            }
        })

        rvMissions.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = missionsAdapter
        }

        btnSortOrder.setOnClickListener {
            missionsViewModel.changeSortOrder()
            rvMissions.scrollToPosition(0)
        }

        missionsViewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                getString(R.string.an_error_has_occurred, it),
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}