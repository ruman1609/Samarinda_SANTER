package com.rudyrachman16.samarindasanter.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.databinding.ItemBeritaBinding
import com.rudyrachman16.samarindasanter.utils.ImageViewUtils.load
import com.rudyrachman16.samarindasanter.utils.ViewUtils.fromHTML

class DashboardAdapter(private val list: List<News>, private val clickCallback: (News) -> Unit) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    inner class ViewHolder(private val bind: ItemBeritaBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(news: News) {
            bind.ivFeedBerita.load(news.foto ?: "")
            bind.tvFeedTitle.text = news.judul ?: ""
            bind.tvFeedDesc.fromHTML(news.isi ?: "")
            bind.tvFeedDate.text = news.createdAt ?: ""

            bind.root.setOnClickListener { clickCallback(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBeritaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(list[holder.adapterPosition])
    }
}