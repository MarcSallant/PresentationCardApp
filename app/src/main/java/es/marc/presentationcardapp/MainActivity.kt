package es.marc.presentationcardapp

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import es.marc.presentationcardapp.ui.theme.PresentationCardAppTheme

class MainActivity : ComponentActivity() {

    private val showGithubView = mutableStateOf(false)
    private val destinationLink = mutableStateOf("")

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PresentationCardAppTheme {
                Links(LinksData.links)
            }
            if (showGithubView.value)
                LoadWebUrl()
        }
    }

    @Composable
    fun Links(links: List<Link>) {

        LazyColumn {
            items(links) { link ->
                LinkCard(link)
            }
        }

    }

    @Composable
    fun LinkCard(link: Link) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Column {
                Text(text = link.title)
                Spacer(modifier = Modifier.height(4.dp))
                ClickableText(text = AnnotatedString(link.link), onClick = {
                    showGithubView.value = true
                    destinationLink.value = link.link
                })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    private fun LoadWebUrl() {
        AndroidView(factory = {
            val apply = WebView(this).apply {
                webViewClient = WebViewClient()
                loadUrl(destinationLink.value)
            }
            apply
        }, update = {
            it.loadUrl(destinationLink.value)
        })
    }

    @Preview(name = "Light Mode", showBackground = true)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
    @Composable
    fun PreviewLinkCard() {
        PresentationCardAppTheme {
            Links(LinksData.links)
        }
    }


    /*data class Message(val author: String, val body: String)

    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.example_image),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

    @Preview(name = "Light Mode", showBackground = true)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
    @Composable
    fun PreviewMessageCard() {
        MaterialTheme {
            MessageCard(
                msg = Message(
                    "Colleague",
                    "Hey, take a look at Jetpack Compose, it's great!"
                )
            )
        }
    }*/
}