package com.example.letsgotour.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsgotour.adapter.ItemDisplayAdapter
import com.example.letsgotour.databinding.FragmentDisplayItemBinding
import com.example.letsgotour.modal.ItemModal
import com.google.firebase.database.*

class DisplayItemFragment : Fragment() {

    lateinit var mDbRef: DatabaseReference
    var ItemList: ArrayList<ItemModal> = ArrayList()
    lateinit var itemDisplayAdapter: ItemDisplayAdapter
    lateinit var binding: FragmentDisplayItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayItemBinding.inflate(inflater, container, false);
        val view = binding.root
        mDbRef = FirebaseDatabase.getInstance().reference
        initView()
        return view
    }

    private fun initView() {

        itemDisplayAdapter = ItemDisplayAdapter(requireContext(), ItemList)
        val manger = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvDisplayItem.layoutManager = manger
        binding.rcvDisplayItem.adapter = itemDisplayAdapter
        mDbRef.child("Item").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                ItemList.clear()
                for (postSnapshot in snapshot.children) {
                    val Item = postSnapshot.getValue(ItemModal::class.java)
                    if (Item != null) {
                        binding.DisplayProgressBar.visibility=View.GONE
                    }
                    ItemList.add(Item!!)
                    Log.d("TAG", "onDataChange:${Item.name} ")
                    setItem(Item)


                }
                itemDisplayAdapter.notifyDataSetChanged()

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setItem(Item: ItemModal?) {

    }


}