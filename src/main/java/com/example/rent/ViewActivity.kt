package com.example.rent

import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import java.util.Date
import java.util.Locale
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ViewActivity : AppCompatActivity() {
    private lateinit var image_views: List<ImageView>
    private lateinit var btn_check: Button
    private lateinit var btn_retype: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        image_views = listOf(
            findViewById(R.id.page1),
            findViewById(R.id.page2),
            findViewById(R.id.page3),
            findViewById(R.id.page4),
            findViewById(R.id.page5),
            findViewById(R.id.page6),
            findViewById(R.id.page7)
        )
        btn_retype = findViewById(R.id.btn_retype)
        btn_check = findViewById(R.id.btn_check)

        val signatureBitmap = intent.getParcelableExtra<Bitmap>("signatureBitmap")
        val currentDate = SimpleDateFormat("yyyy          MM          dd", Locale.getDefault()).format(Date())


        // 이미지 리소스
        for ((index, imageView) in image_views.withIndex()) {
            // 각 이미지에 id 지정
            val resourceId = when (index) {
                0 -> R.drawable.page1
                1 -> R.drawable.page2
                2 -> R.drawable.page3
                3 -> R.drawable.page4
                4 -> R.drawable.page5
                5 -> R.drawable.page6
                6 -> R.drawable.page7
                else -> R.drawable.page1
            }

            val originalBitmap = BitmapFactory.decodeResource(resources, resourceId)

            // 서명 이미지 합성
            if (signatureBitmap != null && originalBitmap != null) {

                if(index != 6){
                    var combinedBitmap = combineImages(originalBitmap, signatureBitmap, 4830f, 3800f)
                    if (index == 0) {
                        combinedBitmap = addTextToImage(combinedBitmap, currentDate, 3730f, 1780f)
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 4000f, 2720f)
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 4000f, 2850f)
                    }

                    if (index == 4) {
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 3200f, 4300f)
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 3900f, 4300f)
                    }

                    if (index == 5) {
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 3200f, 2250f)
                        combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 3900f, 2250f)
                    }
                    imageView.setImageBitmap(combinedBitmap)
                }else{
                    var combinedBitmap = combineImages(originalBitmap, signatureBitmap, 3000f, 4400f)
                    combinedBitmap = combineImages(combinedBitmap, signatureBitmap, 3900f, 4400f)
                    var currentDate = SimpleDateFormat("yyyy            MM           dd", Locale.getDefault()).format(Date())
                    combinedBitmap = addTextToImage(combinedBitmap, currentDate, 3430f, 4370f)
                    imageView.setImageBitmap(combinedBitmap)
                }
            }
        }

        btn_check.setOnClickListener {
            // PDF와 JPG 파일 저장 함수 호출
            val currentDate = SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date())
            val pdfFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "output_$currentDate.pdf")
            val jpgFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "page7_$currentDate.jpg")

            saveImagesToPdfAndJpg()

            if (pdfFile.exists() && jpgFile.exists()) {
                sendEmailWithAttachments(pdfFile, jpgFile)
            } else {
                Toast.makeText(this, "파일 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        btn_retype.setOnClickListener {
            finish()
        }
    }

    // 서명 + 이미지 합성
    private fun combineImages(background: Bitmap, signature: Bitmap, x: Float, y: Float): Bitmap {
        val signatureResized = Bitmap.createScaledBitmap(signature, 500, 500, false) // 서명 크기 조정 (적절한 크기로 조정)
        val combinedBitmap = Bitmap.createBitmap(
            background.width, background.height,
            background.config ?: Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(combinedBitmap)

        canvas.drawBitmap(background, 0f, 0f, null)
        canvas.drawBitmap(signatureResized, x, y, null)

        return combinedBitmap
    }

    //날짜 추가
    private fun addTextToImage(bitmap: Bitmap, text: String, x: Float, y: Float): Bitmap {
        val canvas = Canvas(bitmap)
        val paint = Paint()

        paint.color = android.graphics.Color.BLACK
        paint.textSize = 80f
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT

        canvas.drawText(text, x, y, paint)

        return bitmap
    }


    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val width = (bitmap.width * 0.7).toInt()
        val height = (bitmap.height * 0.7).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun saveImagesToPdfAndJpg() {
        val currentDate = SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date())

        val pdfDocument = PdfDocument()
        var pdfFile: File? = null

        for (i in 0 until 6) {
            val imageView = image_views[i]
            var bitmap = (imageView.drawable as BitmapDrawable).bitmap

            bitmap = compressBitmap(bitmap)

            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, i + 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)

            pdfDocument.finishPage(page)
        }

        try {
            pdfFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "output_$currentDate.pdf")
            pdfDocument.writeTo(FileOutputStream(pdfFile))
            pdfDocument.close()
            Toast.makeText(this, "PDF가 저장되었습니다! (${pdfFile.name})", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val page7Bitmap = (image_views[6].drawable as BitmapDrawable).bitmap
        val jpgFile = saveBitmapAsJpg(page7Bitmap, currentDate)

        if (pdfFile != null && jpgFile != null) {
            sendEmailWithAttachments(pdfFile, jpgFile)
        }
    }

    // JPG 저장 및 압축
    private fun saveBitmapAsJpg(bitmap: Bitmap, currentDate: String): File? {
        val compressedBitmap = compressBitmap(bitmap)

        val jpgFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "page7_$currentDate.jpg")
        return try {
            val outputStream = FileOutputStream(jpgFile)
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "JPG로 저장되었습니다! (${jpgFile.name})", Toast.LENGTH_SHORT).show()
            jpgFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // 이메일 전송
    private fun sendEmailWithAttachments(pdfFile: File, jpgFile: File) {
        val pdfUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", pdfFile)
        val jpgUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", jpgFile)

        val emailIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_SUBJECT, "PDF 및 JPG 파일 전송")
            putExtra(Intent.EXTRA_TEXT, "첨부 파일을 확인하세요.")

            val uris = ArrayList<Uri>().apply {
                add(pdfUri)
                add(jpgUri)
            }
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "이메일 앱을 선택하세요"))
        } catch (e: Exception) {
            Toast.makeText(this, "이메일 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}




