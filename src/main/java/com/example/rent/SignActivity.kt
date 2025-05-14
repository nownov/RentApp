package com.example.rent

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignActivity : AppCompatActivity() {
    private lateinit var signatureView: SignatureView
    private lateinit var btn_save: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        signatureView = findViewById(R.id.signatureView)
        btn_save = findViewById(R.id.btn_save)

        btn_save.setOnClickListener {
            try {
                // 서명 비트맵 생성
                val bitmap = signatureView.getSignatureBitmap()

                // 비트맵 크기 조정 - 사이즈를 더 키우면 자꾸 에러나서 이유를 찾아봐야할 듯
                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true)

                // Intent로 결과 전달
                val intent = Intent().apply {
                    putExtra("signatureBitmap", resizedBitmap) // 크기 조정된 비트맵 전달
                }

                // 결과를 StandardActivity로 보내도록 Intent 설정
                val resultIntent = Intent(this, StandardActivity::class.java).apply {
                    putExtra("signatureBitmap", resizedBitmap) // 크기 조정된 비트맵 전달
                }
                startActivity(resultIntent) // StandardActivity 시작
                finish() // SignActivity 종료
            } catch (e: Exception) {
                Log.e("SignActivity", "Error while saving signature", e)
                setResult(RESULT_CANCELED) // 실패 결과 반환
                finish() // SignActivity 종료
            }
        }
    }
}
