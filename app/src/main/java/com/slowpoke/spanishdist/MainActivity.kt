package com.slowpoke.spanishdist

import android.net.http.HttpException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.slowpoke.spanishdist.databinding.ActivityMainBinding
import com.slowpoke.spanishdist.dictWordEntry.WordEntry
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

const val TAG = "RetrofitActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.isVisible = false;

        binding.btnSearch.setOnClickListener {
            val searchWord = binding.etSearchWord.text.toString()

            lifecycleScope.launch {
                binding.progressBar.isVisible = true;
                val response = try {
                    RetrofitInstance.api.getDictEntry(searchWord, RetrofitInstance.API_KEY)
                } catch (e: IOException) {
                    Log.d(TAG, "Check your internet connection")
                    binding.progressBar.isVisible = false;
                    return@launch
                } catch (e: HttpException) {
                    Log.d(TAG, "Server response unsuccessful")
                    binding.progressBar.isVisible = false;
                    return@launch
                }
                binding.progressBar.isVisible = false;
                if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()
                ) {
                    val firstWordEntry = response.body()!![0]
                    val translation = firstWordEntry.shortdef[0]
                    binding.tvTranslation.text = translation
                } else
                    binding.tvTranslation.text = getString(R.string.entry_not_found)

            }
        }

    }


}