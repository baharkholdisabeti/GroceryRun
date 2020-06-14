package com.example.groceryrun

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val link = "url"

class SearchFragment : BottomSheetDialogFragment() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(link)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Search received url", "Url: " + url)

        // Inflate the layout for this fragment
        // this is the base layout for the fragment
        val inflatedView: View = inflater.inflate(R.layout.fragment_search, container, false)

        //val temp = inflatedView.findViewById(R.id.helloblank) as TextView
        //val tempString = temp.text.toString()

        val myWebView: WebView = inflatedView.findViewById(R.id.webview)
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true

        myWebView.loadUrl(""+url)
        webSettings.setUseWideViewPort(true)
        webSettings.setLoadWithOverviewMode(true)
        webSettings.setUseWideViewPort(true)
        webSettings.setDomStorageEnabled(true)
        myWebView.webViewClient = WebViewClient()

        return inflatedView
    }

    // save location and cart data cookies
    // DON'T NEED THIS BECAUSE WE ARE NO LONGER USING A BROWSER INTENT FOR CHECKOUT
    /*
    override fun onPause(){
        super.onPause()
        val cookie = CookieManager.getInstance()
        cookie.setAcceptCookie(true)
        val tempString = cookie.getCookie("https://www.nofrills.ca/Food/Dairy-and-Eggs/Packaged-Cheeses/Shredded-Cheese/plp/NFR001005007006?navid=flyout-L5-Shredded-Cheese")
        Log.i("Cookies loaded: ", tempString)

        // sync is deprecated after a certain android version, so we'll check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookie.flush()
        } else {
            CookieSyncManager.getInstance().startSync()
        }
    } */

    companion object { // args are link and item name; web link and mobile link are different
        // mobile link is web link + "?navid=flyout-L5-[item-name]" where item-name is item name with spaces replaced with dashes
        fun newInstance(link: String, item: String): SearchFragment {    // create a SearchFragment with url argument
            Log.i("Search received url", "Link: " + link)
            // replace spaces with dashes
            val tempItem = item.replace(' ', '-')
            return SearchFragment().apply {
                arguments = Bundle().apply {
                    this.putString("url", link + "?navid=flyout-L5-" + tempItem)
                }
            }
        }
    }
}
