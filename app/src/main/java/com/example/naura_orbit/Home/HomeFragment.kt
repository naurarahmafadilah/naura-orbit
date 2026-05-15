package com.example.naura_orbit.Home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.naura_orbit.Home.pertemuan_3.LoginActivity
import com.example.naura_orbit.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi User dari Shared Preferences
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        binding.tvUserName.text = sharedPref.getString("reg_user", "Naura Rahma")

        // 2. Setup Chip Group (6 Kategori: Warga, Keluarga, Kematian, Kelahiran, Pindah, Lainnya)
        updateChipVisuals()
        binding.chipGroupCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            updateChipVisuals()
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                Toast.makeText(context, "Kategori: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Setup Klik Titik Tiga (Popup Menu) di Samping Profil
        binding.menuTopRight.setOnClickListener { v ->
            val popup = PopupMenu(requireContext(), v)
            popup.menu.add("Profil Saya")
            popup.menu.add("Pengaturan Akun")
            popup.menu.add("Pusat Bantuan")

            popup.setOnMenuItemClickListener { item ->
                Toast.makeText(context, "Membuka ${item.title}", Toast.LENGTH_SHORT).show()
                true
            }
            popup.show()
        }

        // 4. Inisialisasi Klik Menu Layanan & List Informasi
        setupMenuClick()
        setupInfoList()

        // 5. Logic Tombol Logout
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Keluar")
                .setMessage("Yakin ingin keluar dari sistem?")
                .setPositiveButton("Ya") { _, _ ->
                    sharedPref.edit().clear().apply()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun updateChipVisuals() {
        // Mengatur warna chip (Biru Tua jika dipilih, Biru Muda jika tidak)
        for (i in 0 until binding.chipGroupCategory.childCount) {
            val chip = binding.chipGroupCategory.getChildAt(i) as Chip
            if (chip.isChecked) {
                chip.chipBackgroundColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#2F5DA9"))
                chip.setTextColor(Color.WHITE)
                chip.chipStrokeWidth = 0f
            } else {
                chip.chipBackgroundColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#F0F7FF"))
                chip.setTextColor(Color.parseColor("#2F5DA9"))
                chip.chipStrokeColor = android.content.res.ColorStateList.valueOf(Color.parseColor("#D1E4FF"))
                chip.chipStrokeWidth = 2f
            }
        }
    }

    private fun setupMenuClick() {
        // Listener klik untuk 6 kartu layanan utama
        binding.cardWarga.setOnClickListener { Toast.makeText(context, "Membuka Data Warga", Toast.LENGTH_SHORT).show() }
        binding.cardKeluarga.setOnClickListener { Toast.makeText(context, "Membuka Data Keluarga", Toast.LENGTH_SHORT).show() }
        binding.cardKematian.setOnClickListener { Toast.makeText(context, "Membuka Data Kematian", Toast.LENGTH_SHORT).show() }
        binding.cardKelahiran.setOnClickListener { Toast.makeText(context, "Membuka Data Kelahiran", Toast.LENGTH_SHORT).show() }
        binding.cardPindah.setOnClickListener { Toast.makeText(context, "Membuka Data Pindah", Toast.LENGTH_SHORT).show() }
        binding.menuMore.setOnClickListener { Toast.makeText(context, "Membuka Menu Lainnya", Toast.LENGTH_SHORT).show() }
    }

    private fun setupInfoList() {
        val infoList = arrayOf("Kebijakan Privasi", "Tentang NusaData", "Pusat Bantuan", "Syarat & Ketentuan", "Versi Aplikasi v1.0.4")
        val container = binding.containerInfo
        container.removeAllViews()

        infoList.forEachIndexed { index, title ->
            val itemView = TextView(requireContext()).apply {
                text = title
                textSize = 14f
                setPadding(40, 40, 40, 40)
                setTextColor(Color.parseColor("#455A64"))

                // Efek klik ripple
                val outValue = android.util.TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                setBackgroundResource(outValue.resourceId)

                setOnClickListener {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(title)
                        .setMessage("Detail informasi mengenai $title.")
                        .setPositiveButton("Ok", null)
                        .show()
                }
            }
            container.addView(itemView)

            // Tambahkan garis pembatas (divider) kecuali item terakhir
            if (index < infoList.size - 1) {
                val divider = View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                    setBackgroundColor(Color.parseColor("#EEEEEE"))
                }
                container.addView(divider)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}