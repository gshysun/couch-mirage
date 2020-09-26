package com.huji.couchmirage

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var departmentAdapter: DepartmentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {
        val data = DepartmentSourceData.createDataSet()
        departmentAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            departmentAdapter = DepartmentRecyclerAdapter(object : OnDepartmentClickListen {
                override fun onDepartmentClick(view: View, position: Int) {
//                    Toast.makeText(view.context, "clicked", Toast.LENGTH_SHORT).show()
                    openDepartmentPage(view, position)
                }
            })
            adapter = departmentAdapter
        }
    }

    fun openDepartmentPage(view: View, position: Int) {

        val intent = Intent(this, DepartmentActivity::class.java).apply {
            putExtra("DEPARTMENT NAME", departmentAdapter.items[position].departmentName)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


        var db = FirebaseFirestore.getInstance()
        val list = ArrayList<SingleItem>()

        db.collection(departmentAdapter.items[position].departmentName).get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val i = document.toObject(SingleItem::class.java)
                    list.add(i)
                }
                intent.putExtra("items", list);
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }


    }


}

