package com.gabrieldchartier.compendia.ui.authentication


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.ui.DataState
import com.gabrieldchartier.compendia.ui.DataStateChangeListener
import com.gabrieldchartier.compendia.ui.Response
import com.gabrieldchartier.compendia.ui.ResponseType
import com.gabrieldchartier.compendia.ui.authentication.ForgotPasswordFragment.WebAppInterface.*
import com.gabrieldchartier.compendia.util.Constants
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ClassCastException

class ForgotPasswordFragment : BaseAuthFragment() {

    private lateinit var webView: WebView
    private lateinit var stateChangeListener: DataStateChangeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webview)
        loadPasswordResetWebView()
        return_to_login_fragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangeListener
        }
        catch (e: ClassCastException) {
            Log.e("ForgotPasswordFragment", "onAttach (line 42): $context must implement DataStateChangeListener")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPasswordResetWebView() {
        stateChangeListener.onDataStateChange(DataState.loading(isLoading = true, cachedData = null))

        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                stateChangeListener.onDataStateChange(DataState.loading(isLoading = false, cachedData = null))
            }
        }
        webView.loadUrl(Constants.PASSWORD_RESET_URL)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(webInteractionCallback), "AndroidTextListener")
    }

    private val webInteractionCallback: OnWebInteractionCallback = object: OnWebInteractionCallback {
        override fun onSuccess(email: String) {
            Log.d("ForgotPasswordFragment", "onSuccess (line 34): a reset link will be sent to $email")
            onPasswordResetLinkSent()
        }

        override fun onError(errorMessage: String) {
            Log.e("ForgotPasswordFragment", "onError (line 39): $errorMessage")
            val dataState = DataState.error<Any>(response = Response(errorMessage, ResponseType.Dialog()))
            stateChangeListener.onDataStateChange(dataState = dataState)
        }

        override fun onLoading(isLoading: Boolean) {
            Log.d("ForgotPasswordFragment", "onLoading (line 47): loading")
            GlobalScope.launch(Main) {
                stateChangeListener.onDataStateChange(DataState.loading(isLoading = isLoading, cachedData = null))
            }
        }
    }

    fun onPasswordResetLinkSent() {
        GlobalScope.launch(Main) {
            parent_view.removeView(webView)
            webView.destroy()

            val animation = TranslateAnimation(
                    password_reset_done_container.width.toFloat(),
                    0f, 0f, 0f )
            animation.duration = 500
            password_reset_done_container.startAnimation(animation)
            password_reset_done_container.visibility = View.VISIBLE
        }
    }

    class WebAppInterface constructor(private val callback: OnWebInteractionCallback) {
        @JavascriptInterface
        fun onSuccess(email: String) {
            callback.onSuccess(email)
        }

        @JavascriptInterface
        fun onError(errorMessage: String) {
            callback.onError(errorMessage)
        }

        @JavascriptInterface
        fun onLoading(isLoading: Boolean) {
            callback.onLoading(isLoading)
        }

        interface OnWebInteractionCallback {
            fun onSuccess(email: String)
            fun onError(errorMessage: String)
            fun onLoading(isLoading: Boolean)
        }
    }
}
