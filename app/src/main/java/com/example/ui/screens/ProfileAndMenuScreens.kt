package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.MeskotStrings
import com.example.data.Post
import com.example.data.User
import com.example.ui.MeskotViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.PostCard
import com.example.ui.components.UserAvatar
import com.example.ui.theme.ActiveGreen
import com.example.ui.theme.CardBg
import com.example.ui.theme.CrossRed
import com.example.ui.theme.Gold
import com.example.ui.theme.GoldDeep
import com.example.ui.theme.Ink
import com.example.ui.theme.LineBorder
import com.example.ui.theme.MutedText
import com.example.ui.theme.Paper
import com.example.ui.theme.Paper2
import androidx.compose.ui.text.TextStyle
import com.example.ui.theme.meskotTextFieldColors

@Composable
fun ProfileScreen(
    viewModel: MeskotViewModel,
    user: User,
    currentUser: User?,
    userPosts: List<Post>,
    friendUids: Set<String>,
    currentLanguage: AppLanguage
) {
    val isMe = currentUser?.uid == user.uid
    val isFriend = friendUids.contains(user.uid)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen")
    ) {
        // Back Header if viewing other user
        if (!isMe) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.navigateTo(ScreenTab.FEED) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Ink)
                    }
                    Text(
                        text = user.displayName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink
                    )
                }
            }
        }

        // Profile Hero Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Cover Banner with Meskot Logo Horizontal Gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(com.example.ui.theme.MeskotLogoHorizontalBrush)
                    )

                    // Overlapping Avatar
                    Box(
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .padding(bottom = (-30).dp)
                    ) {
                        UserAvatar(
                            photoUrl = user.photoUrl,
                            name = user.displayName,
                            size = 84,
                            modifier = Modifier.border(3.dp, Color.White, CircleShape)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SelectionContainer {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = user.displayName,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Serif,
                                        color = Ink
                                    )
                                    if (user.isAdmin) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = "🛡️", fontSize = 16.sp)
                                    }
                                }

                                if (user.bio.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = user.bio,
                                        fontSize = 13.sp,
                                        color = MutedText,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }

                        if (user.gender.isNotBlank() || user.birthDate.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (user.gender.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Paper2
                                    ) {
                                        Text(
                                            text = "👤 ${user.gender}",
                                            fontSize = 12.sp,
                                            color = Ink,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                                if (user.birthDate.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Paper2
                                    ) {
                                        Text(
                                            text = "🎂 ${user.birthDate}",
                                            fontSize = 12.sp,
                                            color = Ink,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Actions
                        if (isMe) {
                            Button(
                                onClick = { viewModel.openEditProfile() },
                                colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.White),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(text = "✏️ " + MeskotStrings.get("editProfile", currentLanguage), fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                if (isFriend) {
                                    Button(
                                        onClick = { viewModel.openChat(user) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Ink, contentColor = Color.White),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(text = "💬 " + MeskotStrings.get("messageBtn", currentLanguage), fontWeight = FontWeight.Bold)
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.unfriend(user) },
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(text = MeskotStrings.get("unfriend", currentLanguage), color = CrossRed)
                                    }
                                } else {
                                    Button(
                                        onClick = { viewModel.sendFriendRequest(user) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.White),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(text = "+ " + MeskotStrings.get("addFriend", currentLanguage), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // User's Posts Feed
        item {
            Text(
                text = MeskotStrings.get("posts", currentLanguage),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        if (userPosts.isEmpty()) {
            item {
                EmptyNotice(text = MeskotStrings.get("noPostsYet", currentLanguage))
            }
        } else {
            items(userPosts) { post ->
                val comments = viewModel.getComments(post.id)
                PostCard(
                    post = post,
                    currentUserId = currentUser?.uid,
                    comments = comments,
                    currentLanguage = currentLanguage,
                    onAuthorClick = { viewModel.openProfileByUid(it) },
                    onToggleReaction = { pid, type -> viewModel.toggleReaction(pid, type) },
                    onShare = { viewModel.sharePost(it) },
                    onToggleSave = { viewModel.toggleSavePost(it) },
                    onTipClick = { viewModel.openTipModal(it) },
                    onOpenMenu = { viewModel.openPostMenu(it) },
                    onAddComment = { pid, text, parentId -> viewModel.addComment(pid, text, parentId) },
                    onToggleCommentLike = { pid, cid -> viewModel.toggleCommentLike(pid, cid) },
                    onDeleteComment = { pid, cid -> viewModel.deleteComment(pid, cid) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun MenuScreen(
    viewModel: MeskotViewModel,
    currentUser: User?,
    allUsers: List<User>,
    currentLanguage: AppLanguage
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .testTag("menu_screen")
    ) {
        // User Profile Banner
        if (currentUser != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.openProfile(currentUser) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UserAvatar(photoUrl = currentUser.photoUrl, name = currentUser.displayName, size = 52)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = currentUser.displayName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Ink)
                            Text(text = MeskotStrings.get("viewProfile", currentLanguage), fontSize = 12.sp, color = GoldDeep, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        // Grid Menu Options
        item {
            val menuItems = listOf(
                Triple("🏠", MeskotStrings.get("navFeed", currentLanguage), ScreenTab.FEED),
                Triple("👥", MeskotStrings.get("navFriends", currentLanguage), ScreenTab.FRIENDS),
                Triple("💬", MeskotStrings.get("navMessages", currentLanguage), ScreenTab.MESSAGES),
                Triple("👪", MeskotStrings.get("navGroups", currentLanguage), ScreenTab.GROUPS),
                Triple("🖼️", MeskotStrings.get("navPhotos", currentLanguage), ScreenTab.PHOTOS),
                Triple("🔔", MeskotStrings.get("navNotifs", currentLanguage), ScreenTab.NOTIFICATIONS),
                Triple("📊", "Dashboard", ScreenTab.DASHBOARD),
                Triple("🔖", MeskotStrings.get("savedPosts", currentLanguage), ScreenTab.SAVED)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                menuItems.take(2).forEach { item ->
                    MenuShortcutCard(
                        emoji = item.first,
                        title = item.second,
                        onClick = { viewModel.navigateTo(item.third) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                menuItems.drop(2).take(2).forEach { item ->
                    MenuShortcutCard(
                        emoji = item.first,
                        title = item.second,
                        onClick = { viewModel.navigateTo(item.third) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                menuItems.drop(4).take(2).forEach { item ->
                    MenuShortcutCard(
                        emoji = item.first,
                        title = item.second,
                        onClick = { viewModel.navigateTo(item.third) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                menuItems.drop(6).take(2).forEach { item ->
                    MenuShortcutCard(
                        emoji = item.first,
                        title = item.second,
                        onClick = { viewModel.navigateTo(item.third) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Admin Panel if Admin
        if (currentUser?.isAdmin == true) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                MenuShortcutCard(
                    emoji = "🛡️",
                    title = MeskotStrings.get("adminPanel", currentLanguage),
                    onClick = { viewModel.navigateTo(ScreenTab.ADMIN) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Language & Log Out
        item {
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleLanguage() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🌐 " + MeskotStrings.get("language", currentLanguage), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Ink)
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "English (US)" else "አማርኛ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDeep
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.logout() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🚪 " + MeskotStrings.get("logOut", currentLanguage), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = CrossRed)
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun MenuShortcutCard(
    emoji: String,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Ink)
        }
    }
}

enum class AuthMode {
    SIGN_IN,
    SIGN_UP,
    FORGOT_PASSWORD
}

@Composable
fun AuthScreen(
    viewModel: MeskotViewModel,
    allUsers: List<User> = emptyList(),
    currentLanguage: AppLanguage
) {
    var authMode by remember { mutableStateOf(AuthMode.SIGN_IN) }

    // Sign in state
    var signInEmail by remember { mutableStateOf("") }
    var signInPassword by remember { mutableStateOf("") }
    var isSignInPasswordVisible by remember { mutableStateOf(false) }

    // Facebook-style Sign up state
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var signUpEmail by remember { mutableStateOf("") }
    var signUpPassword by remember { mutableStateOf("") }
    var isSignUpPasswordVisible by remember { mutableStateOf(false) }

    // Birthday state
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    var selectedMonth by remember { mutableStateOf("Jan") }
    var isMonthMenuExpanded by remember { mutableStateOf(false) }

    val days = (1..31).map { it.toString() }
    var selectedDay by remember { mutableStateOf("1") }
    var isDayMenuExpanded by remember { mutableStateOf(false) }

    val years = (2012 downTo 1940).map { it.toString() }
    var selectedYear by remember { mutableStateOf("2000") }
    var isYearMenuExpanded by remember { mutableStateOf(false) }

    // Gender state (Facebook style: Female, Male, Custom)
    var selectedGender by remember { mutableStateOf("Female") }
    var customGenderDescription by remember { mutableStateOf("") }

    // Forgot password state
    var forgotEmail by remember { mutableStateOf("") }
    var resetSuccessMessage by remember { mutableStateOf<String?>(null) }

    // Common feedback state
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paper)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 28.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Meskot Emblem
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(com.example.ui.theme.MeskotLogoBrush)
                .border(2.5.dp, com.example.ui.theme.GoldBorder, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 10.dp, bottomEnd = 10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(3.5.dp)
                    .height(52.dp)
                    .background(Color.White.copy(alpha = 0.9f))
            )
            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(3.5.dp)
                    .background(Color.White.copy(alpha = 0.9f))
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Meskot",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Ink,
            letterSpacing = (-0.5).sp
        )
        Text(
            text = "መስኮት · Ethiopian & Habesha Community Network",
            fontSize = 12.sp,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                when (authMode) {
                    AuthMode.SIGN_IN -> {
                        // Tab switcher (Sign in vs Register)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(com.example.ui.theme.GoldSurface)
                                .border(1.dp, com.example.ui.theme.GoldBorder, RoundedCornerShape(12.dp))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(com.example.ui.theme.MeskotLogoBrush)
                                    .padding(vertical = 9.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = MeskotStrings.get("signIn", currentLanguage),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(Color.Transparent)
                                    .clickable {
                                        authMode = AuthMode.SIGN_UP
                                        errorMessage = null
                                    }
                                    .padding(vertical = 9.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = MeskotStrings.get("createAccount", currentLanguage),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = com.example.ui.theme.GoldDeep
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = signInEmail,
                            onValueChange = { signInEmail = it },
                            label = { Text(MeskotStrings.get("email", currentLanguage)) },
                            placeholder = { Text("email@example.com") },
                            textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                            colors = meskotTextFieldColors(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            enabled = !isLoading
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = signInPassword,
                            onValueChange = { signInPassword = it },
                            label = { Text(MeskotStrings.get("password", currentLanguage)) },
                            textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                            colors = meskotTextFieldColors(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            enabled = !isLoading,
                            visualTransformation = if (isSignInPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isSignInPasswordVisible = !isSignInPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isSignInPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = MutedText
                                    )
                                }
                            }
                        )

                        // Facebook-style "Forgot password?" Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = MeskotStrings.get("forgotPassword", currentLanguage),
                                color = GoldDeep,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable {
                                        authMode = AuthMode.FORGOT_PASSWORD
                                        forgotEmail = signInEmail.trim()
                                        errorMessage = null
                                        resetSuccessMessage = null
                                    }
                                    .padding(vertical = 6.dp)
                            )
                        }

                        if (errorMessage != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = errorMessage!!, color = CrossRed, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                errorMessage = null
                                if (signInEmail.isBlank() || signInPassword.isBlank()) {
                                    errorMessage = "Please enter email and password"
                                } else {
                                    isLoading = true
                                    viewModel.login(signInEmail, signInPassword) { ok, err ->
                                        isLoading = false
                                        if (!ok) {
                                            errorMessage = err ?: "Invalid email or password"
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = MeskotStrings.get("signIn", currentLanguage),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = LineBorder)
                            Text(
                                text = "  or  ",
                                fontSize = 12.sp,
                                color = MutedText
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f), color = LineBorder)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Facebook-style "Create new account" Green Button
                        Button(
                            onClick = {
                                authMode = AuthMode.SIGN_UP
                                errorMessage = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ActiveGreen, contentColor = Color.White),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Text(
                                text = MeskotStrings.get("createAccount", currentLanguage),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    AuthMode.FORGOT_PASSWORD -> {
                        // Facebook-style "Find Your Account" Recovery
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Gold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = GoldDeep,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = MeskotStrings.get("findYourAccount", currentLanguage),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Ink
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = MeskotStrings.get("resetPasswordInstructions", currentLanguage),
                                fontSize = 13.sp,
                                color = MutedText,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            if (resetSuccessMessage != null) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = ActiveGreen.copy(alpha = 0.12f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, ActiveGreen.copy(alpha = 0.3f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = ActiveGreen,
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = resetSuccessMessage!!,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Ink
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = "Please check your inbox (and spam folder) for the password reset link.",
                                                fontSize = 12.sp,
                                                color = MutedText
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(18.dp))

                                Button(
                                    onClick = {
                                        authMode = AuthMode.SIGN_IN
                                        errorMessage = null
                                        resetSuccessMessage = null
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Ink, contentColor = Color.White),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth().height(46.dp)
                                ) {
                                    Text(
                                        text = MeskotStrings.get("backToLogin", currentLanguage),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                OutlinedTextField(
                                    value = forgotEmail,
                                    onValueChange = { forgotEmail = it },
                                    label = { Text(MeskotStrings.get("email", currentLanguage)) },
                                    placeholder = { Text("email@example.com") },
                                    textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                    colors = meskotTextFieldColors(),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                    singleLine = true,
                                    enabled = !isLoading
                                )

                                if (errorMessage != null) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = errorMessage!!, color = CrossRed, fontSize = 12.sp)
                                }

                                Spacer(modifier = Modifier.height(18.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            authMode = AuthMode.SIGN_IN
                                            errorMessage = null
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        enabled = !isLoading
                                    ) {
                                        Text(
                                            text = MeskotStrings.get("cancel", currentLanguage),
                                            color = MutedText
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            errorMessage = null
                                            if (forgotEmail.isBlank() || !forgotEmail.contains("@")) {
                                                errorMessage = "Please enter a valid email address"
                                            } else {
                                                isLoading = true
                                                viewModel.forgotPassword(forgotEmail) { ok, err ->
                                                    isLoading = false
                                                    if (ok) {
                                                        resetSuccessMessage = MeskotStrings.get("resetEmailSent", currentLanguage)
                                                        errorMessage = null
                                                    } else {
                                                        errorMessage = err ?: "Could not send reset email"
                                                    }
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.White),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        enabled = !isLoading
                                    ) {
                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(20.dp),
                                                color = Color.White,
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Text(
                                                text = MeskotStrings.get("sendResetLink", currentLanguage),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    AuthMode.SIGN_UP -> {
                        // Facebook-style Create Account Registration
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = MeskotStrings.get("joinTitle", currentLanguage),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Ink
                            )
                            Text(
                                text = MeskotStrings.get("joinSub", currentLanguage),
                                fontSize = 12.sp,
                                color = MutedText
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Side-by-side First name & Last name (Facebook style)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    label = { Text(MeskotStrings.get("firstName", currentLanguage)) },
                                    textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                    colors = meskotTextFieldColors(),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    singleLine = true,
                                    enabled = !isLoading
                                )
                                OutlinedTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    label = { Text(MeskotStrings.get("lastName", currentLanguage)) },
                                    textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                    colors = meskotTextFieldColors(),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    singleLine = true,
                                    enabled = !isLoading
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Email / Mobile
                            OutlinedTextField(
                                value = signUpEmail,
                                onValueChange = { signUpEmail = it },
                                label = { Text(MeskotStrings.get("email", currentLanguage)) },
                                placeholder = { Text("email@example.com") },
                                textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                colors = meskotTextFieldColors(),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                enabled = !isLoading
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // New Password
                            OutlinedTextField(
                                value = signUpPassword,
                                onValueChange = { signUpPassword = it },
                                label = { Text(MeskotStrings.get("password", currentLanguage)) },
                                textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                colors = meskotTextFieldColors(),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                enabled = !isLoading,
                                visualTransformation = if (isSignUpPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { isSignUpPasswordVisible = !isSignUpPasswordVisible }) {
                                        Icon(
                                            imageVector = if (isSignUpPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = null,
                                            tint = MutedText
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Facebook-style Birthday Selection
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = MeskotStrings.get("birthday", currentLanguage),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ink
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MutedText,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = MeskotStrings.get("birthdaySub", currentLanguage),
                                fontSize = 11.sp,
                                color = MutedText
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // 3 Dropdown Pickers: Month, Day, Year
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Month Selector
                                Box(modifier = Modifier.weight(1.1f)) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder),
                                        color = Paper2,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { isMonthMenuExpanded = true }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = selectedMonth, fontSize = 13.sp, color = Ink, fontWeight = FontWeight.Medium)
                                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Ink)
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = isMonthMenuExpanded,
                                        onDismissRequest = { isMonthMenuExpanded = false }
                                    ) {
                                        months.forEach { m ->
                                            DropdownMenuItem(
                                                text = { Text(m) },
                                                onClick = {
                                                    selectedMonth = m
                                                    isMonthMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }

                                // Day Selector
                                Box(modifier = Modifier.weight(0.9f)) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder),
                                        color = Paper2,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { isDayMenuExpanded = true }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = selectedDay, fontSize = 13.sp, color = Ink, fontWeight = FontWeight.Medium)
                                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Ink)
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = isDayMenuExpanded,
                                        onDismissRequest = { isDayMenuExpanded = false }
                                    ) {
                                        days.forEach { d ->
                                            DropdownMenuItem(
                                                text = { Text(d) },
                                                onClick = {
                                                    selectedDay = d
                                                    isDayMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }

                                // Year Selector
                                Box(modifier = Modifier.weight(1.1f)) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, LineBorder),
                                        color = Paper2,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { isYearMenuExpanded = true }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = selectedYear, fontSize = 13.sp, color = Ink, fontWeight = FontWeight.Medium)
                                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Ink)
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = isYearMenuExpanded,
                                        onDismissRequest = { isYearMenuExpanded = false }
                                    ) {
                                        years.forEach { y ->
                                            DropdownMenuItem(
                                                text = { Text(y) },
                                                onClick = {
                                                    selectedYear = y
                                                    isYearMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Facebook-style Gender Selection (Female, Male, Custom)
                            Text(
                                text = MeskotStrings.get("gender", currentLanguage),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Ink
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("Female", "Male", "Custom").forEach { g ->
                                    val isSelected = selectedGender == g
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) Ink else LineBorder
                                        ),
                                        color = if (isSelected) Paper2 else Color.Transparent,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { selectedGender = g }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = when (g) {
                                                    "Female" -> MeskotStrings.get("female", currentLanguage)
                                                    "Male" -> MeskotStrings.get("male", currentLanguage)
                                                    else -> MeskotStrings.get("custom", currentLanguage)
                                                },
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Ink
                                            )
                                            RadioButton(
                                                selected = isSelected,
                                                onClick = { selectedGender = g },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Ink,
                                                    unselectedColor = MutedText
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            // Custom Gender / Pronoun description
                            if (selectedGender == "Custom") {
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = customGenderDescription,
                                    onValueChange = { customGenderDescription = it },
                                    label = { Text(MeskotStrings.get("customGenderPrompt", currentLanguage)) },
                                    placeholder = { Text("e.g. She/Her, He/Him, They/Them") },
                                    textStyle = TextStyle(color = Ink, fontSize = 14.sp),
                                    colors = meskotTextFieldColors(),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    singleLine = true,
                                    enabled = !isLoading
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Facebook-style Terms Notice
                            Text(
                                text = MeskotStrings.get("termsAgreement", currentLanguage),
                                fontSize = 11.sp,
                                color = MutedText,
                                lineHeight = 15.sp
                            )

                            if (errorMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = errorMessage!!, color = CrossRed, fontSize = 12.sp)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Facebook-style Sign Up Button
                            Button(
                                onClick = {
                                    errorMessage = null
                                    val full = "${firstName.trim()} ${lastName.trim()}".trim()
                                    if (firstName.isBlank() || lastName.isBlank()) {
                                        errorMessage = "Please enter your first and last name"
                                    } else if (signUpEmail.isBlank() || !signUpEmail.contains("@")) {
                                        errorMessage = "Please enter a valid email address"
                                    } else if (signUpPassword.length < 6) {
                                        errorMessage = "Password must be at least 6 characters"
                                    } else {
                                        isLoading = true
                                        val finalGender = if (selectedGender == "Custom" && customGenderDescription.isNotBlank()) {
                                            customGenderDescription.trim()
                                        } else {
                                            selectedGender
                                        }
                                        val birthDate = "$selectedMonth $selectedDay, $selectedYear"
                                        viewModel.signup(
                                            fullName = full,
                                            email = signUpEmail,
                                            pass = signUpPassword,
                                            gender = finalGender,
                                            birthDate = birthDate,
                                            phoneNumber = ""
                                        ) { ok, err ->
                                            isLoading = false
                                            if (!ok) {
                                                errorMessage = err ?: "Failed to create account"
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !isLoading,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = MeskotStrings.get("createAccount", currentLanguage),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // "Already have an account? Log in"
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = MeskotStrings.get("hasAccount", currentLanguage) + " ",
                                    fontSize = 13.sp,
                                    color = MutedText
                                )
                                Text(
                                    text = MeskotStrings.get("logInLink", currentLanguage),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldDeep,
                                    modifier = Modifier.clickable {
                                        authMode = AuthMode.SIGN_IN
                                        errorMessage = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
