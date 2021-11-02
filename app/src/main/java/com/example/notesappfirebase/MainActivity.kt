package com.example.notesappfirebase

import DATA.MyViewModel
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfirebase.MyAdApter.myadap

class MainActivity : AppCompatActivity() {


    lateinit var addNote: EditText
    lateinit var btnsub: Button
    lateinit var Notes: List<Notes>
    lateinit var rvadap: RecyclerView
    lateinit var mymodle: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNote = findViewById(R.id.edtext)
        btnsub = findViewById(R.id.b1)
        rvadap = findViewById(R.id.rvmain)
        Notes = arrayListOf()
        // we create object class so we can access to MyViewModel class
        mymodle = ViewModelProvider(this).get(MyViewModel::class.java)
        updtRC()
        btnsub.setOnClickListener {
            // her we will add note
            var note=addNote.text.toString()
            if (addNote.text.toString().isNotEmpty()){
                addNote.text.clear()
                addNote.clearFocus()
                // we call Insertnote function to add the note to database
                mymodle.Insertnote(Notes("",note))
            }else{
                Toast.makeText(this, "Please Enter A value", Toast.LENGTH_SHORT).show()
            }



        }
        // her we call this function her " MyViewModel class" so we can display the data inside recyclerview
        mymodle.getNote()


    }

    fun updtRC(){

        mymodle.getallnotes().observe(this,{  AllNotes ->
            // her notes will display in recyclerview
            rvadap.adapter = myadap(this,AllNotes)
            rvadap.layoutManager = LinearLayoutManager(this)

        })

    }
    fun openwendow(oldnote:Notes){
        val dialog= AlertDialog.Builder(this)
        val newNote=EditText(this)
        val id = oldnote.id
        newNote.hint="Enter new text"
        dialog.setCancelable(false).setPositiveButton("Save", DialogInterface.OnClickListener {
                _, i -> if (newNote.text.isNotEmpty()) {
            mymodle.updatenote(id, newNote.text.toString())
        }else{
            Toast.makeText(this, "Please Enter A value ", Toast.LENGTH_SHORT).show()
        }
            })

        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, i ->  })
        // the alert will create if we press the edit icon the function edit will cal the updatNote function
        // in database so we can make change in database
        val Alert = dialog.create()
        Alert.setTitle("Update Note")
        // setView will show the value in edittext inside alert
        Alert.setView(newNote)
        Alert.show()


    }

    fun deletedialog(oldnote:Notes){
        val dialog= AlertDialog.Builder(this)
        val id = oldnote.id
        dialog.setMessage( "Are you sure you want to delete?")

        dialog.setCancelable(false).setPositiveButton("Yes", DialogInterface.OnClickListener {
                _, i -> mymodle.deletenote(id)})

        dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val Alert = dialog.create()
        Alert.setTitle("Delete Note")
        Alert.show()



    }


}


