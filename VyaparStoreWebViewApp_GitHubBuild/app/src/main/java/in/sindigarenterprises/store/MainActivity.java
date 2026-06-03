package in.sindigarenterprises.store;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

public class MainActivity extends Activity {
    private static final String STORE_URL = "https://api1.vyaparapp.in/store/sindigarenterprises";

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);
        applySystemBarPadding(root);

        webView = new WebView(this);
        root.addView(webView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        setContentView(root);

        configureWebView();
        setupBackNavigation();

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.loadUrl(STORE_URL);
        }
    }

    private void applySystemBarPadding(FrameLayout root) {
        if (Build.VERSION.SDK_INT >= 20) {
            root.setOnApplyWindowInsetsListener((view, insets) -> {
                if (Build.VERSION.SDK_INT >= 30) {
                    android.graphics.Insets bars = insets.getInsets(WindowInsets.Type.systemBars());
                    view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                } else {
                    view.setPadding(
                            insets.getSystemWindowInsetLeft(),
                            insets.getSystemWindowInsetTop(),
                            insets.getSystemWindowInsetRight(),
                            insets.getSystemWindowInsetBottom()
                    );
                }
                return insets;
            });
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(false);

        if (Build.VERSION.SDK_INT >= 26) {
            settings.setSafeBrowsingEnabled(true);
        }

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUrl(request.getUrl());
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrl(Uri.parse(url));
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= 23 && request.isForMainFrame()) {
                    Toast.makeText(MainActivity.this, "Unable to load page. Check internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean handleUrl(Uri uri) {
        String scheme = uri.getScheme();

        if (scheme == null || "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
            return false;
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "No app found to open this link.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void setupBackNavigation() {
        if (Build.VERSION.SDK_INT >= 33) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                    this::handleBack
            );
        }
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }

    private void handleBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) {
            webView.saveState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
