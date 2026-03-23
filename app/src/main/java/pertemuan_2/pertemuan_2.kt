package pertemuan_2

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R

class pertemuan_2 : AppCompatActivity() {

    lateinit var alas: EditText
    lateinit var tinggi: EditText
    lateinit var sisi: EditText
    lateinit var hasil: TextView
    lateinit var btnSegitiga: Button
    lateinit var btnKubus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pertemuan2)

        alas = findViewById(R.id.inputAlas)
        tinggi = findViewById(R.id.inputTinggi)
        sisi = findViewById(R.id.inputSisi)
        hasil = findViewById(R.id.tvHasil)

        btnSegitiga = findViewById(R.id.btnSegitiga)
        btnKubus = findViewById(R.id.btnKubus)

        btnSegitiga.setOnClickListener {

            val a = alas.text.toString().toDouble()
            val t = tinggi.text.toString().toDouble()

            val luas = 0.5 * a * t

            hasil.text = "Luas Segitiga = $luas"
        }

        btnKubus.setOnClickListener {

            val s = sisi.text.toString().toDouble()

            val volume = s * s * s

            hasil.text = "Volume Kubus = $volume"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}