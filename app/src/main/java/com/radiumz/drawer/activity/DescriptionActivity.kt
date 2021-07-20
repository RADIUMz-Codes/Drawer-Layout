package com.radiumz.drawer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.radiumz.drawer.R
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class DescriptionActivity : AppCompatActivity() {
    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    var bookId: String? = "100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtBookName = findViewById(R.id.txtDsBookName)
        txtBookAuthor = findViewById(R.id.txtDsBookAuthor)
        txtBookPrice = findViewById(R.id.txtDsBookPrice)
        txtBookRating = findViewById(R.id.txtDsRating)
        imgBookImage = findViewById(R.id.imgDsImage)
        txtBookDesc = findViewById(R.id.txtDsDescription)
        btnAddToFav = findViewById(R.id.btnDsFav)
        progressLayout = findViewById(R.id.progressDsLayout)
        progressBar = findViewById(R.id.progressDsBar)

        progressLayout.visibility = View.VISIBLE

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT)
                .show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT)
                .show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id",bookId)
        val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url,jsonParams,Response.Listener {
            try {
                val success = it.getBoolean("success")
                if(success){
                    val bookJsonObject = it.getJSONObject("book_data")
                    progressLayout.visibility = View.GONE

                    Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.book_icon).into(imgBookImage)
                    txtBookName.text = bookJsonObject.getString("name")
                    txtBookAuthor.text = bookJsonObject.getString("author")
                    txtBookPrice.text = bookJsonObject.getString("price")
                    txtBookRating.text = bookJsonObject.getString("rating")
                    txtBookDesc.text = bookJsonObject.getString("description")

                }else{
                    Toast.makeText(this@DescriptionActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT)
                        .show()

                }
            }catch (e:Exception){
                Toast.makeText(this@DescriptionActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT)
                    .show()

            }
        },Response.ErrorListener {
            // Error
            Toast.makeText(this@DescriptionActivity, "Volley Error!!!", Toast.LENGTH_SHORT)
                .show()
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Content-type"] ="application/json"
                headers["token"] = "c5011a8786c3f4"
                return  headers
            }
        }

        queue.add(jsonRequest)
    }

}