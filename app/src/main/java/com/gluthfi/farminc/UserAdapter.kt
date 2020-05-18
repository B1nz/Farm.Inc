import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gluthfi.farminc.DetailActivity
import com.gluthfi.farminc.Produk
import com.gluthfi.farminc.R
import kotlinx.android.synthetic.main.produk_list.view.*

class UserAdapter(private val context: Context, private val produkList: ArrayList<Produk>):RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(this.context)
            .load(produkList?.get(position)?.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.imagePrdk)

        holder.itemView.idPrdk.text = produkList?.get(position)?.id
        holder.itemView.namaPrdk.text = produkList?.get(position)?.nama
        holder.itemView.hargaPrdk.text = produkList?.get(position)?.harga
        holder.itemView.penggunaPrdk.text = produkList?.get(position)?.pengguna_id
        holder.itemView.deskripsiPrdk.text = produkList?.get(position)?.deskripsi
        holder.itemView.kategoriPrdk.text = produkList?.get(position)?.kategori

        holder.itemView.cvList.setOnClickListener() {

            val i = Intent(context, DetailActivity::class.java)

            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("editmode","1")
            i.putExtra("id", produkList?.get(position)?.id)
            i.putExtra("image", produkList?.get(position)?.image)
            i.putExtra("nama", produkList?.get(position)?.nama)
            i.putExtra("harga", produkList?.get(position)?.harga)
            i.putExtra("pengguna", produkList?.get(position)?.pengguna_id)
            i.putExtra("deskripsi", produkList?.get(position)?.deskripsi)
            i.putExtra("kategori", produkList?.get(position)?.kategori)
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
    }

}