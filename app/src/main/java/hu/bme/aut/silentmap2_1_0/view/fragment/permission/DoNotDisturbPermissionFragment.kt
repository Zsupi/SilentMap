package hu.bme.aut.silentmap2_1_0.view.fragment.permission


import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import hu.nemeth.android.silentmap.R
import hu.nemeth.android.silentmap.databinding.FragmentDoNotDisturbPermissionBinding

class DoNotDisturbPermissionFragment(): DialogFragment() {

    private lateinit var binding: FragmentDoNotDisturbPermissionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDoNotDisturbPermissionBinding.inflate(inflater, container, false)
        dialog?.setTitle("doNotDisturbTitle")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bAccept.setOnClickListener {
            permissionDoNotDisturb()

        }
    }

    override fun onResume() {
        super.onResume()
        if (doNotDisturbGranted()){
            findNavController().navigate(R.id.action_doNotDisturbPermissionFragment_to_viewPagerFragment)
            dismiss()
        }
    }

    private fun permissionDoNotDisturb(){
        val intent = Intent(
            Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
        )
        startActivity(intent)
    }

    private fun doNotDisturbGranted(): Boolean {
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.isNotificationPolicyAccessGranted
    }


}