package pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.naura_orbit.R
import com.example.naura_orbit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {

                val intent = Intent(this, GreetingActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)

            } else {
                binding.etUsername.error = "Username wajib diisi"
                binding.etPassword.error = "Password wajib diisi"
            }
        }
    }
}