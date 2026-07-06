package com.example.naura_orbit.Home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import androidx.lifecycle.lifecycleScope // Tambahan untuk Coroutine API
import androidx.recyclerview.widget.LinearLayoutManager // Tambahan untuk List Berita
import com.example.naura_orbit.Home.pertemuan_3.LoginActivity
import com.example.naura_orbit.Profile.SettingsActivity
import com.example.naura_orbit.Profile.HelpCenterActivity
import com.example.naura_orbit.Home.pertemuan_10.WargaActivity
import com.example.naura_orbit.Home.pertemuan_10.keluarga.KeluargaActivity
import com.example.naura_orbit.Home.pertemuan_11.api_news.NewsAdapter
import com.example.naura_orbit.Home.pertemuan_11.NewsModel
import com.example.naura_orbit.Home.pertemuan_10.kelahiran.KelahiranActivity
import com.example.naura_orbit.Home.pertemuan_10.kematian.KematianActivity
import com.example.naura_orbit.Home.pertemuan_10.pindah.PindahActivity
import com.example.naura_orbit.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch // Tambahan untuk Coroutine Launch

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
                when (chip.id) {
                    com.example.naura_orbit.R.id.chipWarga -> {
                        startActivity(Intent(requireContext(), WargaActivity::class.java))
                    }
                    com.example.naura_orbit.R.id.chipKeluarga -> {
                        startActivity(Intent(requireContext(), KeluargaActivity::class.java))
                    }
                    com.example.naura_orbit.R.id.chipKematian -> {
                        startActivity(Intent(requireContext(), KematianActivity::class.java))
                    }
                    com.example.naura_orbit.R.id.chipKelahiran -> {
                        startActivity(Intent(requireContext(), KelahiranActivity::class.java))
                    }
                    com.example.naura_orbit.R.id.chipPindah -> {
                        startActivity(Intent(requireContext(), PindahActivity::class.java))
                    }
                    com.example.naura_orbit.R.id.chipLainnya -> {
                        Toast.makeText(context, "Membuka Menu Lainnya", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 3. Setup Klik Titik Tiga (Popup Menu) di Samping Profil
        binding.menuTopRight.setOnClickListener { v ->
            val popup = PopupMenu(requireContext(), v)
            popup.menu.add("Profil Saya")
            popup.menu.add("Pengaturan Akun")
            popup.menu.add("Pusat Bantuan")

            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Profil Saya" -> {
                        val baseNav = activity?.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(com.example.naura_orbit.R.id.bottom_nav_view)
                        baseNav?.selectedItemId = com.example.naura_orbit.R.id.nav_profile
                    }
                    "Pengaturan Akun" -> {
                        startActivity(Intent(requireContext(), SettingsActivity::class.java))
                    }
                    "Pusat Bantuan" -> {
                        startActivity(Intent(requireContext(), HelpCenterActivity::class.java))
                    }
                }
                true
            }
            popup.show()
        }

        // 4. Inisialisasi Klik Menu Layanan & List Informasi
        setupMenuClick()
        setupQrClick()
        setupInfoList()

        // 🟢 5. JALANKAN LOGIKA DATA LIST BERITA API PUBLIC (PERTEMUAN 11)
        setupNewsRecyclerView()
        fetchNewsData()

        // 6. Logic Tombol Logout
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

    private fun setupQrClick() {
        val showQrDialog = View.OnClickListener { v ->
            val (title, qrUrl) = when (v.id) {
                com.example.naura_orbit.R.id.qrWarga -> {
                    Pair("QR Code Total Warga", "https://api.qrserver.com/v1/create-qr-code/?size=500x500&color=2F5DA9&data=Total%20Warga%20NusaData%3A%201.245%20Jiwa")
                }
                com.example.naura_orbit.R.id.qrLaki -> {
                    Pair("QR Code Laki-Laki", "https://api.qrserver.com/v1/create-qr-code/?size=500x500&color=1F3E73&data=Warga%20Laki-Laki%20NusaData%3A%20620%20Jiwa")
                }
                com.example.naura_orbit.R.id.qrPerempuan -> {
                    Pair("QR Code Perempuan", "https://api.qrserver.com/v1/create-qr-code/?size=500x500&color=E91E63&data=Warga%20Perempuan%20NusaData%3A%20625%20Jiwa")
                }
                else -> {
                    Pair("QR Code Demografi Desa", "https://api.qrserver.com/v1/create-qr-code/?size=500x500&color=2F5DA9&data=Desa%20NusaData%20-%20Ringkasan%20Demografi%3A%0A-%20Total%20Warga%3A%201.245%20Jiwa%0A-%20Laki-Laki%3A%20620%20Jiwa%20(49.8%25)%0A-%20Perempuan%3A%20625%20Jiwa%20(50.2%25)%0A-%20Klasifikasi%20Usia%3A%20Mayoritas%20Usia%20Produktif%20(15-64%20Tahun)")
                }
            }
            val context = requireContext()
            val dialogView = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = android.view.Gravity.CENTER
                setPadding(50, 60, 50, 60)
            }
            val qrImageView = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(450, 450)
                adjustViewBounds = true
            }
            
            // Memuat QR Code scannable dinamis via Glide
            Glide.with(context)
                .load(qrUrl)
                .placeholder(com.example.naura_orbit.R.drawable.ic_qr_code)
                .error(com.example.naura_orbit.R.drawable.ic_qr_code)
                .into(qrImageView)

            dialogView.addView(qrImageView)
            
            val titleView = TextView(context).apply {
                text = title
                typeface = resources.getFont(com.example.naura_orbit.R.font.poppins_bold)
                textSize = 18f
                setTextColor(Color.parseColor("#1F3E73"))
                gravity = android.view.Gravity.CENTER
                setPadding(0, 20, 0, 8)
            }
            dialogView.addView(titleView)
            
            val descView = TextView(context).apply {
                text = "Pindai kode QR di atas menggunakan kamera HP atau Google Lens Anda untuk melihat detail teks ringkasan data."
                typeface = resources.getFont(com.example.naura_orbit.R.font.poppins_regular)
                textSize = 12f
                setTextColor(Color.parseColor("#7F8C8D"))
                gravity = android.view.Gravity.CENTER
            }
            dialogView.addView(descView)
            MaterialAlertDialogBuilder(context)
                .setView(dialogView)
                .setPositiveButton("Selesai", null)
                .show()
        }
        binding.qrWarga.setOnClickListener(showQrDialog)
        binding.qrLaki.setOnClickListener(showQrDialog)
        binding.qrPerempuan.setOnClickListener(showQrDialog)
        binding.qrDemografi.setOnClickListener(showQrDialog)
    }

    private fun updateChipVisuals() {
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
        binding.cardWarga.setOnClickListener {
            val intent = Intent(requireContext(), WargaActivity::class.java)
            startActivity(intent)
        }

        binding.cardKeluarga.setOnClickListener {
            val intent = Intent(requireContext(), com.example.naura_orbit.Home.pertemuan_10.keluarga.KeluargaActivity::class.java)
            startActivity(intent)
        }

        binding.cardKematian.setOnClickListener {
            val intent = Intent(requireContext(), com.example.naura_orbit.Home.pertemuan_10.kematian.KematianActivity::class.java)
            startActivity(intent)
        }

        binding.cardKelahiran.setOnClickListener {
            val intent = Intent(requireContext(), com.example.naura_orbit.Home.pertemuan_10.kelahiran.KelahiranActivity::class.java)
            startActivity(intent)
        }

        binding.cardPindah.setOnClickListener {
            val intent = Intent(requireContext(), com.example.naura_orbit.Home.pertemuan_10.pindah.PindahActivity::class.java)
            startActivity(intent)
        }

        binding.menuMore.setOnClickListener { Toast.makeText(context, "Membuka Menu Lainnya", Toast.LENGTH_SHORT).show() }
    }

    // 🟢 6. FUNGSI BARU PERTEMUAN 11: MENYIAPKAN INSTANCE RECYCLERVIEW BERITA
    private fun setupNewsRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.setHasFixedSize(true)
    }

    // 🟢 7. FUNGSI BARU PERTEMUAN 11: MENARIK DATA BERITA SECARA ASINKRONUS (COROUTINE)
    private fun fetchNewsData() {
        // Menggunakan berita static lokal bertema kependudukan & Bina Desa NusaData
        val listBeritaDesa = listOf(
            NewsModel(
                id = "1",
                author = "Digitalisasi Akta Kelahiran dan KIA Desa NusaData Resmi Diluncurkan",
                download_url = "android.resource://com.example.naura_orbit/drawable/futuristic_hub",
                content = "Desa NusaData resmi meluncurkan program digitalisasi akta kelahiran dan Kartu Identitas Anak (KIA). Kepala Desa menyampaikan bahwa langkah ini bertujuan untuk mempermudah pelayanan administrasi warga secara cepat, transparan, dan terintegrasi secara online. Warga kini dapat mengajukan pendaftaran secara mandiri langsung melalui aplikasi NusaData tanpa perlu antre lama di kantor balai desa. Semua berkas akan diverifikasi secara digital oleh petugas kependudukan secara efisien."
            ),
            NewsModel(
                id = "2",
                author = "Pemutakhiran Data Warga Secara Kolektif Menuju NusaData Mandiri 2026",
                download_url = "android.resource://com.example.naura_orbit/drawable/desa",
                content = "Dalam rangka menyongsong tahun kemandirian data kependudukan 2026, pemerintah desa menyelenggarakan pemutakhiran data secara kolektif. Kegiatan ini melibatkan seluruh ketua RT/RW dan kader PKK untuk mencatat perubahan status perkawinan, kepindahan, kematian, dan kelahiran warga. Dengan data kependudukan yang akurat, pembagian bantuan sosial dan fasilitas kesehatan dapat tersalurkan tepat sasaran bagi warga yang benar-benar membutuhkan."
            ),
            NewsModel(
                id = "3",
                author = "Sensus Penduduk dan Pemetaan Wilayah Desa Menggunakan Aplikasi Orbit",
                download_url = "android.resource://com.example.naura_orbit/drawable/logo_nusadata",
                content = "Aplikasi Orbit secara resmi diintegrasikan untuk menunjang kegiatan sensus penduduk di Desa NusaData. Melalui sistem ini, pemetaan demografis dan analisis kebutuhan infrastruktur desa seperti akses air bersih, listrik, dan jalan raya dapat dipantau langsung dalam bentuk grafik visual. Kerja sama ini diharapkan mempercepat pembangunan fisik dan pelayanan kesejahteraan warga secara digital."
            )
        )

        // Pasangkan list data ke adapter RecyclerView
        val newsAdapter = NewsAdapter(listBeritaDesa)
        binding.rvNews.adapter = newsAdapter
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

                val outValue = android.util.TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                setBackgroundResource(outValue.resourceId)

                setOnClickListener {
                    val detailMessage = when (title) {
                        "Kebijakan Privasi" -> 
                            "Kami di NusaData berkomitmen untuk melindungi informasi pribadi Anda. Kebijakan privasi ini menjelaskan bagaimana data kependudukan Anda dikumpulkan, disimpan, dan digunakan secara aman. Kami memastikan seluruh data tersimpan dalam enkripsi lokal pada database Room Anda, serta tidak membagikan informasi sensitif ini kepada pihak ketiga tanpa persetujuan Anda."
                        "Tentang NusaData" -> 
                            "NusaData adalah aplikasi administrasi kependudukan tingkat RT/RW dan Desa yang dirancang untuk mendigitalisasi pencatatan data warga, keluarga, kelahiran, kematian, dan perpindahan secara cepat dan efisien. Dengan NusaData, pengelolaan administrasi warga dapat diakses di mana saja dan kapan saja secara anda serta aman."
                        "Pusat Bantuan" -> 
                            "Butuh bantuan dalam menggunakan aplikasi NusaData? Anda dapat mengakses petunjuk penggunaan di menu Pusat Bantuan. Jika Anda menemukan kendala teknis atau memiliki pertanyaan seputar cara menginput data warga, silakan menghubungi administrator desa kami melalui email naura24si@mahasiswa.pcr.ac.id."
                        "Syarat & Ketentuan" -> 
                            "Dengan menggunakan aplikasi NusaData, Anda menyetujui bahwa seluruh informasi warga yang Anda masukkan adalah benar, akurat, dan dapat dipertanggungjawabkan. Penyalahgunaan data warga atau penginputan informasi palsu dapat dikenakan sanksi administrasi dan hukum sesuai undang-undang perlindungan data yang berlaku."
                        "Versi Aplikasi v1.0.4" -> 
                            "Aplikasi NusaData Versi 1.0.4 (Build Terakhir).\n\nCatatan Rilis:\n- Mendesain ulang ikon navigasi bawah agar lebih premium.\n- Penyelarasan layout halaman About dengan gambar Desa kependudukan.\n- Integrasi form input kependudukan lokal secara penuh.\n- Menambahkan efek collapsing scroll pada header halaman.\n- Mendukung fitur Edit & Hapus Catatan.\n- Menambahkan peninjau detail Berita Terkini."
                        else -> "Detail informasi mengenai $title."
                    }
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(title)
                        .setMessage(detailMessage)
                        .setPositiveButton("Tutup", null)
                        .show()
                }
            }
            container.addView(itemView)

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