package com.example.mad

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapters.FeelingAdapter
import com.example.mad.Adapters.QuoteAdapter
import com.example.mad.Retrofit.RetrofitBuilder
import com.example.mad.databinding.FragmentMainBinding
import com.example.mad.managers.UserManager
import com.example.mad.models.Feeling
import com.example.mad.models.Quote
import com.example.navigation.Viewmodel.DataViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerViewFeeling: RecyclerView
    private lateinit var recyclerViewQuote: RecyclerView
    private lateinit var feelingAdapter: FeelingAdapter
    private lateinit var quoteAdapter: QuoteAdapter
    private lateinit var dataViewModel: DataViewModel

    lateinit var userManager: UserManager
    var nick = ""
    var avatar = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        userManager = UserManager(requireContext())
        feelingAdapter = FeelingAdapter()
        quoteAdapter = QuoteAdapter()

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        // Получение имя и изображение пользователя
        lifecycleScope.launch {
            nick = userManager.userNickFlow.first()
            avatar = userManager.userAvatarFlow.first()
        }

        binding.Welcome.text = "С возвращением, $nick!"
        binding.avatar.clipToOutline = true

        Picasso.get()
            .load(avatar)
            .into(binding.avatar)

        recyclerViewFeeling = binding.recyclerViewFeeling
        recyclerViewFeeling.adapter = feelingAdapter
        recyclerViewFeeling.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerViewQuote = binding.recyclerViewQuote
        recyclerViewQuote.adapter = quoteAdapter
        recyclerViewQuote.layoutManager = LinearLayoutManager(requireContext())

        dataViewModel.feelingsData.observe(viewLifecycleOwner) { feelings ->
            feelings?.let {
                feelingAdapter.setData(it)
            }
        }

        dataViewModel.quotesData.observe(viewLifecycleOwner) { quotes ->
            quotes?.let {
                quoteAdapter.setData(it)
            }
        }

        dataViewModel.fetchData()

    }

}