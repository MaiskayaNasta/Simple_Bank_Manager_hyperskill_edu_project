package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserMenuFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userMenuWelcomeTextView = view.findViewById<TextView>(R.id.userMenuWelcomeTextView)
        val username = arguments?.getString("enteredUsername")
        userMenuWelcomeTextView.text = "Welcome $username"

        val intent = (view.context as AppCompatActivity).intent
        val currentBalance = intent.getDoubleExtra("balance", 100.0)

        val newbalance = arguments?.getDouble("newbalance", currentBalance) ?: currentBalance

        val balanceButton = view.findViewById<Button>(R.id.userMenuViewBalanceButton)
        balanceButton.setOnClickListener {

            val bundle = Bundle().apply {
                putDouble("userbalance", newbalance)
            }
            this.findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment, bundle)
        }

        val transfer = view.findViewById<Button>(R.id.userMenuTransferFundsButton)
        transfer.setOnClickListener {
            val bundle = Bundle().apply {
                putDouble("userbalance", newbalance)
            }
            this.findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment, bundle)
        }

        val exgange = view.findViewById<Button>(R.id.userMenuExchangeCalculatorButton)
        exgange.setOnClickListener {
            this.findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
        }

        val payBills = view.findViewById<Button>(R.id.userMenuPayBillsButton)
        payBills.setOnClickListener {
            val bundle = Bundle().apply {
                putDouble("userbalance", newbalance)
            }
            this.findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment, bundle)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}