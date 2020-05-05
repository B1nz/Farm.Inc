import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gluthfi.farminc.Produk
import com.gluthfi.farminc.ProdukActivity
import com.gluthfi.farminc.R
import kotlinx.android.synthetic.main.produk_list.view.*

class CustomAdapter(private val context: Context, private val produkList: ArrayList<Produk>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val prdk: Produk=produkList[position]
//        holder?.idPrdk?.text = prdk.id
//        holder?.imagePrdk?.text = prdk.image
//        holder?.namaPrdk?.text = prdk.nama
//        holder?.hargaPrdk?.text = prdk.harga

        holder.itemView.idPrdk.text = produkList?.get(position)?.id
        holder.itemView.imagePrdk.text = produkList?.get(position)?.image
        holder.itemView.namaPrdk.text = produkList?.get(position)?.nama
        holder.itemView.hargaPrdk.text = produkList?.get(position)?.harga

        holder.itemView.cvList.setOnClickListener() {

            val i = Intent(context, ProdukActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("editmode","1")
            i.putExtra("id", produkList?.get(position)?.id)
            i.putExtra("image", produkList?.get(position)?.image)
            i.putExtra("nama", produkList?.get(position)?.nama)
            i.putExtra("harga", produkList?.get(position)?.harga)
            context.startActivity(i)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.produk_list, parent, false)
        return  ViewHolder(v)

    }


    override fun getItemCount(): Int {

        return produkList.size
    }



    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val idPrdk = itemView.findViewById(R.id.idPrdk) as TextView
        val imagePrdk = itemView.findViewById(R.id.imagePrdk) as TextView
        val namaPrdk = itemView.findViewById(R.id.namaPrdk) as TextView
        val hargaPrdk = itemView.findViewById(R.id.hargaPrdk) as TextView
    }

}