package com.example.naura_orbit.Home.pertemuan_10.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naura_orbit.databinding.FragmentTabCKeluargaBinding

class TabCKeluargaFragment : Fragment() {
    private var _binding: FragmentTabCKeluargaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabCKeluargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 50 Data Dummy Kartu Keluarga Resmi
        val dataKeluarga = listOf(
            KeluargaModel("3201234567890001", "Rahmat Simbolon S.T.", "Jl. Merdeka No. 12, RT 04/RW 02", 4),
            KeluargaModel("3201234567890002", "Supriadi", "Jl. Elang No. 5, RT 01/RW 02", 3),
            KeluargaModel("3201234567890003", "Budi Sudarsono", "Gang Kelinci No. 22, RT 02/RW 02", 5),
            KeluargaModel("3201234567890004", "Hendra Wijaya", "Jl. Mawar No. 15, RT 03/RW 02", 4),
            KeluargaModel("3201234567890005", "Siti Aminah", "Jl. Melati No. 8, RT 05/RW 02", 2),
            KeluargaModel("3201234567890006", "Ahmad Fauzi", "Jl. Kenanga No. 4, RT 01/RW 01", 6),
            KeluargaModel("3201234567890007", "Bambang Pamungkas", "Jl. Garuda No. 10, RT 02/RW 01", 4),
            KeluargaModel("3201234567890008", "Eko Prasetyo", "Jl. Merpati No. 3, RT 03/RW 01", 3),
            KeluargaModel("3201234567890009", "Dewi Lestari", "Jl. Cendrawasih No. 7, RT 04/RW 01", 5),
            KeluargaModel("3201234567890010", "Joko Widodo", "Jl. Diponegoro No. 1, RT 05/RW 01", 4),
            KeluargaModel("3201234567890011", "Rudi Hartono", "Jl. Sudirman No. 45, RT 01/RW 03", 3),
            KeluargaModel("3201234567890012", "Agus Salim", "Jl. Thamrin No. 12, RT 02/RW 03", 5),
            KeluargaModel("3201234567890013", "Megawati S.", "Jl. Gatot Subroto No. 9, RT 03/RW 03", 2),
            KeluargaModel("3201234567890014", "Susilo Bambang", "Jl. S Parman No. 24, RT 04/RW 03", 4),
            KeluargaModel("3201234567890015", "Anies Baswedan", "Jl. Rasuna Said No. 18, RT 05/RW 03", 6),
            KeluargaModel("3201234567890016", "Ganjar Pranowo", "Jl. Pemuda No. 30, RT 01/RW 04", 3),
            KeluargaModel("3201234567890017", "Prabowo Subianto", "Jl. Kertanegara No. 4, RT 02/RW 04", 2),
            KeluargaModel("3201234567890018", "Gibran Rakabuming", "Jl. Slamet Riyadi No. 11, RT 03/RW 04", 4),
            KeluargaModel("3201234567890019", "Mahfud MD", "Jl. Kaliurang KM 5, RT 04/RW 04", 5),
            KeluargaModel("3201234567890020", "Muhaimin Iskandar", "Jl. Ampera No. 14, RT 05/RW 04", 4),
            KeluargaModel("3201234567890021", "Ridwan Kamil", "Jl. Asia Afrika No. 8, RT 01/RW 05", 4),
            KeluargaModel("3201234567890022", "Khofifah Indar", "Jl. Basuki Rahmat No. 3, RT 02/RW 05", 3),
            KeluargaModel("3201234567890023", "Andika Perkasa", "Jl. Teuku Umar No. 21, RT 03/RW 05", 5),
            KeluargaModel("3201234567890024", "Erick Thohir", "Jl. Medan Merdeka No. 2, RT 04/RW 05", 4),
            KeluargaModel("3201234567890025", "Sri Mulyani", "Jl. Lapangan Banteng No. 5, RT 05/RW 05", 3),
            KeluargaModel("3201234567890026", "Luhut Binsar", "Jl. Mega Kuningan No. 1, RT 01/RW 06", 4),
            KeluargaModel("3201234567890027", "Basuki Hadimuljono", "Jl. Pattimura No. 20, RT 02/RW 06", 5),
            KeluargaModel("3201234567890028", "Retno Marsudi", "Jl. Pejambon No. 6, RT 03/RW 06", 2),
            KeluargaModel("3201234567890029", "Budi Gunadi", "Jl. H.R. Rasuna Said No. 7, RT 04/RW 06", 4),
            KeluargaModel("3201234567890030", "Nadiem Makarim", "Jl. Jenderal Sudirman No. 1, RT 05/RW 06", 4),
            KeluargaModel("3201234567890031", "Sandiaga Uno", "Jl. Kebon Sirih No. 12, RT 01/RW 07", 5),
            KeluargaModel("3201234567890032", "Tri Rismaharini", "Jl. Salemba Raya No. 28, RT 02/RW 07", 3),
            KeluargaModel("3201234567890033", "Tito Karnavian", "Jl. Medan Merdeka Utara No. 7, RT 03/RW 07", 4),
            KeluargaModel("3201234567890034", "Yasonna Laoly", "Jl. H.R. Rasuna Said No. 21, RT 04/RW 07", 4),
            KeluargaModel("3201234567890035", "Zulkifli Hasan", "Jl. Ridwan Rais No. 5, RT 05/RW 07", 5),
            KeluargaModel("3201234567890036", "Airlangga Hartarto", "Jl. Gatot Subroto No. 36, RT 01/RW 08", 4),
            KeluargaModel("3201234567890037", "Agus Harimurti", "Jl. Proklamasi No. 41, RT 02/RW 08", 3),
            KeluargaModel("3201234567890038", "Puan Maharani", "Jl. Gatot Subroto No. 1, RT 03/RW 08", 3),
            KeluargaModel("3201234567890039", "Muhaimin Iskandar", "Jl. Raden Saleh No. 9, RT 04/RW 08", 5),
            KeluargaModel("3201234567890040", "Surya Paloh", "Jl. R.P. Soeroso No. 44, RT 05/RW 08", 2),
            KeluargaModel("3201234567890041", "Hary Tanoe", "Jl. Diponegoro No. 29, RT 01/RW 09", 6),
            KeluargaModel("3201234567890042", "Oesman Sapta", "Jl. Karang Asem No. 15, RT 02/RW 09", 4),
            KeluargaModel("3201234567890043", "Yusril Ihza", "Jl. Kasablanka No. 22, RT 03/RW 09", 3),
            KeluargaModel("3201234567890044", "Anis Matta", "Jl. Kalibata Raya No. 1, RT 04/RW 09", 5),
            KeluargaModel("3201234567890045", "Ahmad Syaikhu", "Jl. T.B. Simatupang No. 82, RT 05/RW 09", 4),
            KeluargaModel("3201234567890046", "Said Aqil", "Jl. Kramat Raya No. 164, RT 01/RW 10", 5),
            KeluargaModel("3201234567890047", "Haedar Nashir", "Jl. Cik Ditiro No. 23, RT 02/RW 10", 4),
            KeluargaModel("3201234567890048", "Yahya Staquf", "Jl. Kramat Raya No. 164, RT 03/RW 10", 4),
            KeluargaModel("3201234567890049", "Anwar Abbas", "Jl. Menteng Raya No. 20, RT 04/RW 10", 3),
            KeluargaModel("3201234567890050", "Din Syamsuddin", "Jl. Pejaten Barat No. 14, RT 05/RW 10", 4)
        )

        // Setup RecyclerView dengan ID rvKeluargaTabC yang sudah diperbaiki
        binding.rvKeluargaTabC.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKeluargaTabC.adapter = KeluargaAdapter(dataKeluarga) { keluarga ->
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Detail Kartu Keluarga (KK)")
                .setMessage("No KK:\n${keluarga.noKK}\n\nKepala Keluarga:\n${keluarga.kepalaKeluarga}\n\nAlamat:\n${keluarga.alamat}\n\nJumlah Anggota Keluarga: ${keluarga.jumlahAnggota} Jiwa")
                .setPositiveButton("Tutup", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}