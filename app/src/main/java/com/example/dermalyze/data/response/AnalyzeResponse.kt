package com.example.dermalyze.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class AnalyzeResponse(
	@field:SerializedName("skin_condition_information")
	val skinConditionInformation: SkinConditionInformation? = null,

	@field:SerializedName("disease_prediction")
	val diseasePrediction: DiseasePrediction? = null,

	@field:SerializedName("skin_type_prediction")
	val skinTypePrediction: SkinTypePrediction? = null
) : Parcelable

@Parcelize
data class SkinTypePrediction(
	@field:SerializedName("confidence")
	val confidence: @RawValue Any? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null
) : Parcelable

@Parcelize
data class DiseasePrediction(
	@field:SerializedName("confidence")
	val confidence: @RawValue Any? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null
) : Parcelable

@Parcelize
data class SkinConditionInformation(
	@field:SerializedName("Common Causes")
	val commonCauses: List<String?>? = null,

	@field:SerializedName("Self-care Tips")
	val selfCareTips: List<String?>? = null,

	@field:SerializedName("Treatable")
	val treatable: String? = null,

	@field:SerializedName("Definition")
	val definition: String? = null
) : Parcelable
