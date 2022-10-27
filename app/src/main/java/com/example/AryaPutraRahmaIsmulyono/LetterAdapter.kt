package com.example.AryaPutraRahmaIsmulyono

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the [RecyclerView] in [MainActivity].
 */
class LetterAdapter (context: Context) :
    RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    // memanggil daftar wisata dari array
    private val list = context.resources.getStringArray(R.array.Wisata).toList()

    /**
     * Provides a reference for the views needed to display items in your list.
     */
    class LetterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button_item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        // atur delegasi aksesibilitas khusus untuk mengatur pembacaan teks
        layout.accessibilityDelegate = Accessibility
        return LetterViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val item = list.get(position)
        holder.button.text = item.toString()

        // Menetapkan [OnClickListener] ke tombol yang ada di [ViewHolder]
        holder.button.setOnClickListener {
            // buat action dari WordList ke DetailList
            // menggunakan argumen yang diperlukan
            val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = holder.button.text.toString())
            // aksi pada navigasi
            holder.view.findNavController().navigate(action)
        }
    }


    // Atur aksesibilitas khusus untuk mengatur teks yang dibaca dengan layanan aksesibilitas
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // Dengan `null` sebagai argumen kedua untuk [AccessibilityAction], layanan aksesibilitas
            // mengumumkan "ketuk dua kali untuk mengaktifkan". Jika string khusus disediakan, ia mengumumkan
            // "ketuk dua kali ke <string khusus>".
            val customString = host.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}