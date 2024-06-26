package com.imooc.composeapp.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.imooc.composeapp.model.entity.VideoEntity

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    videoEntity: VideoEntity,
    loaded: Boolean,
) {
    val constraintSet =
        ConstraintSet {
            val title = createRefFor("title")
            val cover = createRefFor("cover")
            val type = createRefFor("type")
            val duration = createRefFor("dutation")
            val divider = createRefFor("divider")
            constrain(cover) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
                width = Dimension.value(115.5.dp)
            }
            constrain(title) {
                start.linkTo(cover.end, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(type) {
                start.linkTo(title.start)
                bottom.linkTo(parent.bottom)
            }

            constrain(duration) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            constrain(divider) {
                bottom.linkTo(cover.bottom, margin = (-8).dp)
            }
        }

    ConstraintLayout(
        constraintSet,
        modifier =
            modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        AsyncImage(
            model = videoEntity.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier =
                Modifier
                    .layoutId("cover")
                    .aspectRatio(16 / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = !loaded,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
        )

        Text(
            text = videoEntity.title,
            fontSize = 16.sp,
            color = Color(0xFF666666),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("title"),
        )

        Text(
            text = videoEntity.type ?: "",
            fontSize = 10.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("type"),
        )

        Text(
            text = "时长:${videoEntity.duration}",
            fontSize = 10.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("dutation"),
        )
        Divider(
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .layoutId("divider"),
        )
    }
}
