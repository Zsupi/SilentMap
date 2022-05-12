package hu.nemeth.android.silentmap.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentSplashBinding
import permissions.dispatcher.*

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        YoYo.with(Techniques.Bounce)
            .duration(1000)
            .repeat(2)
            .playOn(binding.splashLogo)
        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().navigate(R.id.action_splashFragment_to_doNotDisturbPermissionFragment)
            }
        }, 2000)
        return binding.root
    }


}


