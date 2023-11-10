package com.example.mad_app090_p11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_app090_p11.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val personList = ArrayList<PersonListCardModel>()
    private val adapter = RecyclerPersonlistAdapter(this, personList)
    lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        db= DatabaseHelper(applicationContext)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        getPersionDetailsFromSqliteDb()
        binding.fab.setOnClickListener {
            networkDb()
        }
    }
    fun deletePerson(position: Int) {
        val person = personList[position]
        db.deletePerson(person)
        personList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_sqlitedb -> {
                getPersionDetailsFromSqliteDb()
                true
            }
            R.id.action_nwdb -> {
                networkDb()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPersionDetailsFromSqliteDb() {
        val size = personList.size
        personList.clear()
        adapter.notifyItemRangeRemoved(0,size)
        try{
            personList.addAll(db.allPersons)
            adapter.notifyItemRangeRemoved(0,personList.size)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getPersonDetailsFromJson(data: String) {
        val size = personList.size
        personList.clear()
        adapter.notifyItemRangeRemoved(0,size)
        try{
            val jsonArray = JSONArray(data)
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray[i] as JSONObject
                val person = PersonListCardModel(jsonObject)
                personList.add(person)
                try{
                    if(db.getPerson(person.id) != null)
                        db.updatePerson(person)
                    else
                        db.insertPerson(person)
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
            adapter.notifyItemRangeRemoved(0,personList.size)
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun networkDb(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = HttpRequest().makeServiceCall(
                    "https://api.json-generator.com/templates/EQ-I7-g55etR/data",
                    "c6v0zd5hcrtbhztoqfq22836w3buikp5fzusw92c")
                withContext(Dispatchers.Main) {
                    try {
                        if(data != null){
                            runOnUiThread{getPersonDetailsFromJson(data)}
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
