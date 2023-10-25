package com.slowpoke.spanishdist

import android.net.http.HttpException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.lifecycleScope
import com.slowpoke.spanishdist.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "RetrofitActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val searchWord = binding.etSearchWord.text.toString()

            lifecycleScope.launch {

                val response = try {
                    RetrofitInstance.api.getDictEntry(searchWord, RetrofitInstance.API_KEY)
                } catch (e: IOException) {
                    Log.d(TAG, "Check your internet connection")
                    return@launch
                } catch (e: HttpException) {
                    Log.d(TAG, "Server response unsuccessful")
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    val firstWordEntry = response.body()!![0]
                    val translation = firstWordEntry.shortdef[0]
                    binding.tvTranslation.text = translation
                }

            }
        }

    }


}