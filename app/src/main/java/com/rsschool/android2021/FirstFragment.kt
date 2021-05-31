package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var editMin: EditText? = null
    private var editMax: EditText? = null
    private var listener: OnFirstFragmentDataListener? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        editMin = view.findViewById(R.id.min_value)
        editMax = view.findViewById(R.id.max_value)

        val textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                generateButton?.isEnabled = isValidInput()
            }
        }
        editMin?.addTextChangedListener (textWatcher)
        editMax?.addTextChangedListener (textWatcher)

        generateButton?.isEnabled = false
        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min: String = editMin?.text.toString()
            val max: String = editMax?.text.toString()
            listener?.onOpenSecondFragment(min.toInt(), max.toInt())
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            val result = listener?.onGetPreviousNumber()
            if (result != null) {
                arguments?.putInt(PREVIOUS_RESULT_KEY, result)
                previousResult?.text = "Previous result: $result"
            }
        }
        catch (e: Exception){
            println("Exception: ${e.message}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is OnFirstFragmentDataListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFirstFragmentDataListener"
            )
        }
    }

    private fun isValidInput () : Boolean {
        try {
            if ( editMin?.text?.isNotBlank() == true && editMax?.text?.isNotBlank() == true ) {
                val min = editMin?.text.toString().toInt()
                val max = editMax?.text.toString().toInt()
                return min in 0 until max
            }
        }
        catch (e: Exception){
            println("Exception: ${e.message}")
        }
        return false
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}