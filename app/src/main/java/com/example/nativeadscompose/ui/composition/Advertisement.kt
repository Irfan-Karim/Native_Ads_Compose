package com.example.nativeadscompose.ui.composition

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.isVisible
import com.example.nativeadscompose.databinding.AdmobNativeBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions

@Composable
fun MediumNativeAd(
    adId: String,
    modifier: Modifier = Modifier
) {
    AndroidViewBinding(
        factory = AdmobNativeBinding::inflate
    ) {
        val adView = root.also { adview ->
            adview.bodyView = this.adBody
            adview.callToActionView = this.adCallToAction
            adview.headlineView = this.adHeadline
            adview.iconView = this.adAppIcon
        }

        val adContainer = this.container
        val adParent = this.parentNative

        // Request Ad
        val adLoader = AdLoader.Builder(adView.context, adId)
            .forNativeAd { nativeAd ->
                nativeAd.advertiser?.let {

                }
                nativeAd.body?.let { body ->
                    this.adBody.text = body
                }
                nativeAd.callToAction?.let { callToAction ->
                    this.adCallToAction.text = callToAction
                }
                nativeAd.headline?.let { headline ->
                    this.adHeadline.text = headline
                }
                nativeAd.icon?.let { icon ->
                    this.adAppIcon.setImageDrawable(icon.drawable)
                }
                adView.setNativeAd(nativeAd)

            }.withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    Log.i("TAG", "onAdLoaded: Native Ad loaded")
                    adContainer.isVisible = false
                    super.onAdLoaded()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.i("TAG", "onAdFailedToLoad: ${error.message}")
                    adParent.isVisible = false
                    super.onAdFailedToLoad(error)
                }
            }).withNativeAdOptions(
                NativeAdOptions.Builder().setAdChoicesPlacement(
                    NativeAdOptions.ADCHOICES_TOP_RIGHT
                ).build()
            ).build()
        adContainer.isVisible = true
        adLoader.loadAd(AdRequest.Builder().build())
    }
}