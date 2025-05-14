package com.example.rent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProvide: Button = findViewById(R.id.btn_provide)
        val btnStandard: Button = findViewById(R.id.btn_standard)

        // 거주/숙소 제공 확인서 버튼 클릭 시 activity_provide로 이동
        btnProvide.setOnClickListener {
            val intent = Intent(this, ProvideActivity::class.java)
            startActivity(intent)
        }

        // 표준 임대차 계약서 버튼 클릭 시 activity_standard로 이동
        btnStandard.setOnClickListener {
            val intent = Intent(this, StandardActivity::class.java)
            startActivity(intent)
        }
    }
}