package com.example.dailyactivity.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dailyactivity.R
import com.example.dailyactivity.application.DailyApplication
import com.example.dailyactivity.databinding.FragmentGraphichBinding
import com.example.dailyactivity.utils.SharedPreferencesHelper
import com.example.dailyactivity.viewmodel.TaskViewModel
import com.example.dailyactivity.viewmodel.TaskViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class GraphichFragment : Fragment() {
    private lateinit var binding: FragmentGraphichBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((requireActivity().application as DailyApplication).taskRepository)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGraphichBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = SharedPreferencesHelper.getUserId(requireContext())

        taskViewModel.setUserId(userId)

        taskViewModel.getTaskCountForToday(userId).observe(viewLifecycleOwner, Observer { todayCount ->
            taskViewModel.getTaskCountForThisWeek(userId).observe(viewLifecycleOwner, Observer { weekCount ->
                taskViewModel.getTaskCountForThisYear(userId).observe(viewLifecycleOwner, Observer { yearCount ->
                    updatePieChart(todayCount, weekCount, yearCount)
                    updateBubbleChart()
                    horizontalChart()
                })
            })
        })
    }


    private fun updatePieChart(todayCount: Int, weekCount: Int, yearCount: Int) {
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(todayCount.toFloat(), "Bugün"))
        pieEntries.add(PieEntry(weekCount.toFloat(), "Bu Hafta"))
        pieEntries.add(PieEntry(yearCount.toFloat(), "Bu yıl"))

        val dataSet = PieDataSet(pieEntries, "Görev Dağıtımı")
        dataSet.colors = listOf(
            resources.getColor(R.color.lightblue, null),
            resources.getColor(R.color.red, null),
            resources.getColor(R.color.lightpurple, null)
        )
        dataSet.valueTextSize = 16f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        binding.getTheGraph.data = data
        binding.getTheGraph.setUsePercentValues(true)
        binding.getTheGraph.description.isEnabled = false
        binding.getTheGraph.setHoleColor(Color.TRANSPARENT)
        binding.getTheGraph.setEntryLabelColor(Color.BLACK)

        binding.getTheGraph.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun updateBubbleChart() {
        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            val eventTypes = mapOf(
                "Ofis" to tasks.filter { it.tags.contains("Ofis") }.size,
                "Ev" to tasks.filter { it.tags.contains("Ev") }.size,
                "İş" to tasks.filter { it.tags.contains("İş") }.size,
                "Acil" to tasks.filter { it.tags.contains("Acil") }.size
            )
            val bubbleEntries = ArrayList<BubbleEntry>()
            var index = 0
            eventTypes.forEach { (eventType, count) ->
                val radius = (count + 1).toFloat() / 3 // Boluncuk boyutunu küçültmek için 3'e böldük
                bubbleEntries.add(BubbleEntry(index.toFloat(), count.toFloat(), radius, eventType))
                index++
            }
            val dataSet = BubbleDataSet(bubbleEntries, "Etkinlik Dağılımı")
            dataSet.colors = listOf(
                resources.getColor(R.color.lightblue, null),
                resources.getColor(R.color.red, null),
                resources.getColor(R.color.offred, null),
                resources.getColor(R.color.green, null)
            )
            dataSet.valueTextSize = 16f
            dataSet.valueTextColor = Color.WHITE

            val data = BubbleData(dataSet)
            binding.barTheGraph.data = data
            binding.barTheGraph.description.isEnabled = false
            binding.barTheGraph.setTouchEnabled(true)
            binding.barTheGraph.setDrawGridBackground(false)
            binding.barTheGraph.isDragEnabled = true
            binding.barTheGraph.setScaleEnabled(true)
            binding.barTheGraph.setPinchZoom(true)
            binding.barTheGraph.xAxis.valueFormatter = IndexAxisValueFormatter(eventTypes.keys.toList()) // Eksen etiketlerini ayarla
            binding.barTheGraph.animateY(1400, Easing.EaseInOutQuad)
        })
    }

    private fun horizontalChart() {
     taskViewModel.allTasks.observe(viewLifecycleOwner,Observer { tasks ->
         val eventTypes = mapOf(
             "Personel" to tasks.filter { it.type.contains("Personel") }.size,
             "Özel" to tasks.filter { it.type.contains("Özel") }.size,
             "Gizli" to tasks.filter { it.type.contains("Gizli") }.size,

             )

         val barEntries = ArrayList<BarEntry>()
         var index = 0
         eventTypes.forEach { (eventTypes,count) ->
             barEntries.add(BarEntry(index.toFloat(),count.toFloat()))
             index++

         }
         val dataSet = BarDataSet(barEntries,"Tip Grafiği")
         dataSet.colors = listOf(
             resources.getColor(R.color.lightpurple),
             resources.getColor(R.color.green),
             resources.getColor(R.color.red)
         )
          val data = BarData(dataSet)
         val horizontalBarChart = binding.horizontalBarChart
         horizontalBarChart.data = data
         horizontalBarChart.description.isEnabled = false
         horizontalBarChart.setTouchEnabled(true)
         horizontalBarChart.setDrawGridBackground(false)
         horizontalBarChart.isDragEnabled = true
         horizontalBarChart.setScaleEnabled(true)
         horizontalBarChart.setPinchZoom(true)
         horizontalBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(eventTypes.keys.toList()) // Eksen etiketlerini ayarla
         horizontalBarChart.animateY(1400, Easing.EaseInOutQuad)



     })
    }





}
