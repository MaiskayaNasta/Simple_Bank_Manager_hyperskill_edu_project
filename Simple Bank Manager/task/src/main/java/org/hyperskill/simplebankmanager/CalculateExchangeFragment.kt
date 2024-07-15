package org.hyperskill.simplebankmanager

import android.app.Fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculateExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculateExchangeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defaultMap = mapOf("EUR" to mapOf("GBP" to 0.5, "USD" to 2.0),
            "GBP" to mapOf("EUR" to 2.0, "USD" to 4.0), "USD" to mapOf("EUR" to 0.5, "GBP" to 0.25))
        val intent = (view.context as AppCompatActivity).intent
        val exchangeMap = intent.extras?.getSerializable("exchangeMap") as? Map<String, Map<String, Double>> ?: defaultMap
        val symbolsMap: Map<String, Char> = mapOf("EUR" to '€', "USD" to '$', "GBP" to '£')

        val currencyArray = resources.getStringArray(R.array.currency_array)
        val spinnerFrom = view.findViewById<Spinner>(R.id.calculateExchangeFromSpinner)
        val adapterFrom = SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyArray)
        spinnerFrom.adapter = adapterFrom
        val spinnerTo = view.findViewById<Spinner>(R.id.calculateExchangeToSpinner)
        val adapterTo = SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyArray)
        spinnerTo.adapter = adapterTo

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val selectedItemFrom = adapterFrom.getItem(spinnerFrom.selectedItemPosition)
                val selectedItemTo = adapterTo.getItem(spinnerTo.selectedItemPosition)

                if (selectedItemFrom == selectedItemTo) {
                    Toast.makeText(context, "Cannot convert to same currency", Toast.LENGTH_SHORT).show()
                    if (spinnerTo.selectedItemPosition + 1 < adapterTo.count) {
                        spinnerTo.setSelection(spinnerTo.selectedItemPosition + 1)
                    } else {
                        spinnerTo.setSelection(0)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
        spinnerTo.onItemSelectedListener = listener

        val calculateButton = view.findViewById<Button>(R.id.calculateExchangeButton)
        calculateButton.setOnClickListener {
            val amount = view.findViewById<EditText>(R.id.calculateExchangeAmountEditText).text.toString()
            if(amount.isEmpty()) {
                Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show()
            } else {
                val shownText = view.findViewById<TextView>(R.id.calculateExchangeDisplayTextView)
                val choice1 = adapterFrom.getItem(spinnerFrom.selectedItemPosition).toString()
                val choice2 = adapterTo.getItem(spinnerTo.selectedItemPosition).toString()

                val result = amount.toDouble() * exchangeMap[choice1]!!.get(choice2)!!.toDouble()
                shownText.text = "${symbolsMap[choice1]}${String.format("%.2f", amount.toDouble())} = ${symbolsMap[choice2]}${String.format("%.2f", result)}"
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_calculateExchangeFragment_to_userMenuFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculateExchangeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculateExchangeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}