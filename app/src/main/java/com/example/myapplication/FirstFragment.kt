package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?




    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener()
        {
            if (binding.etUserName.text.toString().equals("a") && binding.etPassword.text.toString().equals("b")){
                Toast.makeText(activity, ":D", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

            //Toast.makeText(activity, binding.etUserName.text, Toast.LENGTH_LONG).show()
            Toast.makeText(activity, binding.etPassword.text, Toast.LENGTH_LONG).show()


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}