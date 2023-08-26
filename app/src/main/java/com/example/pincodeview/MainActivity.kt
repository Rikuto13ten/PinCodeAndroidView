package com.example.pincodeview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pincodeview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listOf(
            binding.pinCodeTextView1,
            binding.pinCodeTextView2,
            binding.pinCodeTextView3,
            binding.pinCodeTextView4
        ).also {
            it.forEachIndexed { index, textView ->
                textView.setOnClickListener {
                    binding.pinCodeEditText.requestFocus()
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(
                        binding.pinCodeEditText,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
        }

        binding.pinCodeEditText.addTextChangedListener(onTextWatcher(binding))
    }

    private fun onTextWatcher(binding: ActivityMainBinding) = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            s?.let { text ->
                binding.pinCodeTextView1.text = text.getString(0)
                binding.pinCodeTextView2.text = text.getString(1)
                binding.pinCodeTextView3.text = text.getString(2)
                binding.pinCodeTextView4.text = text.getString(3)
            }
        }
    }

    private fun CharSequence.getString(index: Int) = if (this.length > index) {
        this[index].toString()
    } else {
        " "
    }
}
