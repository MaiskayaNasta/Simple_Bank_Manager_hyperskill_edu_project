package org.hyperskill.simplebankmanager

import android.app.Fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFundsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFundsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_transfer_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transferButton = view.findViewById<Button>(R.id.transferFundsButton)
        val accountName = view.findViewById<EditText>(R.id.transferFundsAccountEditText)
        val accountSum = view.findViewById<EditText>(R.id.transferFundsAmountEditText)

        transferButton.setOnClickListener {
            val isWrongName = !accountName.text.toString().matches("(sa|ca)\\d{4}".toRegex())
            val isWrongSum = accountSum.text.toString().isEmpty() || accountSum.text.toString().toDouble() <= 0.0

            if(isWrongName) {
                accountName.setError("Invalid account number")
            } else if(isWrongSum) {
                accountSum.setError("Invalid amount")
            } else if(isWrongName && isWrongSum) {
                accountName.setError("Invalid account number")
                accountSum.setError("Invalid amount")
            } else {
                val inputSum = accountSum.text.toString().toDouble()

                val intent = (view.context as AppCompatActivity).intent
                val balance = intent.getDoubleExtra("balance", 100.0)

                val currentBalance = arguments?.getDouble("userbalance") ?: balance
                if(inputSum > currentBalance) {
                    Toast.makeText(context, "Not enough funds to transfer $${String.format("%.2f", inputSum)}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Transferred \$${String.format("%.2f", inputSum)} to account ${accountName.text}", Toast.LENGTH_SHORT).show()
                    val newBalance = currentBalance - inputSum
                    val bundle = Bundle().apply {
                        putDouble("newbalance", newBalance)
                    }
                    findNavController().navigate(R.id.action_transferFundsFragment_to_userMenuFragment, bundle)
                }
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_transferFundsFragment_to_userMenuFragment)
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
         * @return A new instance of fragment TransferFundsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransferFundsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}