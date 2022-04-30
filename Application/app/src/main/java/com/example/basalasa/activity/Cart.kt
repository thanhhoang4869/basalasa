package com.example.basalasa.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.adapter.CartAdapter
import com.example.basalasa.databinding.ActivityCartBinding
import com.example.basalasa.model.body.DeleteCartBody
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.GetCartResponse
import com.example.basalasa.model.reponse.GetUpdateResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.DecimalFormat
import java.text.NumberFormat


class Cart : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    lateinit var arrBooks: ArrayList<BooksInCart>
    lateinit var adapter: CartAdapter
    var choosen: HashMap<String, BooksInCart> = HashMap()
    private var total: Int = 0

    private val formatter: NumberFormat = DecimalFormat("#,###")
    var tmp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        binding = ActivityCartBinding.inflate(layoutInflater)
        binding.cartActive.isVisible=false
        binding.noCart.isVisible=false
        loadListCart()
    }

    private fun loadListCart() {
        val token = Cache.getToken(this)
        if (token === null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            val response = MyAPI.getAPI().getCart(token.toString())
            arrBooks = ArrayList()

            response.enqueue(object : Callback<GetCartResponse> {
                override fun onResponse(
                    call: Call<GetCartResponse>,
                    response: Response<GetCartResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if(data?.arrBooks.isNullOrEmpty()){
                            binding.cartActive.isVisible=false
                            binding.noCart.isVisible=true
                        } else{
                            if (data != null) {
                                setUpCart(data)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<GetCartResponse>, t: Throwable) {
                    Toast.makeText(this@Cart, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            })
            findViewById<Button>(R.id.checkout).setOnClickListener() {
                var intent: Intent = Intent(this, Checkout::class.java)

                if (choosen.size != 0) {
                    intent.putExtra("map", choosen)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    fun setUpCart(data:GetCartResponse){
        binding.cartActive.isVisible=true
        binding.noCart.isVisible=false

        for (item: BooksInCart in data.arrBooks!!) {
            arrBooks.add(item)
        }

        var seller= ""

        val TotalView: TextView = findViewById(R.id.total)
        val hiddenView: TextView = findViewById(R.id.tempTotalView)
        tmp = total.toString()
        TotalView.text = formatter.format(parseInt(tmp))

        hiddenView.text = total.toString()
        adapter = CartAdapter(arrBooks, hiddenView, TotalView)
        val listCartitem: RecyclerView = findViewById(R.id.listCartitem)
        listCartitem.adapter = adapter
        listCartitem.layoutManager = LinearLayoutManager(this@Cart)
        adapter.onItemClick = { s, position ->
            if (choosen.get(s._id) != null) {
                total -= s.price * s.quantity
                hiddenView.text =
                    (parseInt(hiddenView.text.toString()) - s.price * s.quantity).toString()
                //total.toString()
                tmp = hiddenView.text as String
                TotalView.text = formatter.format(parseInt(tmp))

                choosen.remove(s._id)
                seller = ""
            }
            deleteData(s, position)
        }
        adapter.onCheckClick = { s, position, check_btn ->
            if (choosen.size == 0) {
                choosen.put(s._id, s)
                seller = s.seller
                total += s.price * s.quantity
                hiddenView.text =
                    (parseInt(hiddenView.text.toString()) + s.price * s.quantity).toString()
                //total.toString()
                tmp = hiddenView.text as String
                TotalView.text = formatter.format(parseInt(tmp))
            } else {
                if (s.seller == seller) {
                    choosen.put(s._id, s)
                    hiddenView.text =
                        (parseInt(hiddenView.text.toString()) + s.price * s.quantity).toString()
                    //(total+ s.price * s.quantity).toString()
                    tmp = hiddenView.text as String
                    TotalView.text = formatter.format(parseInt(tmp))
                    //(total+s.price*s.quantity).toString()
                } else {
                    check_btn.isChecked = false
                    Toast.makeText(
                        this@Cart,
                        "Different Seller, choose again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
        adapter.removeCheck = { s, position ->
            if (choosen.get(s._id)?.seller == seller) {
                choosen.remove(s._id)
                if (choosen.size == 0)
                    seller = ""
                total -= s.price * s.quantity
                hiddenView.text =
                    (parseInt(hiddenView.text.toString()) - s.price * s.quantity).toString()
                //total.toString()
//                                Log.d("Test",hiddenView.text.toString())
                tmp = hiddenView.text as String
                TotalView.text = formatter.format(parseInt(tmp))
                //(total-s.price*s.quantity).toString()
            }
        }
    }

    private fun deleteData(book: BooksInCart, position: Int) {
        val token = Cache.getToken(this)
        val response = MyAPI.getAPI().deleteCart(token.toString(), DeleteCartBody(book._id))
        response.enqueue(object : Callback<GetUpdateResponse> {
            override fun onResponse(
                call: Call<GetUpdateResponse>,
                response: Response<GetUpdateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Cart, "Delete Successfully", Toast.LENGTH_LONG).show()
                    arrBooks.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }

            }

            override fun onFailure(call: Call<GetUpdateResponse>, t: Throwable) {
                Toast.makeText(this@Cart, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

}