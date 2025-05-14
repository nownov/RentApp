package com.example.rent

import android.content.Intent
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class StandardActivity : AppCompatActivity() {
    private lateinit var btn_sign: Button
    private lateinit var btn_view: Button
    private lateinit var image_view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard)

        btn_sign = findViewById(R.id.btn_sign)
        btn_view = findViewById(R.id.btn_view)  // 파일 확인 버튼 초기화
        image_view = findViewById(R.id.image_view)  // 이미지 뷰 초기화

        // 서명하기 버튼 클릭
        btn_sign.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGN)
        }

        // 파일 확인 버튼 클릭
        btn_view.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            val signatureBitmap = (image_view.drawable as BitmapDrawable).bitmap // 서명된 이미지
            intent.putExtra("signatureBitmap", signatureBitmap)

            startActivity(intent)
        }

        // SignActivity에서 서명 비트맵 전달 받으면 이미지 뷰에 표시 - 없애도 됨
        val signatureBitmap = intent.getParcelableExtra<Bitmap>("signatureBitmap")
        signatureBitmap?.let {
            image_view.setImageBitmap(it)
        }
    }

    companion object {
        const val REQUEST_SIGN = 1
    }
}
