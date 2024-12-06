package com.example.allergifyapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.allergifyapp.R
import com.example.allergifyapp.data.remote.model.userallergen.UserAllergen
import com.example.allergifyapp.databinding.UserAllergenInformationDeleteDialogBinding
import com.example.allergifyapp.databinding.UserAllergenInformationEditDialogBinding
import com.example.allergifyapp.databinding.UserAllergenInfromationCardBinding
import com.example.allergifyapp.viewmodel.UserAllergenViewModel
import javax.inject.Inject

class UserAllergenAdapter @Inject constructor(
    private val userAllergenViewModel: UserAllergenViewModel
) : RecyclerView.Adapter<UserAllergenAdapter.UserAllergenViewHolder>() {

    private lateinit var binding: UserAllergenInfromationCardBinding
    private lateinit var context: Context

    private val differCallback = object : DiffUtil.ItemCallback<UserAllergen>() {
        override fun areItemsTheSame(oldItem: UserAllergen, newItem: UserAllergen): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserAllergen, newItem: UserAllergen): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private val allergenImages = listOf(
        R.drawable.walnutvectoruserallergyimage,
        R.drawable.crabvectoruserallergyimage,
        R.drawable.cheesevectoruserallergyimage,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAllergenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = UserAllergenInfromationCardBinding.inflate(inflater, parent, false)
        context = parent.context
        return UserAllergenViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: UserAllergenViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    inner class UserAllergenViewHolder(private val binding: UserAllergenInfromationCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userAllergen: UserAllergen,  position: Int) {
            binding.userAllergenName.text = userAllergen.name

            val imageIndex = position % allergenImages.size
            binding.imageVectorAllergenInformationCard.setImageResource(allergenImages[imageIndex])

            binding.editUserAllergyButton.setOnClickListener {
                showEditDialog(userAllergen)
            }

            binding.deleteUserAllergyButton.setOnClickListener {
                showDeleteConfirmationDialog(userAllergen)
            }
        }
    }

    private fun showEditDialog(userAllergen: UserAllergen) {
        val editDialogBinding =
            UserAllergenInformationEditDialogBinding.inflate(LayoutInflater.from(context))

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(editDialogBinding.root)
            .setCancelable(false)

        val dialog = dialogBuilder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        editDialogBinding.editUserAllergyEditText.setText(userAllergen.name)

        editDialogBinding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        editDialogBinding.userAllergenSaveButton.setOnClickListener {
            val updatedName = editDialogBinding.editUserAllergyEditText.text.toString()
            if (updatedName.isNotEmpty()) {
                userAllergenViewModel.updateUserAllergens(userAllergen.id, updatedName)
                dialog.dismiss()
            } else {
                //TODO
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(userAllergen: UserAllergen) {
        val deleteDialogBinding = UserAllergenInformationDeleteDialogBinding.inflate(LayoutInflater.from(context))

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(deleteDialogBinding.root)
            .setCancelable(false)

        val dialog = dialogBuilder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        deleteDialogBinding.deleteForeverButton.setOnClickListener {
            userAllergenViewModel.deleteUserAllergens(userAllergen.id)
            dialog.dismiss()
        }

        deleteDialogBinding.cancelDeleteButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}