package com.example.pincodeview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pincodeview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val textViews = listOf(
            binding.pinCodeTextView1,
            binding.pinCodeTextView2,
            binding.pinCodeTextView3,
            binding.pinCodeTextView4
        ).also {
            it.forEachIndexed { _, textView ->
                textView.setOnClickListener {
                    binding.pinCodeEditText.requestFocus()
                    binding.pinCodeEditText.showKeyboard()
                }
            }
        }

        binding.pinCodeEditText.addTextChangedListener(onTextWatcher(textViews))
    }

    /**
     * テキストの変更を監視し、それに応じて複数のTextViewのテキストを更新するTextWatcherを返します。
     * CharSequenceの各文字は、提供されたTextViewリストの対応する位置に設定されます。
     * 例: CharSequenceの最初の文字は、TextViewリストの最初の項目に設定されます。
     *
     * @param textViews 更新するTextViewのリスト。
     * @return TextWatcher テキストの変更を監視するオブジェクト。
     */
    private fun onTextWatcher(textViews: List<TextView>) = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            s?.let { text ->
                textViews.forEachIndexed { index, textView ->
                    textView.text = text.getString(index)
                }
            }
        }
    }

    /**
     * 指定されたインデックスの文字を取得します。
     * インデックスがCharSequenceの長さを超えている場合、空の文字列を返します。
     *
     * @param index 取得したい文字のインデックス。
     * @return String 指定されたインデックスの文字。インデックスが範囲外の場合は空の文字列。
     */
    private fun CharSequence.getString(index: Int) = if (this.length > index) {
        this[index].toString()
    } else {
        ""
    }

    /**
     * キーボードを表示します。
     *
     * @receiver View キーボードを表示するためのビュー。
     * @return Boolean キーボードが正常に表示された場合はtrue、それ以外の場合はfalse。
     */
    private fun View.showKeyboard(): Boolean {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.showSoftInput(
            this,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}
