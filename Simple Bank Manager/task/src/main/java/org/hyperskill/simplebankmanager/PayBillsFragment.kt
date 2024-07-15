package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import android.app.AlertDialog
import android.app.Fragment
import androidx.core.app.BundleCompat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PayBillsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PayBillsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_pay_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val payButton = view.findViewById<Button>(R.id.payBillsShowBillInfoButton)
        val code = view.findViewById<EditText>(R.id.payBillsCodeInputEditText)

        val defaultBillInfoMap = mapOf(
            "ELEC" to Triple("Electricity", "ELEC", 45.0),
            "GAS" to Triple("Gas", "GAS", 20.0),
            "WTR" to Triple("Water", "WTR", 25.5)
        )
        var flag = false
        var newBalance: Double = 0.0
        var bundle = Bundle()

        payButton.setOnClickListener {
            val intent = (view.context as AppCompatActivity).intent
            val billInfoMap = intent.extras?.getSerializable("billInfo") as? Map<String, Triple<String, String, Double>> ?: defaultBillInfoMap
            val enteredCode = code.text.toString()

            if(enteredCode !in billInfoMap.keys) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error").setMessage("Wrong code").setPositiveButton(android.R.string.ok) { _, _ ->
                        Toast.makeText(requireContext(), "Ok", Toast.LENGTH_SHORT).show() }.show()
            } else {
                val billName = (billInfoMap[enteredCode])?.first
                val billCode = (billInfoMap[enteredCode])?.second
                val billAmount = (billInfoMap[enteredCode])?.third

                AlertDialog.Builder(requireContext())
                    .setTitle("Bill info")
                    .setMessage("Name: $billName\n" +
                            "BillCode: $billCode\n" +
                            "Amount: $${String.format("%.2f", billAmount)}")
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        val intent = (view.context as AppCompatActivity).intent
                        val balance = intent.getDoubleExtra("balance", 100.0)
                        val currentBalance = if(flag == true) newBalance else arguments?.getDouble("userbalance") ?: balance

                        Toast.makeText(requireContext(), "Confirm", Toast.LENGTH_SHORT).show()
                        if(billAmount!! < currentBalance) {
                            Toast.makeText(context, "Payment for bill $billName, was successful", Toast.LENGTH_SHORT).show()
                            flag = true
                            newBalance = currentBalance - billAmount
                            bundle = Bundle().apply {
                                putDouble("newbalance", newBalance)
                            }
                        } else {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Error")
                                .setMessage("Not enough funds")
                                .setPositiveButton(android.R.string.ok) { _, _ ->
                                    Toast.makeText(requireContext(), "Ok", Toast.LENGTH_SHORT).show()
                                }
                                .show()
                        }
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog, which ->
                        Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
                    }.show()
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (flag == true) {
                    findNavController().navigate(R.id.action_payBillsFragment_to_userMenuFragment, bundle)
                } else {
                    findNavController().navigate(R.id.action_payBillsFragment_to_userMenuFragment)
                }
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
         * @return A new instance of fragment PayBillsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PayBillsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}