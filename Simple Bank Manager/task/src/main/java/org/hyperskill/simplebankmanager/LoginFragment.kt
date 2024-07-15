package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginUsername = view.findViewById<EditText>(R.id.loginUsername)
        val loginPassword = view.findViewById<EditText>(R.id.loginPassword)
        val loginButton = view.findViewById<Button>(R.id.loginButton)


        loginButton.setOnClickListener {
            val enteredUsername = loginUsername.text.toString()
            val enteredPassword = loginPassword.text.toString()

            val intent = (view.context as AppCompatActivity).intent
            val testUsername = intent.extras?.getString("username") ?: "Lara"
            val testPassword = intent.extras?.getString("password") ?: "1234"

            if (enteredUsername == testUsername && enteredPassword == testPassword) {
                Toast.makeText(context, "logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "invalid credentials", Toast.LENGTH_SHORT).show()
            }
            val bundle = Bundle().apply {
                putString("enteredUsername", enteredUsername)
            }
            this.findNavController().navigate(R.id.action_loginFragment_to_userMenuFragment, bundle)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}