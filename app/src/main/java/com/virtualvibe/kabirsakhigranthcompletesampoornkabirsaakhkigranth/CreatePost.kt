package com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.CheckVaniSaved
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.SelectedFormat
import com.virtualvibe.kabirsakhigranthcompletesampoornkabirsaakhkigranth.Utils.Splitter
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePost : AppCompatActivity() {

    lateinit var chapter_name: String
    lateinit var newDohaNumberList: MutableList<String>
    lateinit var finalDoheList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val extra = intent.extras
        if (extra != null) {
            chapter_name = extra.getString("chap_name").toString()
            chapter_name_title.text = chapter_name
            Toast.makeText(this, "$chapter_name", Toast.LENGTH_LONG).show()
        }
        val commontText = "write Your Dohe/Vani Here in Format shown below\n\n"
        when(SelectedFormat.format){
            "format1" -> {
                vani_text_box.hint = "$commontText गरीब, सेवक होय करि ऊतरे , इस पृथ्वी के माँहि ।\n" +
                        "जीव उधारन जगतगुरु , बार बार बलि जांहि ।।380।।\n" +
                        "गरीब, काशीपुरी कस्त किया , उतरे अधर अधार ।\n" +
                        "मोमन कू मुजरा हुवा , जंगल में दीदार ।।381।।"
            }
            "format2" ->{
                vani_text_box.hint ="$commontText नारि एक संसारहि आई, माय न बाके बापहिं जाई । \n" +
                        " गोड न मूड न प्रान अधारा, तामह भरमि रहा संसारा ।\n " +
                        "दिना सात लौं उनकी सही, बुद अदबुद अचरज का कही ।\n " +
                        "बाकी बंदन करे सब कोई, बुद अदबुद अचरज बड़ होई ।"
            }
            "format3" ->{
                vani_text_box.hint ="$commontText गरीब, सेवक होय करि ऊतरे , इस पृथ्वी के माँहि ।\n" +
                        "जीव उधारन जगतगुरु , बार बार बलि जांहि ।।\n" +
                        "गरीब, काशीपुरी कस्त किया , उतरे अधर अधार ।\n" +
                        "मोमन कू मुजरा हुवा , जंगल में दीदार ।।"
            }
        }

        goToHistory.setOnClickListener {
            Intent(this , ChapterList::class.java).apply {
                this.putExtra("chapter" , chapter_name)
                startActivity(this)
            }
        }

        preViewBttn.setOnClickListener {
            val content = vani_text_box.text.toString().trim()
            if (content != "") {
                var regex: String
                if (SelectedFormat.format == "format1" || SelectedFormat.format == "format3") {
                    if(lastCharacterBox.text.toString()!=""){
                        regex = lastCharacterBox.text.toString().trim()
                    }else{
                        regex = "।।"
                    }
//                    Log.v("doha", "${SelectedFormat.format}")
                } else {
                    if(lastCharacterBox.text.toString()!=""){
                        regex = lastCharacterBox.text.toString().trim()
                    }else {
                        regex = "।"
                    }
//                    Log.v("doha", "${SelectedFormat.format}")
                }

                val doheList = arrayToArrayList(Splitter.splitText(content, regex))

                when(SelectedFormat.format){
                    "format1" -> {
                        val doheListWithoutNumber = seperateDohaAndNumber(doheList)
                        finalDoheList = attachSlashInEnd(doheListWithoutNumber as ArrayList<String>)
                        finalDoheList =
                            reattachDohaAndNumber(finalDoheList, newDohaNumberList as ArrayList<String>)
//                        Log.v("withouNum" , "$doheListWithoutNumber")
//                        Log.v("withNum" , "$doheList")
                    }
                    "format2" ->{
                        finalDoheList = attachSlashInEnd(doheList)
                    }
                    "format3" ->{
                        finalDoheList = attachSlashInEnd(doheList)
                    }
                }

//                Log.v("fin", "${finalDoheList}")
//
                 val bunddle = Bundle()
                 bunddle.putStringArrayList("dohaList" , finalDoheList )
                 Intent(this , Preview::class.java).apply {
                     this.putExtras(bunddle)
                     this.putExtra("chapter" , chapter_name)
                     startActivity(this)
                 }
            } else {
                Toast.makeText(
                    this,
                    "Cannot Save Empty Content ! Please Insert Dohe / Vani",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun seperateDohaAndNumber(doheList: ArrayList<String>): MutableList<String> {
        var newDohaList: MutableList<String> = ArrayList()
        newDohaNumberList = ArrayList()
        var index = 0
        for (doha in doheList) {
            if (index % 2 == 0) {
                newDohaList.add(doha)
//                Log.v("doha", "$doha")
            } else {
                newDohaNumberList.add(doha)
            }
            index = index + 1
        }
        return newDohaList

    }

    private fun reattachDohaAndNumber(
        newDohaList: MutableList<String>,
        newDohaNumberList: ArrayList<String>
    ): ArrayList<String> {
        var modifiedDohaList: MutableList<String> = ArrayList()
        for (i in 0..newDohaList.size - 1) {
            modifiedDohaList.add(newDohaList[i].trim() + " " + newDohaNumberList[i].trim() + " ।।")
//            Log.v("modi", "$i")
        }
        return modifiedDohaList as ArrayList<String>

    }

    fun attachSlashInEnd(newDohaList: ArrayList<String>): ArrayList<String> {
        val finalDohaList = ArrayList<String>()
        for (doha in newDohaList) {
            var d = doha.trim()
            if (SelectedFormat.format == "format2") {
                d += " ।"
            } else {
                d += " ।।"
            }
            finalDohaList.add(d)
        }
//        Log.v("doha", "${finalDohaList}")
        return finalDohaList

    }

    fun arrayToArrayList(dohaArray: Array<String>): ArrayList<String> {
        var  dohaList: MutableList<String> = ArrayList()
        for(doha in dohaArray){
            dohaList.add(doha)
        }
        return dohaList as ArrayList<String>
    }
    override fun onRestart() {
        super.onRestart()
        if (CheckVaniSaved.isVaniSaved) {
            vani_text_box.setText("")
        }
    }
}