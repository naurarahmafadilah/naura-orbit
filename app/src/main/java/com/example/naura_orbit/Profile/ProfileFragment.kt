package com.example.naura_orbit.Profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.naura_orbit.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Bind TextViews
        val tvProfileName: TextView = view.findViewById(R.id.tvProfileName)
        val tvProfileRole: TextView = view.findViewById(R.id.tvProfileRole)

        // Load values
        tvProfileName.text = sharedPref.getString("reg_user", "Naura Rahma")
        tvProfileRole.text = sharedPref.getString("reg_role", "UI/UX Designer • 2457301111")

        val btnBack: ImageButton = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val btnInstagram: LinearLayout = view.findViewById(R.id.btnInstagram)
        btnInstagram.setOnClickListener {
            val url = "https://instagram.com/naurarahmafadilah"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btnEmail: LinearLayout = view.findViewById(R.id.btnEmail)
        btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:naura24si@mahasiswa.pcr.ac.id")
            }
            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(context, "Tidak ada aplikasi email yang terpasang", Toast.LENGTH_SHORT).show()
            }
        }

        val btnGithub: LinearLayout = view.findViewById(R.id.btnGithub)
        btnGithub.setOnClickListener {
            val url = "https://github.com/naura-orbit"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btnSettings: LinearLayout = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        val btnHelpCenter: LinearLayout = view.findViewById(R.id.btnHelpCenter)
        btnHelpCenter.setOnClickListener {
            startActivity(Intent(requireContext(), HelpCenterActivity::class.java))
        }

        val btnEditProfile: MaterialButton = view.findViewById(R.id.btnEditProfile)
        btnEditProfile.setOnClickListener {
            val context = requireContext()
            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(50, 40, 50, 10)
            }

            val inputName = com.google.android.material.textfield.TextInputEditText(context).apply {
                hint = "Nama Lengkap"
                setText(sharedPref.getString("reg_user", "Naura Rahma"))
            }
            val layoutName = com.google.android.material.textfield.TextInputLayout(context).apply {
                addView(inputName)
                hint = "Nama Lengkap"
            }
            layout.addView(layoutName)

            val spacer = View(context).apply {
                minimumHeight = 24
            }
            layout.addView(spacer)

            val inputRole = com.google.android.material.textfield.TextInputEditText(context).apply {
                hint = "Pekerjaan / NIM"
                setText(sharedPref.getString("reg_role", "UI/UX Designer • 2457301111"))
            }
            val layoutRole = com.google.android.material.textfield.TextInputLayout(context).apply {
                addView(inputRole)
                hint = "Pekerjaan / NIM"
            }
            layout.addView(layoutRole)

            MaterialAlertDialogBuilder(context)
                .setTitle("Ubah Profil")
                .setView(layout)
                .setPositiveButton("Simpan") { _, _ ->
                    val newName = inputName.text.toString().trim()
                    val newRole = inputRole.text.toString().trim()

                    if (newName.isNotEmpty() && newRole.isNotEmpty()) {
                        sharedPref.edit().apply {
                            putString("reg_user", newName)
                            putString("reg_role", newRole)
                            apply()
                        }
                        tvProfileName.text = newName
                        tvProfileRole.text = newRole
                        Toast.makeText(context, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Nama dan Pekerjaan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}