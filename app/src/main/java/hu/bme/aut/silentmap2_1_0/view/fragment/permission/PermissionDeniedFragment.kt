package hu.bme.aut.silentmap2_1_0.view.fragment.permission

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentPermissionDeniedBinding
import hu.nemeth.android.silentmap.databinding.FragmentSplashBinding

class PermissionDeniedFragment: Fragment() {

    private lateinit var binding: FragmentPermissionDeniedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPermissionDeniedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}