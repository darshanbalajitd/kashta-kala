package com.example.kashtakala

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// ── Locale helpers ────────────────────────────────────────────────────────────

/** Apply the stored language to the app context so system strings resolve correctly. */
fun applyLocale(context: Context, langCode: String): Context {
    val locale = when (langCode) {
        "kn" -> java.util.Locale("kn", "IN")
        "hi" -> java.util.Locale("hi", "IN")
        else -> java.util.Locale.ENGLISH
    }
    java.util.Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    @Suppress("DEPRECATION")
    return context.createConfigurationContext(config)
}

// ── Screen sealed class ───────────────────────────────────────────────────────

sealed class Screen {
    object Loading           : Screen()
    object LanguageSelection : Screen()
    data class Dashboard(val restoreCatId: String? = null, val restoreSubId: String? = null) : Screen()
    data class ProductDetail(val productId: Long, val originCatId: String, val originSubId: String?) : Screen()
    data class EditProduct  (val productId: Long, val originCatId: String, val originSubId: String?) : Screen()
    data class Checkout      (val productId: Long, val originCatId: String, val originSubId: String?) : Screen()
    object QuoteHistory      : Screen()
    object Settings          : Screen()
}

// ── Activity ──────────────────────────────────────────────────────────────────

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { KashtaKalaApp() } }
    }
}

fun assetUri(path: String): Uri = Uri.parse("file:///android_asset/$path".removePrefix("asset://").let { "file:///android_asset/$it" })

// ── Root composable ────────────────────────────────────────────────────────────

@Composable
fun KashtaKalaApp() {
    val context = LocalContext.current
    val db      = remember { KashtaKalaDatabase.getDatabase(context) }
    val dao     = db.dao()
    val scope   = rememberCoroutineScope()
    var seeded        by remember { mutableStateOf(false) }
    val settings      by dao.getSettings().collectAsState(initial = null)
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Loading) }

    // Apply locale whenever language changes
    val lang = settings?.languageCode ?: "en"
    LaunchedEffect(lang) { applyLocale(context, lang) }

    LaunchedEffect(settings, seeded) {
        when {
            settings == null && !seeded -> scope.launch {
                dao.updateSettings(AppSettings(isFirstLaunch = true))
                seedInitialData(dao)
                seeded = true
            }
            // Show language selection only on first launch (no lang stored)
            settings != null && settings?.languageCode == null ->
                currentScreen = Screen.LanguageSelection
            settings != null && currentScreen == Screen.Loading ->
                currentScreen = Screen.Dashboard()
        }
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (val s = currentScreen) {
            Screen.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }

            Screen.LanguageSelection -> LanguageSelectionScreen { chosen ->
                scope.launch {
                    // Persist language — isFirstLaunch = false so screen never shows again
                    dao.updateSettings(AppSettings(languageCode = chosen, isFirstLaunch = false))
                    currentScreen = Screen.Dashboard()
                }
            }

            is Screen.Dashboard -> DashboardScreen(lang, dao, s) { currentScreen = it }

            is Screen.ProductDetail -> ProductDetailScreen(
                lang, s.productId, dao,
                onBack = { currentScreen = Screen.Dashboard(s.originCatId, s.originSubId) },
                onEdit = { currentScreen = Screen.EditProduct(s.productId, s.originCatId, s.originSubId) },
                onCheckout = { currentScreen = Screen.Checkout(s.productId, s.originCatId, s.originSubId) }
            )

            is Screen.EditProduct -> EditProductScreen(
                lang, s.productId, dao,
                onBack = { currentScreen = Screen.ProductDetail(s.productId, s.originCatId, s.originSubId) }
            )

            is Screen.Checkout -> CheckoutScreen(
                lang, s.productId, dao,
                onBack = { currentScreen = Screen.ProductDetail(s.productId, s.originCatId, s.originSubId) },
                onConfirmed = { currentScreen = Screen.QuoteHistory }
            )

            Screen.QuoteHistory -> QuoteHistoryScreen(lang, dao) { currentScreen = Screen.Dashboard() }
            Screen.Settings -> SettingsScreen(lang, dao) { currentScreen = Screen.Dashboard() }
        }
    }
}

// ── Language Selection ─────────────────────────────────────────────────────────

@Composable
fun LanguageSelectionScreen(onLanguageSelected: (String) -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Language, null, Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))
        Text(
            "Select Language\nಭಾಷೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ\nभाषा चुनें",
            style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(48.dp))
        LangBtn("English")             { onLanguageSelected("en") }
        Spacer(Modifier.height(16.dp))
        LangBtn("Kannada - ಕನ್ನಡ")  { onLanguageSelected("kn") }
        Spacer(Modifier.height(16.dp))
        LangBtn("Hindi - हिन्दी")     { onLanguageSelected("hi") }
    }
}

@Composable
private fun LangBtn(text: String, onClick: () -> Unit) =
    OutlinedButton(
        onClick, Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)
    ) { Text(text, style = MaterialTheme.typography.bodyLarge) }

// ── Dashboard ──────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(lang: String, dao: KashtaKalaDao, screen: Screen.Dashboard, onNavigate: (Screen) -> Unit) {
    val rootCategories by dao.getRootCategories().collectAsState(initial = emptyList())
    // Default: pre-select "sofa" category (or restored category)
    var selectedRootId  by remember { mutableStateOf<String?>(screen.restoreCatId) }
    var activeCatId     by remember { mutableStateOf<String?>(screen.restoreSubId) }
    var editMode        by remember { mutableStateOf(false) }
    var portfolioMode   by remember { mutableStateOf(false) }
    val subCategories   by dao.getSubCategories(selectedRootId ?: "").collectAsState(initial = emptyList())
    val allProducts     by dao.getProductsByCategory(activeCatId ?: "__none__").collectAsState(initial = emptyList())
    val customProducts  by dao.getCustomProductsByCategory(activeCatId ?: "__none__").collectAsState(initial = emptyList())
    val products = if (portfolioMode) customProducts else allProducts
    val scope          = rememberCoroutineScope()
    val ctx            = LocalContext.current

    // Auto-select Sofa on first load (skip if restoring a previous category)
    LaunchedEffect(rootCategories) {
        if (selectedRootId == null && rootCategories.isNotEmpty()) {
            val sofa = rootCategories.firstOrNull { it.id == "sofa" } ?: rootCategories.first()
            selectedRootId = sofa.id
        }
    }

    LaunchedEffect(selectedRootId) { editMode = false }

    LaunchedEffect(selectedRootId, subCategories) {
        val rid = selectedRootId ?: return@LaunchedEffect
        if (subCategories.isEmpty()) {
            activeCatId = rid
        } else if (activeCatId == null || subCategories.none { it.id == activeCatId }) {
            // auto-select first sub-category
            activeCatId = subCategories.first().id
        }
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var addEn  by remember { mutableStateOf("") }
    var addKn  by remember { mutableStateOf("") }
    var addHi  by remember { mutableStateOf("") }
    var addUri by remember { mutableStateOf<Uri?>(null) }
    var deleteConfirm by remember { mutableStateOf<Product?>(null) }
    var duplicateError by remember { mutableStateOf(false) }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            try { ctx.contentResolver.takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION) }
            catch (_: SecurityException) {}
            addUri = uri
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kashta Kala") },
                actions = {
                    IconButton({ portfolioMode = !portfolioMode }) {
                        Icon(
                            if (portfolioMode) Icons.Default.WorkspacePremium else Icons.Default.WorkOutline,
                            "Portfolio",
                            tint = if (portfolioMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton({ onNavigate(Screen.QuoteHistory) }) { Icon(Icons.Default.Receipt, "Quotes") }
                    IconButton({ onNavigate(Screen.Settings) })     { Icon(Icons.Default.Settings, "Settings") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize()) {
            item {
                Text(
                    "Categories", style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(
                    Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(rootCategories) { cat ->
                        FilterChip(
                            selected = selectedRootId == cat.id,
                            onClick  = {
                                selectedRootId = cat.id
                                activeCatId    = null
                                editMode       = false
                            },
                            label    = { Text(cat.localName(lang)) },
                            leadingIcon = if (selectedRootId == cat.id)
                                ({ Icon(Icons.Default.Check, null, Modifier.size(16.dp)) }) else null
                        )
                    }
                }
                Divider(Modifier.padding(top = 8.dp))
                if (portfolioMode) {
                    Row(
                        Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiaryContainer).padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.WorkspacePremium, null, Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(Modifier.width(6.dp))
                        Text("Portfolio mode — showing your products only", style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }
            }

            // Sub-category row (only when parent has sub-categories)
            if (selectedRootId != null && subCategories.isNotEmpty()) {
                item {
                    Text(
                        "Type", style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 4.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyRow(
                        Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(subCategories) { sub ->
                            FilterChip(
                                selected = activeCatId == sub.id,
                                onClick  = { activeCatId = sub.id; editMode = false },
                                label    = { Text(sub.localName(lang)) },
                                leadingIcon = if (activeCatId == sub.id)
                                    ({ Icon(Icons.Default.Check, null, Modifier.size(16.dp)) }) else null
                            )
                        }
                    }
                    Divider(Modifier.padding(top = 8.dp))
                }
            }

            // Products section (shown as soon as a category is active — no placeholder)
            if (activeCatId != null) {
                item {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Products", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                        if (editMode) {
                            IconButton(onClick = { showAddDialog = true }, Modifier.size(36.dp)) {
                                Icon(Icons.Default.Add, "Add", Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        FilledTonalButton(
                            onClick = { editMode = !editMode },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(if (editMode) Icons.Default.Close else Icons.Default.Edit, null, Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (editMode) "Done" else "Edit", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                if (products.isEmpty()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(32.dp), Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Inventory2, null, Modifier.size(40.dp), tint = MaterialTheme.colorScheme.outline)
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    if (editMode) "No products yet — tap + to add." else "No products yet. Tap Edit to add one.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(products, key = { it.id }) { product ->
                        DashboardProductRow(
                            product, lang, editMode,
                            onTap    = { onNavigate(Screen.ProductDetail(product.id, selectedRootId!!, activeCatId)) },
                            onFav    = { scope.launch { dao.updateProduct(product.copy(isFavorite = !product.isFavorite, lastModified = System.currentTimeMillis())) } },
                            onDelete = { deleteConfirm = product }
                        )
                    }
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }

    // Add product dialog (with duplicate check)
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false; addEn = ""; addKn = ""; addHi = ""; addUri = null; duplicateError = false },
            title = { Text("Add Product") },
            text  = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (duplicateError) {
                        Text("A product with this name already exists in this category.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall)
                    }
                    OutlinedTextField(addEn, { addEn = it; duplicateError = false }, Modifier.fillMaxWidth(), label = { Text("Name (English) *") }, singleLine = true)
                    OutlinedTextField(addKn, { addKn = it }, Modifier.fillMaxWidth(), label = { Text("Name (Kannada)") }, singleLine = true)
                    OutlinedTextField(addHi, { addHi = it }, Modifier.fillMaxWidth(), label = { Text("Name (Hindi)") }, singleLine = true)
                    OutlinedButton({ picker.launch("image/*") }, Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Image, null); Spacer(Modifier.width(8.dp))
                        Text(if (addUri != null) "Image selected" else "Pick Image (optional)")
                    }
                }
            },
            confirmButton = {
                TextButton(enabled = addEn.isNotBlank(), onClick = {
                    scope.launch {
                        val catId = selectedRootId!!
                        val exists = dao.findProductByName(catId, addEn.trim())
                        if (exists != null) {
                            duplicateError = true
                        } else {
                            val defaultUseGlass = catId !in listOf("sofa", "bed")
                            val defaultUsePlywood = catId in listOf("table", "cabinet", "wardrobe")
                            dao.insertProduct(
                                Product(
                                    categoryId    = catId,
                                    subCategoryId = if (subCategories.isNotEmpty()) activeCatId else null,
                                    nameEn        = addEn.trim(),
                                    nameKn        = addKn.trim().ifEmpty { addEn.trim() },
                                    nameHi        = addHi.trim().ifEmpty { addEn.trim() },
                                    imageUris     = addUri?.toString(),
                                    isCustom      = true,
                                    useGlass      = defaultUseGlass,
                                    usePlywood    = defaultUsePlywood
                                )
                            )
                            addEn = ""; addKn = ""; addHi = ""; addUri = null; showAddDialog = false; duplicateError = false
                        }
                    }
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton({ addEn = ""; addKn = ""; addHi = ""; addUri = null; showAddDialog = false; duplicateError = false }) { Text("Cancel") }
            }
        )
    }

    deleteConfirm?.let { p ->
        AlertDialog(
            onDismissRequest = { deleteConfirm = null },
            title = { Text("Delete Product") },
            text  = { Text("Delete \"${p.nameEn}\"? This cannot be undone.") },
            confirmButton = {
                TextButton({
                    scope.launch { dao.clearProductMaterials(p.id); dao.deleteProduct(p) }
                    deleteConfirm = null
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton({ deleteConfirm = null }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun DashboardProductRow(
    product: Product, lang: String, editMode: Boolean,
    onTap: () -> Unit, onFav: () -> Unit, onDelete: () -> Unit
) {
    val ctx = LocalContext.current
    val firstImage = product.imageUris?.split("|")?.firstOrNull()
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp).clickable { onTap() },
        shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant), Alignment.Center) {
                if (firstImage != null) {
                    AsyncImage(
                        ImageRequest.Builder(ctx).data(resolveImageData(ctx, firstImage)).crossfade(true).build(),
                        null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Chair, null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.localName(lang), style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                if (product.isFavorite) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Favorite, null, Modifier.size(10.dp), tint = Color(0xFFE53935))
                        Spacer(Modifier.width(3.dp))
                        Text("Favourite", style = MaterialTheme.typography.labelSmall, color = Color(0xFFE53935))
                    }
                }
            }
            if (editMode) {
                IconButton(onFav, Modifier.size(36.dp)) {
                    Icon(
                        if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, null,
                        Modifier.size(20.dp),
                        tint = if (product.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.outline
                    )
                }
                IconButton(onDelete, Modifier.size(36.dp)) {
                    Icon(Icons.Default.Delete, null, Modifier.size(20.dp), tint = MaterialTheme.colorScheme.error)
                }
            } else {
                Icon(Icons.Default.ChevronRight, null, Modifier.size(20.dp), tint = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

// ── Product Detail ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(
    lang: String, productId: Long, dao: KashtaKalaDao,
    onBack: () -> Unit, onEdit: () -> Unit, onCheckout: () -> Unit
) {
    val ctx   = LocalContext.current
    val scope = rememberCoroutineScope()
    val product           by dao.getProductById(productId).collectAsState(initial = null)
    val rawCategories     by dao.getRawMaterialCategories().collectAsState(initial = emptyList())
    val selectedMaterials by dao.getMaterialsForProduct(productId).collectAsState(initial = emptyList())
    val dimensions        by dao.getDimensionsForProduct(productId).collectAsState(initial = emptyList())
    val materialBreakdown by dao.getMaterialBreakdownForProduct(productId).collectAsState(initial = emptyList())
    val hardware          by dao.getHardwareForProduct(productId).collectAsState(initial = emptyList())

    // Auto-assign default material for each category (skip if already assigned)
    LaunchedEffect(rawCategories, productId) {
        rawCategories.forEach { rmCat ->
            val existing = dao.getSelectedTypeForCategory(productId, rmCat.id)
            if (existing == null) {
                val first = dao.getFirstTypeInCategory(rmCat.id)
                if (first != null) dao.insertProductMaterial(ProductMaterial(productId, first.id))
            }
        }
    }

    var showInfoSheet by remember { mutableStateOf(false) }
    BackHandler { onBack() }
    val p = product ?: return
    val imageUris = remember(p.imageUris) {
        p.imageUris?.split("|")?.filter { it.isNotBlank() } ?: emptyList()
    }

    // Compute totals (polish/laminate use totalWoodArea × price)
    val woodDims = dimensions.filter { it.dimensionKind in listOf("wood_surface", "wood_supporting", "wood_foot") }
    val totalWoodArea = woodDims.sumOf { it.value }

    val totalGlassArea = dimensions.filter { it.dimensionKind == "glass_area" }.sumOf { it.value }
    val materialTotal = materialBreakdown.sumOf { row ->
        when (row.materialKind) {
            "polish", "laminate", "wood" -> row.pricePerUnit * totalWoodArea
            "glass"                      -> row.pricePerUnit * totalGlassArea
            else                         -> row.lineCost
        }
    } + hardware.sumOf { it.lineCost }
    val labourCharge = materialTotal * 0.10
    val grandTotal   = materialTotal + labourCharge

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(p.localName(lang), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } },
                actions = {
                    IconButton({
                        scope.launch {
                            dao.updateProduct(p.copy(isFavorite = !p.isFavorite, lastModified = System.currentTimeMillis()))
                        }
                    }) {
                        Icon(
                            if (p.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Fav",
                            tint = if (p.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onEdit) { Icon(Icons.Default.Edit, "Edit") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = onCheckout,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, null); Spacer(Modifier.width(8.dp))
                    Text("Checkout  Rs.${"%.0f".format(grandTotal)}", style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) {
            // Image carousel
            item {
                Box(Modifier.fillMaxWidth().height(240.dp).background(MaterialTheme.colorScheme.surfaceVariant)) {
                    if (imageUris.isNotEmpty()) {
                        val pagerState = rememberPagerState { imageUris.size }
                        HorizontalPager(pagerState, Modifier.fillMaxSize()) { page ->
                            AsyncImage(
                                ImageRequest.Builder(ctx).data(resolveImageData(ctx, imageUris[page])).crossfade(true).build(),
                                null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                            )
                        }
                        if (imageUris.size > 1) {
                            Row(Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                repeat(imageUris.size) { i ->
                                    Box(Modifier.size(if (pagerState.currentPage == i) 10.dp else 7.dp).clip(CircleShape)
                                        .background(if (pagerState.currentPage == i) Color.White else Color.White.copy(.5f)))
                                }
                            }
                        }
                    } else {
                        Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ImageNotSupported, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.outline)
                            Spacer(Modifier.height(8.dp))
                            Text("No images — tap Edit to add", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Divider()
            }

            // Price summary card
            item {
                Card(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(Modifier.padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Estimated Price", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("Rs.${"%.2f".format(grandTotal)}", style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text("incl. labour (10%)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Materials", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("Rs.${"%.2f".format(materialTotal)}", style = MaterialTheme.typography.bodyMedium)
                            Text("Labour", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("Rs.${"%.2f".format(labourCharge)}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // General Dimensions
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("General Dimensions", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    IconButton(onClick = { showInfoSheet = true }, Modifier.size(32.dp)) {
                        Icon(Icons.Default.Info, "Breakdown", Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                }
                val generalDims = dimensions.filter { it.dimensionKind == "general" }
                if (generalDims.isEmpty()) {
                    Text("No general dimensions set.", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
                } else {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column {
                            generalDims.forEachIndexed { i, dim ->
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(dim.label, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                                    Text("${dim.value} ${dim.unit}", style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                                }
                                if (i < generalDims.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                            }
                        }
                    }
                }
            }

            // Wood Dimensions
            item {
                Text("Wood Dimensions", style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                val woodDimsFiltered = dimensions.filter { it.dimensionKind in listOf("wood_surface", "wood_supporting", "wood_foot") }
                if (woodDimsFiltered.isEmpty()) {
                    Text("No wood dimensions set.", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
                } else {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column {
                            woodDimsFiltered.forEachIndexed { i, dim ->
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(dim.label, style = MaterialTheme.typography.bodyMedium)
                                        Text(woodKindLabel(dim.dimensionKind), style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Text("${dim.value} ${dim.unit}", style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                                }
                                if (i < woodDimsFiltered.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                            }
                            Divider(Modifier.padding(horizontal = 14.dp))
                            Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp)) {
                                Text("Total Wood Area", Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                Text("${"%.2f".format(totalWoodArea)} sq.ft", style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                // Glass section (if enabled)
                if (p.useGlass) {
                    Spacer(Modifier.height(4.dp))
                    Text("Glass Dimensions", style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                    val glassDims = dimensions.filter { it.dimensionKind == "glass_area" }
                    if (glassDims.isEmpty()) {
                        Text("No glass dimensions set.", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
                    } else {
                        Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                            Column {
                                glassDims.forEachIndexed { i, dim ->
                                    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(dim.label, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                                        Text("${dim.value} ${dim.unit}", style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                                    }
                                    if (i < glassDims.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Hardware (bushes etc.)
            if (hardware.isNotEmpty()) {
                item {
                    Text("Hardware", style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column {
                            hardware.forEachIndexed { i, hw ->
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("${hw.quantity}× ${hw.nameEn}", Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                                    Text("@ Rs.${"%.0f".format(hw.priceEach)} = Rs.${"%.0f".format(hw.lineCost)}",
                                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                }
                                if (i < hardware.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                            }
                        }
                    }
                }
            }

            item { Text("Raw Materials", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) }

            items(rawCategories) { cat ->
                // Filter: if not useGlass, skip Glass category
                // If usePlywood, skip Polish; if not usePlywood, skip Laminate
                val shouldShow = when (cat.nameEn) {
                    "Glass Type"      -> p.useGlass
                    "Polish Type"     -> !p.usePlywood
                    "Laminate Sheets" -> p.usePlywood
                    else              -> true
                }
                if (shouldShow) {
                    ViewOnlyRawMaterialSection(lang, cat, dao, productId, selectedMaterials, scope, totalWoodArea)
                }
            }
        }
    }

    // Material breakdown sheet
    if (showInfoSheet) {
        AlertDialog(
            onDismissRequest = { showInfoSheet = false },
            title = { Text("Material Breakdown") },
            text  = {
                Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (materialBreakdown.isEmpty() && hardware.isEmpty()) {
                        Text("No materials selected.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        materialBreakdown.forEach { row ->
                            val cost = when (row.materialKind) {
                                "polish", "laminate", "wood" -> row.pricePerUnit * totalWoodArea
                                "glass"                      -> row.pricePerUnit * totalGlassArea
                                else                         -> row.lineCost
                            }
                            Card(shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                                Column(Modifier.padding(10.dp)) {
                                    Text(row.materialName(lang), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    when (row.materialKind) {
                                        "polish", "laminate" ->
                                            Text("Rs.${"%.0f".format(row.pricePerUnit)}/sq.ft × ${"%.2f".format(totalWoodArea)} sq.ft (total wood) = Rs.${"%.2f".format(cost)}",
                                                style = MaterialTheme.typography.labelMedium)
                                        "glass" -> Text("Glass Area: ${"%.2f".format(totalGlassArea)} sq.ft × Rs.${"%.0f".format(row.pricePerUnit)} = Rs.${"%.2f".format(cost)}",
                                            style = MaterialTheme.typography.labelMedium)
                                        "glass" -> Text("Glass Area: ${"%.2f".format(totalGlassArea)} sq.ft × Rs.${"%.0f".format(row.pricePerUnit)} = Rs.${"%.2f".format(cost)}",
                                            style = MaterialTheme.typography.labelMedium)
                                        else -> if (row.dimensionLabel != null && row.dimensionValue != null) {
                                            Text("${row.dimensionLabel}: ${row.dimensionValue} ${row.dimensionUnit ?: "sq.ft"}",
                                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Text("Rs.${"%.0f".format(row.pricePerUnit)}/sq.ft × ${row.dimensionValue} = Rs.${"%.2f".format(cost)}",
                                                style = MaterialTheme.typography.labelMedium)
                                        } else {
                                            Text("No dimension assigned — Rs.0.00", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            }
                        }
                        hardware.forEach { hw ->
                            Card(shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                                Column(Modifier.padding(10.dp)) {
                                    Text(hw.nameEn, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    Text("${hw.quantity} @ Rs.${"%.0f".format(hw.priceEach)} = Rs.${"%.2f".format(hw.lineCost)}",
                                        style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                        Divider(Modifier.padding(vertical = 4.dp))
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text("Materials", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            Text("Rs.${"%.2f".format(materialTotal)}")
                        }
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text("Labour (10%)", style = MaterialTheme.typography.labelMedium)
                            Text("Rs.${"%.2f".format(labourCharge)}")
                        }
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text("Grand Total", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Rs.${"%.2f".format(grandTotal)}", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            },
            confirmButton = { TextButton({ showInfoSheet = false }) { Text("Close") } }
        )
    }
}

@Composable
private fun ViewOnlyRawMaterialSection(
    lang: String, cat: RawMaterialCategory, dao: KashtaKalaDao,
    productId: Long, selectedMaterials: List<RawMaterialType>,
    scope: CoroutineScope, totalWoodArea: Double
) {
    val types by dao.getRawMaterialTypes(cat.id).collectAsState(initial = emptyList())
    if (types.isEmpty()) return
    val selectedType = selectedMaterials.firstOrNull { sel -> types.any { it.id == sel.id } }
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
        Text(cat.localName(lang), style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 4.dp))
        Card(shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(1.dp)) {
            Column {
                types.forEachIndexed { idx, type ->
                    Row(Modifier.fillMaxWidth()
                        .clickable { scope.launch { dao.selectMaterialInCategory(productId, cat.id, type.id) } }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedType?.id == type.id,
                            onClick = { scope.launch { dao.selectMaterialInCategory(productId, cat.id, type.id) } })
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(type.localName(lang), style = MaterialTheme.typography.bodyMedium)
                            // Show thickness for glass types
                            if (type.materialKind == "glass" && type.glassThicknessCm > 0) {
                                Text("Thickness: ${type.glassThicknessCm} cm", style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        val displayPrice = when (type.materialKind) {
                            "polish", "laminate", "wood" -> "Rs.${"%.0f".format(type.defaultPrice * totalWoodArea)} (total)"
                            else                         -> "Rs.${"%.0f".format(type.defaultPrice)}/sq.ft"
                        }
                        Text(displayPrice, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    if (idx < types.lastIndex) Divider(Modifier.padding(horizontal = 12.dp))
                }
            }
        }
    }
}

// ── Edit Product ────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EditProductScreen(lang: String, productId: Long, dao: KashtaKalaDao, onBack: () -> Unit) {
    val ctx   = LocalContext.current
    val scope = rememberCoroutineScope()
    val product       by dao.getProductById(productId).collectAsState(initial = null)
    val rawCategories by dao.getRawMaterialCategories().collectAsState(initial = emptyList())
    val dimensions    by dao.getDimensionsForProduct(productId).collectAsState(initial = emptyList())
    val selectedMats  by dao.getMaterialsForProduct(productId).collectAsState(initial = emptyList())
    val matDimLinks   by dao.getMaterialDimensionsForProduct(productId).collectAsState(initial = emptyList())
    val hardware      by dao.getHardwareForProduct(productId).collectAsState(initial = emptyList())

    var editDim      by remember { mutableStateOf<ProductDimension?>(null) }
    var editDimLabel by remember { mutableStateOf("") }
    var editDimValue by remember { mutableStateOf("") }
    var editDimUnit  by remember { mutableStateOf("sq.ft") }
    var editDimKind  by remember { mutableStateOf("general") }
    var showAddDim   by remember { mutableStateOf(false) }
    var newDimLabel  by remember { mutableStateOf("") }
    var newDimValue  by remember { mutableStateOf("") }
    var newDimUnit   by remember { mutableStateOf("sq.ft") }
    var newDimKind   by remember { mutableStateOf("general") }

    var showAddHw    by remember { mutableStateOf(false) }
    var hwName       by remember { mutableStateOf("") }
    var hwQty        by remember { mutableStateOf("") }
    var hwPrice      by remember { mutableStateOf("") }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty() && product != null) {
            uris.forEach { uri ->
                try { ctx.contentResolver.takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION) }
                catch (_: SecurityException) {}
            }
            val existing = product!!.imageUris?.split("|")?.filter { it.isNotBlank() } ?: emptyList()
            val merged   = (existing + uris.map { it.toString() }).joinToString("|")
            scope.launch { dao.updateProduct(product!!.copy(imageUris = merged, lastModified = System.currentTimeMillis())) }
        }
    }

    BackHandler { onBack() }
    val p = product ?: return
    val imageUris = remember(p.imageUris) {
        p.imageUris?.split("|")?.filter { it.isNotBlank() } ?: emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit: ${p.localName(lang)}", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(bottom = 40.dp)) {

            // Images
            item {
                Text("Images", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 6.dp))
                if (imageUris.isNotEmpty()) {
                    val pagerState = rememberPagerState { imageUris.size }
                    Box(Modifier.fillMaxWidth().height(200.dp)) {
                        HorizontalPager(pagerState, Modifier.fillMaxSize()) { page ->
                            AsyncImage(ImageRequest.Builder(ctx).data(resolveImageData(ctx, imageUris[page].toString())).crossfade(true).build(),
                                null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                        }
                        if (imageUris.size > 1) {
                            Row(Modifier.align(Alignment.BottomCenter).padding(bottom = 6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                repeat(imageUris.size) { i ->
                                    Box(Modifier.size(if (pagerState.currentPage == i) 10.dp else 7.dp).clip(CircleShape)
                                        .background(if (pagerState.currentPage == i) Color.White else Color.White.copy(.5f)))
                                }
                            }
                        }
                        IconButton(
                            onClick = { scope.launch { dao.updateProduct(p.copy(imageUris = null)) } },
                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).background(Color.Black.copy(.4f), CircleShape)
                        ) { Icon(Icons.Default.DeleteForever, "Remove images", tint = Color.White, modifier = Modifier.size(20.dp)) }
                    }
                }
                OutlinedButton({ imagePicker.launch("image/*") }, Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Icon(Icons.Default.AddPhotoAlternate, null); Spacer(Modifier.width(8.dp)); Text("Add Images")
                }
                Divider()
            }

            // Glass toggle
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Glass", style = MaterialTheme.typography.titleSmall)
                        Text("Enable glass surfaces for this product", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = p.useGlass, onCheckedChange = { scope.launch { dao.updateProduct(p.copy(useGlass = it)) } })
                }
                Divider()
            }

            // Plywood toggle
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Plywood", style = MaterialTheme.typography.titleSmall)
                        Text(if (p.usePlywood) "Plywood selected → Laminate visible, Polish hidden"
                             else "Solid wood selected → Polish visible, Laminate hidden",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = p.usePlywood, onCheckedChange = { scope.launch { dao.updateProduct(p.copy(usePlywood = it)) } })
                }
                Divider()
            }

            // Dimensions (for custom products only; preloaded products have them pre-filled)
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Dimensions", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    if (p.isCustom) {
                        IconButton(onClick = { showAddDim = true }, Modifier.size(32.dp)) {
                            Icon(Icons.Default.Add, "Add", Modifier.size(20.dp))
                        }
                    }
                }
                if (dimensions.isEmpty()) {
                    Text(
                        if (p.isCustom) "No dimensions yet — tap + to add." else "Dimensions are preloaded for this product.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                } else {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column {
                            dimensions.forEachIndexed { i, dim ->
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(dim.label, style = MaterialTheme.typography.bodyMedium)
                                        Text("${dim.value} ${dim.unit}  •  ${woodKindLabel(dim.dimensionKind)}",
                                            style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (p.isCustom) {
                                        IconButton(onClick = {
                                            editDim = dim; editDimLabel = dim.label
                                            editDimValue = dim.value.toString(); editDimUnit = dim.unit; editDimKind = dim.dimensionKind
                                        }, Modifier.size(32.dp)) { Icon(Icons.Default.Edit, "Edit", Modifier.size(16.dp)) }
                                        IconButton(onClick = { scope.launch { dao.deleteDimension(dim) } }, Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Delete, "Delete", Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                                if (i < dimensions.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 8.dp))
            }

            // Hardware section
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Hardware (Bushes / Fittings)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    IconButton(onClick = { showAddHw = true }, Modifier.size(32.dp)) {
                        Icon(Icons.Default.Add, "Add", Modifier.size(20.dp))
                    }
                }
                if (hardware.isEmpty()) {
                    Text("No hardware yet — tap + to add.", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
                } else {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column {
                            hardware.forEachIndexed { i, hw ->
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text("${hw.quantity}× ${hw.nameEn}", style = MaterialTheme.typography.bodyMedium)
                                        Text("@ Rs.${"%.0f".format(hw.priceEach)} each = Rs.${"%.0f".format(hw.lineCost)}",
                                            style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    IconButton({ scope.launch { dao.deleteHardware(hw) } }, Modifier.size(32.dp)) {
                                        Icon(Icons.Default.Delete, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                                if (i < hardware.lastIndex) Divider(Modifier.padding(horizontal = 14.dp))
                            }
                        }
                    }
                }
                Divider(Modifier.padding(top = 8.dp))
            }

            // Material ↔ Dimension mapping
            item {
                Text("Material to Dimension Mapping", style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                Text("Assign each selected material to the dimension it covers. Cost = price/sq.ft × sq.ft value.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp))
            }

            items(rawCategories) { cat ->
                val shouldShow = when (cat.nameEn) {
                    "Glass Type"      -> p.useGlass
                    "Polish Type"     -> !p.usePlywood
                    "Laminate Sheets" -> p.usePlywood
                    else              -> true
                }
                if (shouldShow) {
                    val woodArea = dimensions.filter { it.dimensionKind in listOf("wood_surface","wood_supporting","wood_foot") }.sumOf { it.value }
                    EditableMaterialDimSection(lang, cat, dao, productId, selectedMats, dimensions, matDimLinks, scope, woodArea)
                }
            }
        }
    }

    // Add Dimension dialog (only for custom products)
    if (showAddDim) {
        DimensionDialog("Add Dimension", newDimLabel, { newDimLabel = it }, newDimValue, { newDimValue = it },
            newDimUnit, { newDimUnit = it }, newDimKind, { newDimKind = it },
            onConfirm = {
                val v = newDimValue.toDoubleOrNull()
                if (newDimLabel.isNotBlank() && v != null) {
                    scope.launch {
                        dao.insertDimension(ProductDimension(productId = productId,
                            label = newDimLabel.trim(), value = v,
                            unit = newDimUnit.trim().ifEmpty { "sq.ft" }, dimensionKind = newDimKind))
                    }
                    newDimLabel = ""; newDimValue = ""; newDimUnit = "sq.ft"; newDimKind = "general"; showAddDim = false
                }
            },
            onDismiss = { newDimLabel = ""; newDimValue = ""; newDimUnit = "sq.ft"; newDimKind = "general"; showAddDim = false }
        )
    }

    editDim?.let { dim ->
        DimensionDialog("Edit Dimension", editDimLabel, { editDimLabel = it }, editDimValue, { editDimValue = it },
            editDimUnit, { editDimUnit = it }, editDimKind, { editDimKind = it },
            onConfirm = {
                val v = editDimValue.toDoubleOrNull()
                if (editDimLabel.isNotBlank() && v != null) {
                    scope.launch {
                        dao.updateDimension(dim.copy(label = editDimLabel.trim(), value = v,
                            unit = editDimUnit.trim().ifEmpty { "sq.ft" }, dimensionKind = editDimKind))
                    }
                    editDim = null
                }
            },
            onDismiss = { editDim = null }
        )
    }

    // Add Hardware dialog
    if (showAddHw) {
        AlertDialog(
            onDismissRequest = { showAddHw = false; hwName = ""; hwQty = ""; hwPrice = "" },
            title = { Text("Add Hardware") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(hwName, { hwName = it }, Modifier.fillMaxWidth(), label = { Text("Name (e.g. Bush) *") }, singleLine = true)
                    OutlinedTextField(hwQty,  { hwQty = it }, Modifier.fillMaxWidth(), label = { Text("Quantity *") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    OutlinedTextField(hwPrice, { hwPrice = it }, Modifier.fillMaxWidth(), label = { Text("Price per unit (Rs.) *") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
                }
            },
            confirmButton = {
                TextButton(
                    enabled = hwName.isNotBlank() && hwQty.toIntOrNull() != null && hwPrice.toDoubleOrNull() != null,
                    onClick = {
                        scope.launch {
                            dao.insertHardware(ProductHardware(productId = productId, nameEn = hwName.trim(),
                                nameKn = hwName.trim(), nameHi = hwName.trim(),
                                quantity = hwQty.toInt(), priceEach = hwPrice.toDouble()))
                        }
                        showAddHw = false; hwName = ""; hwQty = ""; hwPrice = ""
                    }
                ) { Text("Add") }
            },
            dismissButton = { TextButton({ showAddHw = false; hwName = ""; hwQty = ""; hwPrice = "" }) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditableMaterialDimSection(
    lang: String, cat: RawMaterialCategory, dao: KashtaKalaDao,
    productId: Long, selectedMats: List<RawMaterialType>, dimensions: List<ProductDimension>,
    matDimLinks: List<ProductMaterialDimension>, scope: CoroutineScope, totalWoodArea: Double
) {
    val types        by dao.getRawMaterialTypes(cat.id).collectAsState(initial = emptyList())
    val selectedType = selectedMats.firstOrNull { sel -> types.any { it.id == sel.id } } ?: return
    val isPolishOrLaminate = selectedType.materialKind in listOf("polish", "laminate", "wood")
    val linkedDimId  = matDimLinks.firstOrNull { it.productId == productId && it.materialTypeId == selectedType.id }?.dimensionId
    var dimExpanded  by remember { mutableStateOf(false) }
    val selectedDim  = dimensions.firstOrNull { it.id == linkedDimId }

    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
        Text(cat.localName(lang), style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 4.dp))
        Card(shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(1.dp)) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                // Material type selection
                types.forEach { type ->
                    Row(Modifier.fillMaxWidth().clickable { scope.launch { dao.selectMaterialInCategory(productId, cat.id, type.id) } }
                        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedType.id == type.id,
                            onClick = { scope.launch { dao.selectMaterialInCategory(productId, cat.id, type.id) } })
                        Spacer(Modifier.width(6.dp))
                        Column(Modifier.weight(1f)) {
                            Text(type.localName(lang), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            if (type.materialKind == "glass" && type.glassThicknessCm > 0) {
                                Text("Thickness: ${type.glassThicknessCm} cm", style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        val priceLabel = when (type.materialKind) {
                            "polish", "laminate" -> "Rs.${"%.0f".format(type.defaultPrice * totalWoodArea)} total"
                            else                 -> "Rs.${"%.0f".format(type.defaultPrice)}/sq.ft"
                        }
                        Text(priceLabel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                // Dimension assignment (not for polish/laminate/wood/glass — they auto-calculate)
                if (!isPolishOrLaminate && selectedType.materialKind != "glass") {
                    Spacer(Modifier.height(6.dp))
                    if (dimensions.isEmpty()) {
                        Text("Add dimensions above first.", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        ExposedDropdownMenuBox(dimExpanded, { dimExpanded = it }) {
                            OutlinedTextField(
                                value = selectedDim?.let { "${it.label} (${it.value} ${it.unit})" } ?: "assign dimension",
                                onValueChange = {}, readOnly = true,
                                label = { Text("Dimension", style = MaterialTheme.typography.labelSmall) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(dimExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                textStyle = MaterialTheme.typography.bodySmall
                            )
                            ExposedDropdownMenu(dimExpanded, { dimExpanded = false }) {
                                DropdownMenuItem({ Text("no dimension", style = MaterialTheme.typography.bodySmall) }, onClick = {
                                    scope.launch { dao.upsertMaterialDimension(ProductMaterialDimension(productId, selectedType.id, null)) }
                                    dimExpanded = false
                                })
                                dimensions.forEach { dim ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(dim.label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                                                Text("${dim.value} ${dim.unit}  →  Rs.${"%.2f".format(selectedType.defaultPrice * dim.value)}",
                                                    style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            }
                                        },
                                        onClick = {
                                            scope.launch { dao.upsertMaterialDimension(ProductMaterialDimension(productId, selectedType.id, dim.id)) }
                                            dimExpanded = false
                                        })
                                }
                            }
                        }
                        if (linkedDimId != null && selectedDim != null) {
                            Text("Rs.${"%.0f".format(selectedType.defaultPrice)} × ${selectedDim.value} ${selectedDim.unit} = Rs.${"%.2f".format(selectedType.defaultPrice * selectedDim.value)}",
                                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                } else if (selectedType.materialKind == "glass") {
                    // show nothing here; glass cost is shown in ProductDetail via glass_area dims
                } else {
                    Text("Cost auto-calculated: Rs.${"%.0f".format(selectedType.defaultPrice)}/sq.ft × ${"%.2f".format(totalWoodArea)} sq.ft total wood area = Rs.${"%.2f".format(selectedType.defaultPrice * totalWoodArea)}",
                        style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DimensionDialog(
    title: String,
    label: String, onLabelChange: (String) -> Unit,
    value: String, onValueChange: (String) -> Unit,
    unit: String, onUnitChange: (String) -> Unit,
    kind: String, onKindChange: (String) -> Unit,
    onConfirm: () -> Unit, onDismiss: () -> Unit
) {
    val unitOptions  = listOf("sq.ft", "cu.ft", "ft", "m2", "m3", "m", "cm")
    val kindOptions  = listOf(
        "general" to "General (length/breadth/height)",
        "wood_surface"    to "Wood – Surface Area",
        "wood_supporting" to "Wood – Supporting Area",
        "wood_foot"       to "Wood – Foot Area",
        "glass_area"      to "Glass – Surface Area"
    )
    var unitExpanded by remember { mutableStateOf(false) }
    var kindExpanded by remember { mutableStateOf(false) }

    AlertDialog(onDismissRequest = onDismiss, title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(label, onLabelChange, Modifier.fillMaxWidth(), label = { Text("Label *") }, singleLine = true)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value, onValueChange, Modifier.weight(1f), label = { Text("Value *") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
                    ExposedDropdownMenuBox(unitExpanded, { unitExpanded = it }, Modifier.weight(1f)) {
                        OutlinedTextField(value = unit, onValueChange = {}, readOnly = true,
                            label = { Text("Unit") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unitExpanded) },
                            modifier = Modifier.menuAnchor())
                        ExposedDropdownMenu(unitExpanded, { unitExpanded = false }) {
                            unitOptions.forEach { opt -> DropdownMenuItem({ Text(opt) }, onClick = { onUnitChange(opt); unitExpanded = false }) }
                        }
                    }
                }
                ExposedDropdownMenuBox(kindExpanded, { kindExpanded = it }) {
                    OutlinedTextField(
                        value = kindOptions.firstOrNull { it.first == kind }?.second ?: kind,
                        onValueChange = {}, readOnly = true,
                        label = { Text("Type") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(kindExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                    ExposedDropdownMenu(kindExpanded, { kindExpanded = false }) {
                        kindOptions.forEach { (k, display) ->
                            DropdownMenuItem({ Text(display, style = MaterialTheme.typography.bodySmall) }, onClick = { onKindChange(k); kindExpanded = false })
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onConfirm, enabled = label.isNotBlank() && value.toDoubleOrNull() != null) { Text("Save") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

// ── Checkout ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    lang: String, productId: Long, dao: KashtaKalaDao,
    onBack: () -> Unit, onConfirmed: () -> Unit
) {
    val scope     = rememberCoroutineScope()
    val product   by dao.getProductById(productId).collectAsState(initial = null)
    val breakdown by dao.getMaterialBreakdownForProduct(productId).collectAsState(initial = emptyList())
    val hardware  by dao.getHardwareForProduct(productId).collectAsState(initial = emptyList())
    val dims      by dao.getDimensionsForProduct(productId).collectAsState(initial = emptyList())
    var buyerName    by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf("") }
    var deliveryError by remember { mutableStateOf<String?>(null) }
    var adjType   by remember { mutableStateOf("percent") }
    var adjValue  by remember { mutableStateOf("10") }
    BackHandler { onBack() }
    val p = product ?: return

    val totalWoodArea = dims.filter { it.dimensionKind in listOf("wood_surface","wood_supporting","wood_foot") }.sumOf { it.value }
    val totalGlassArea = dims.filter { it.dimensionKind == "glass_area" }.sumOf { it.value }
    val materialTotal = breakdown.sumOf { row ->
        when (row.materialKind) {
            "polish", "laminate", "wood" -> row.pricePerUnit * totalWoodArea
            "glass"                      -> row.pricePerUnit * totalGlassArea
            else                         -> row.lineCost
        }
    } + hardware.sumOf { it.lineCost }

    val labourAdjusted: Double = when (adjType) {
        "percent" -> materialTotal * ((adjValue.toDoubleOrNull() ?: 10.0) / 100.0)
        else      -> adjValue.toDoubleOrNull() ?: (materialTotal * 0.10)
    }
    val grandTotal = materialTotal + labourAdjusted

    fun validateDate(s: String): Boolean = try { LocalDate.parse(s); true } catch (_: Exception) { false }

    fun buildSnapshot(): String = (breakdown.map { row ->
        val cost = when (row.materialKind) {
            "polish", "laminate", "wood" -> row.pricePerUnit * totalWoodArea
            "glass"                      -> row.pricePerUnit * totalGlassArea
            else                         -> row.lineCost
        }
        "${row.materialName(lang)}: Rs.${"%.2f".format(cost)}"
    } + hardware.map { "${it.nameEn} (${it.quantity}@Rs.${"%.0f".format(it.priceEach)}): Rs.${"%.2f".format(it.lineCost)}" }).joinToString(" | ")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout") },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(12.dp)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Chair, null, Modifier.size(28.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(12.dp))
                    Text(p.localName(lang), style = MaterialTheme.typography.titleMedium)
                }
            }
            OutlinedTextField(buyerName, { buyerName = it }, Modifier.fillMaxWidth(), label = { Text("Buyer Name *") },
                leadingIcon = { Icon(Icons.Default.Person, null) }, singleLine = true)
            OutlinedTextField(deliveryDate,
                { deliveryDate = it; deliveryError = if (it.isNotBlank() && !validateDate(it)) "Use format YYYY-MM-DD" else null },
                label = { Text("Delivery Date * (YYYY-MM-DD)") },
                leadingIcon = { Icon(Icons.Default.CalendarToday, null) },
                isError = deliveryError != null,
                supportingText = { deliveryError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                singleLine = true, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Text("Labour Adjustment", style = MaterialTheme.typography.titleSmall)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = adjType == "percent", onClick = { adjType = "percent"; adjValue = "10" },
                    label = { Text("Percentage (%)") }, modifier = Modifier.weight(1f))
                FilterChip(selected = adjType == "fixed", onClick = { adjType = "fixed"; adjValue = (materialTotal * 0.10).toInt().toString() },
                    label = { Text("Fixed Amount") }, modifier = Modifier.weight(1f))
            }
            OutlinedTextField(adjValue, { adjValue = it }, Modifier.fillMaxWidth(),
                label = { Text(if (adjType == "percent") "Labour % (negative = discount)" else "Labour Rs. (negative = discount)") },
                leadingIcon = { Text(if (adjType == "percent") "%" else "Rs", modifier = Modifier.padding(start = 12.dp)) },
                singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Divider()
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer), shape = RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Price Summary", style = MaterialTheme.typography.titleSmall)
                    Divider(Modifier.padding(vertical = 4.dp))
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("Materials Total"); Text("Rs.${"%.2f".format(materialTotal)}") }
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text(if (adjType == "percent") "Labour (${adjValue}%)" else "Labour (Rs.${adjValue})")
                        Text("Rs.${"%.2f".format(labourAdjusted)}")
                    }
                    Divider(Modifier.padding(vertical = 4.dp))
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("Grand Total", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("Rs.${"%.2f".format(grandTotal)}", style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (validateDate(deliveryDate)) {
                val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(deliveryDate))
                val (clr, msg) = when {
                    daysLeft < 0   -> Pair(MaterialTheme.colorScheme.error, "Delivery date has passed!")
                    daysLeft == 0L -> Pair(MaterialTheme.colorScheme.error, "Due today!")
                    daysLeft <= 7  -> Pair(Color(0xFFE65100), "Due in $daysLeft day(s)")
                    else           -> Pair(MaterialTheme.colorScheme.primary, "$daysLeft days remaining")
                }
                Text(msg, color = clr, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            }
            Button(
                enabled  = buyerName.isNotBlank() && validateDate(deliveryDate),
                onClick  = {
                    scope.launch {
                        dao.insertQuote(Quote(productId = productId, productNameEn = p.nameEn,
                            buyerName = buyerName.trim(), deliveryDate = deliveryDate,
                            materialTotal = materialTotal, labourAdjustmentType = adjType,
                            labourAdjustmentValue = adjValue.toDoubleOrNull() ?: labourAdjusted,
                            grandTotal = grandTotal, snapshotJson = buildSnapshot()))
                        onConfirmed()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
                Icon(Icons.Default.CheckCircle, null); Spacer(Modifier.width(8.dp))
                Text("Confirm Quote", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

// ── Quote History ───────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteHistoryScreen(lang: String, dao: KashtaKalaDao, onBack: () -> Unit) {
    val quotes by dao.getAllQuotes().collectAsState(initial = emptyList())
    val scope  = rememberCoroutineScope()
    var deleteConfirm by remember { mutableStateOf<Quote?>(null) }
    BackHandler { onBack() }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Quote History") },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))
        }
    ) { padding ->
        if (quotes.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ReceiptLong, null, Modifier.size(56.dp), tint = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(12.dp))
                    Text("No quotes yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Complete a checkout to create quotes.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item { Text("Sorted by nearest delivery date", style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)) }
                items(quotes, key = { it.id }) { quote -> QuoteCard(quote) { deleteConfirm = quote } }
            }
        }
    }
    deleteConfirm?.let { q ->
        AlertDialog(onDismissRequest = { deleteConfirm = null },
            title = { Text("Delete Quote") },
            text  = { Text("Delete quote for \"${q.buyerName}\" — ${q.productNameEn}?") },
            confirmButton = {
                TextButton({ scope.launch { dao.deleteQuote(q) }; deleteConfirm = null }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton({ deleteConfirm = null }) { Text("Cancel") } })
    }
}

@Composable
private fun QuoteCard(quote: Quote, onDelete: () -> Unit) {
    val today     = LocalDate.now()
    val delivDate = try { LocalDate.parse(quote.deliveryDate) } catch (_: Exception) { null }
    val daysLeft  = delivDate?.let { ChronoUnit.DAYS.between(today, it) }
    val urgencyColor = when {
        daysLeft == null -> MaterialTheme.colorScheme.outline
        daysLeft < 0    -> MaterialTheme.colorScheme.error
        daysLeft <= 3   -> Color(0xFFE65100)
        daysLeft <= 7   -> Color(0xFFF57F17)
        else            -> MaterialTheme.colorScheme.primary
    }
    Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(quote.productNameEn, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("Buyer: ${quote.buyerName}", style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onDelete, Modifier.size(32.dp)) { Icon(Icons.Default.Delete, "Delete", Modifier.size(18.dp), tint = MaterialTheme.colorScheme.error) }
            }
            Spacer(Modifier.height(8.dp)); Divider(); Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text("Delivery", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(quote.deliveryDate, style = MaterialTheme.typography.bodyMedium, color = urgencyColor, fontWeight = FontWeight.Medium)
                    val badge = when {
                        daysLeft == null  -> ""
                        daysLeft < 0     -> "Overdue"
                        daysLeft == 0L   -> "Due today!"
                        daysLeft == 1L   -> "Tomorrow!"
                        daysLeft <= 7    -> "In $daysLeft days"
                        else             -> "$daysLeft days left"
                    }
                    if (badge.isNotEmpty()) {
                        Surface(color = urgencyColor.copy(alpha = .15f), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 2.dp)) {
                            Text(badge, style = MaterialTheme.typography.labelSmall, color = urgencyColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Materials", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Rs.${"%.0f".format(quote.materialTotal)}", style = MaterialTheme.typography.bodySmall)
                    val labourLabel = if (quote.labourAdjustmentType == "percent")
                        "Labour (${quote.labourAdjustmentValue.toInt()}%)" else "Labour (Rs.${quote.labourAdjustmentValue.toInt()})"
                    Text(labourLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Rs.${"%.0f".format(quote.grandTotal - quote.materialTotal)}", style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(4.dp))
                    Text("Rs.${"%.2f".format(quote.grandTotal)}", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
            if (quote.snapshotJson.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(quote.snapshotJson, style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

// ── Settings ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(lang: String, dao: KashtaKalaDao, onBack: () -> Unit) {
    val rawCategories by dao.getRawMaterialCategories().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var showAddCat   by remember { mutableStateOf(false) }
    var newCatEn     by remember { mutableStateOf("") }
    var newCatKn     by remember { mutableStateOf("") }
    var newCatHi     by remember { mutableStateOf("") }
    var addTypeFor   by remember { mutableStateOf<RawMaterialCategory?>(null) }
    var newTypeEn    by remember { mutableStateOf("") }
    var newTypeKn    by remember { mutableStateOf("") }
    var newTypeHi    by remember { mutableStateOf("") }
    var newTypePrice by remember { mutableStateOf("") }
    var newTypeKind  by remember { mutableStateOf("other") }
    var editPriceFor by remember { mutableStateOf<RawMaterialType?>(null) }
    var editPriceVal by remember { mutableStateOf("") }
    var deleteCatConf by remember { mutableStateOf<RawMaterialCategory?>(null) }
    var dupCatError  by remember { mutableStateOf(false) }
    var dupTypeError by remember { mutableStateOf(false) }
    BackHandler { onBack() }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))
        },
        floatingActionButton = {
            FloatingActionButton({ showAddCat = true }) { Icon(Icons.Default.Add, "Add Category") }
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text("Raw Material Categories", style = MaterialTheme.typography.titleMedium)
                Text("Tap a price chip to edit. Use + to add types or categories.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            items(rawCategories, key = { it.id }) { cat ->
                SettingsCategoryCard(cat, lang, dao,
                    onAddType    = { addTypeFor = cat; dupTypeError = false },
                    onEditPrice  = { t -> editPriceFor = t; editPriceVal = t.defaultPrice.toInt().toString() },
                    onDeleteCat  = { deleteCatConf = cat }, scope = scope)
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }

    // Add category dialog (with duplicate guard)
    if (showAddCat) {
        AlertDialog(onDismissRequest = { showAddCat = false; newCatEn = ""; newCatKn = ""; newCatHi = ""; dupCatError = false },
            title = { Text("New Category") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (dupCatError) Text("A category with this name already exists.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    OutlinedTextField(newCatEn, { newCatEn = it; dupCatError = false }, Modifier.fillMaxWidth(), label = { Text("Name (English) *") }, singleLine = true)
                    OutlinedTextField(newCatKn, { newCatKn = it }, Modifier.fillMaxWidth(), label = { Text("Name (Kannada)") }, singleLine = true)
                    OutlinedTextField(newCatHi, { newCatHi = it }, Modifier.fillMaxWidth(), label = { Text("Name (Hindi)") }, singleLine = true)
                }
            },
            confirmButton = {
                TextButton(enabled = newCatEn.isNotBlank(), onClick = {
                    scope.launch {
                        val existing = dao.findRawCategoryByName(newCatEn.trim())
                        if (existing != null) {
                            dupCatError = true
                        } else {
                            dao.insertRawMaterialCategory(RawMaterialCategory(
                                nameEn = newCatEn.trim(),
                                nameKn = newCatKn.trim().ifEmpty { newCatEn.trim() },
                                nameHi = newCatHi.trim().ifEmpty { newCatEn.trim() }
                            ))
                            newCatEn = ""; newCatKn = ""; newCatHi = ""; showAddCat = false; dupCatError = false
                        }
                    }
                }) { Text("Add") }
            },
            dismissButton = { TextButton({ newCatEn = ""; newCatKn = ""; newCatHi = ""; showAddCat = false; dupCatError = false }) { Text("Cancel") } })
    }

    // Add type dialog (with duplicate guard)
    addTypeFor?.let { tc ->
        val kindOptions = listOf("wood","polish","laminate","glass","hardware","other")
        var kindExpanded by remember { mutableStateOf(false) }
        AlertDialog(onDismissRequest = { addTypeFor = null; newTypeEn = ""; newTypeKn = ""; newTypeHi = ""; newTypePrice = ""; dupTypeError = false },
            title = { Text("Add Type to \"${tc.localName(lang)}\"") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (dupTypeError) Text("A type with this name already exists.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    OutlinedTextField(newTypeEn, { newTypeEn = it; dupTypeError = false }, Modifier.fillMaxWidth(), label = { Text("Name (English) *") }, singleLine = true)
                    OutlinedTextField(newTypeKn, { newTypeKn = it }, Modifier.fillMaxWidth(), label = { Text("Name (Kannada)") }, singleLine = true)
                    OutlinedTextField(newTypeHi, { newTypeHi = it }, Modifier.fillMaxWidth(), label = { Text("Name (Hindi)") }, singleLine = true)
                    OutlinedTextField(newTypePrice, { newTypePrice = it }, Modifier.fillMaxWidth(),
                        label = { Text("Price per sq.ft (Rs.) *") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    ExposedDropdownMenuBox(kindExpanded, { kindExpanded = it }) {
                        OutlinedTextField(value = newTypeKind, onValueChange = {}, readOnly = true,
                            label = { Text("Material Kind") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(kindExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth())
                        ExposedDropdownMenu(kindExpanded, { kindExpanded = false }) {
                            kindOptions.forEach { k -> DropdownMenuItem({ Text(k) }, onClick = { newTypeKind = k; kindExpanded = false }) }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(enabled = newTypeEn.isNotBlank() && newTypePrice.toDoubleOrNull() != null, onClick = {
                    scope.launch {
                        val existing = dao.findRawTypeByName(tc.id, newTypeEn.trim())
                        if (existing != null) {
                            dupTypeError = true
                        } else {
                            dao.insertRawMaterialType(RawMaterialType(
                                categoryId   = tc.id,
                                nameEn       = newTypeEn.trim(),
                                nameKn       = newTypeKn.trim().ifEmpty { newTypeEn.trim() },
                                nameHi       = newTypeHi.trim().ifEmpty { newTypeEn.trim() },
                                defaultPrice = newTypePrice.toDouble(),
                                materialKind = newTypeKind
                            ))
                            newTypeEn = ""; newTypeKn = ""; newTypeHi = ""; newTypePrice = ""; addTypeFor = null; dupTypeError = false
                        }
                    }
                }) { Text("Add") }
            },
            dismissButton = { TextButton({ newTypeEn = ""; newTypeKn = ""; newTypeHi = ""; newTypePrice = ""; addTypeFor = null; dupTypeError = false }) { Text("Cancel") } })
    }

    // Edit price dialog
    editPriceFor?.let { t ->
        AlertDialog(onDismissRequest = { editPriceFor = null },
            title = { Text("Edit Price") },
            text = {
                Column {
                    Text(t.localName(lang), style = MaterialTheme.typography.bodyMedium)
                    Text("Kind: ${t.materialKind}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(editPriceVal, { editPriceVal = it }, Modifier.fillMaxWidth(),
                        label = { Text("Price per sq.ft (Rs.)") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = { Text("Rs", modifier = Modifier.padding(start = 12.dp)) })
                }
            },
            confirmButton = {
                TextButton(enabled = editPriceVal.toDoubleOrNull() != null, onClick = {
                    scope.launch { dao.updateRawMaterialType(t.copy(defaultPrice = editPriceVal.toDouble())) }
                    editPriceFor = null
                }) { Text("Save") }
            },
            dismissButton = { TextButton({ editPriceFor = null }) { Text("Cancel") } })
    }

    deleteCatConf?.let { cat ->
        AlertDialog(onDismissRequest = { deleteCatConf = null },
            title = { Text("Delete Category") },
            text  = { Text("Delete \"${cat.localName(lang)}\" and all its types?") },
            confirmButton = {
                TextButton({ scope.launch { dao.deleteRawMaterialCategory(cat) }; deleteCatConf = null }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton({ deleteCatConf = null }) { Text("Cancel") } })
    }
}

@Composable
private fun SettingsCategoryCard(
    cat: RawMaterialCategory, lang: String, dao: KashtaKalaDao,
    onAddType: () -> Unit, onEditPrice: (RawMaterialType) -> Unit, onDeleteCat: () -> Unit,
    scope: CoroutineScope
) {
    val types by dao.getRawMaterialTypes(cat.id).collectAsState(initial = emptyList())
    var deleteTypeConf by remember { mutableStateOf<RawMaterialType?>(null) }
    Card(shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(cat.localName(lang), style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.primary)
                IconButton(onAddType, Modifier.size(36.dp)) { Icon(Icons.Default.Add, null, Modifier.size(18.dp)) }
                IconButton(onDeleteCat, Modifier.size(36.dp)) { Icon(Icons.Default.Delete, null, Modifier.size(18.dp), tint = MaterialTheme.colorScheme.error) }
            }
            if (types.isEmpty()) {
                Text("No types yet — tap + to add", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                Divider(Modifier.padding(vertical = 6.dp))
                types.forEach { type ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(type.localName(lang), style = MaterialTheme.typography.bodyMedium)
                            Text(type.materialKind, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.clickable { onEditPrice(type) }) {
                            Text("Rs.${"%.0f".format(type.defaultPrice)}/sq.ft",
                                Modifier.padding(horizontal = 8.dp, vertical = 4.dp), style = MaterialTheme.typography.labelMedium)
                        }
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.Default.Edit, null, Modifier.size(13.dp), tint = MaterialTheme.colorScheme.outline)
                        Spacer(Modifier.width(8.dp))
                        IconButton({ deleteTypeConf = type }, Modifier.size(28.dp)) {
                            Icon(Icons.Default.Close, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
    deleteTypeConf?.let { t ->
        AlertDialog(onDismissRequest = { deleteTypeConf = null }, title = { Text("Delete Type") },
            text = { Text("Delete \"${t.localName(lang)}\"?") },
            confirmButton = {
                TextButton({ scope.launch { dao.deleteRawMaterialType(t) }; deleteTypeConf = null }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton({ deleteTypeConf = null }) { Text("Cancel") } })
    }
}

// ── Seed ────────────────────────────────────────────────────────────────────────

suspend fun seedInitialData(dao: KashtaKalaDao) {
    // Categories (IGNORE prevents duplication on re-seed)
    dao.insertCategories(listOf(
        Category("sofa",     "Sofa",     "ಸೋಫಾ",    "सोफा"),
        Category("bed",      "Bed",      "ಹಾಸಿಗೆ",  "बेड"),
        Category("cabinet",  "Cabinet",  "ಕ್ಯಾಬಿನೆಟ್", "कैबिनेट"),
        Category("table",    "Table",    "ಮೇಜು",   "टेबल"),
        Category("wardrobe", "Wardrobe", "ಅಲಮಾರಿ", "अलमारी")
    ))
    dao.insertCategories(listOf(
        Category("king",         "King Size",  "King Size",  "King Size",  "bed"),
        Category("queen",        "Queen Size", "Queen Size", "Queen Size", "bed"),
        Category("single",       "Single",     "Single",     "Single",     "bed"),
        Category("regular_bed",  "Regular",    "Regular",    "Regular",    "bed")
    ))
    dao.insertCategories(listOf(
        Category("office",        "Office",  "Office",  "Office",  "table"),
        Category("study",         "Study",   "Study",   "Study",   "table"),
        Category("dining",        "Dining",  "Dining",  "Dining",  "table"),
        Category("regular_table", "Regular", "Regular", "Regular", "table")
    ))

    // ── Raw Material Categories & Types ──────────────────────────────────────
    // Wood Type
    var woodCatId = dao.findRawCategoryByName("Wood Type")?.id
    if (woodCatId == null) {
        woodCatId = dao.insertRawMaterialCategory(RawMaterialCategory(nameEn = "Wood Type", nameKn = "ಮರದ ವಿಧ", nameHi = "लकड़ी का प्रकार"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Teak",    nameKn = "ತೇಗ",   nameHi = "सागवान", defaultPrice = 5000.0, materialKind = "wood"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Oak",     nameKn = "ಓಕ್",   nameHi = "ओक",     defaultPrice = 4000.0, materialKind = "wood"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Maple",   nameKn = "ಮೇಪಲ್", nameHi = "मेपल",   defaultPrice = 3500.0, materialKind = "wood"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Walnut",  nameKn = "ವಾಲ್‌ನಟ್", nameHi = "अखरोट", defaultPrice = 6000.0, materialKind = "wood"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Rosewood",nameKn = "ರೋಸ್‌ವುಡ್", nameHi = "शीशम", defaultPrice = 7000.0, materialKind = "wood"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = woodCatId, nameEn = "Plywood", nameKn = "ಪ್ಲೈವುಡ್", nameHi = "प्लाईवुड", defaultPrice = 1800.0, materialKind = "wood", isPlywood = true))
    }

    // Polish Type (no parameters; cost = total wood area × price)
    var polishCatId = dao.findRawCategoryByName("Polish Type")?.id
    if (polishCatId == null) {
        polishCatId = dao.insertRawMaterialCategory(RawMaterialCategory(nameEn = "Polish Type", nameKn = "ಪಾಲಿಷ್ ವಿಧ", nameHi = "पॉलिश प्रकार"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = polishCatId, nameEn = "Varnish",      nameKn = "ವಾರ್ನಿಷ್",  nameHi = "वार्निश",   defaultPrice = 120.0,  materialKind = "polish"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = polishCatId, nameEn = "Shellac",      nameKn = "ಶೆಲ್ಲಾಕ್",  nameHi = "शैलेक",    defaultPrice = 90.0,   materialKind = "polish"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = polishCatId, nameEn = "PU Polish",    nameKn = "PU ಪಾಲಿಷ್", nameHi = "PU पॉलिश", defaultPrice = 150.0,  materialKind = "polish"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = polishCatId, nameEn = "NC Polish",    nameKn = "NC ಪಾಲಿಷ್", nameHi = "NC पॉलिश", defaultPrice = 110.0,  materialKind = "polish"))
    }

    // Laminate Sheets (no parameters; cost = total wood area × price)
    var laminateCatId = dao.findRawCategoryByName("Laminate Sheets")?.id
    if (laminateCatId == null) {
        laminateCatId = dao.insertRawMaterialCategory(RawMaterialCategory(nameEn = "Laminate Sheets", nameKn = "ಲ್ಯಾಮಿನೇಟ್ ಶೀಟ್", nameHi = "लैमिनेट शीट"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = laminateCatId, nameEn = "White",  nameKn = "ಬಿಳಿ",  nameHi = "सफेद",  defaultPrice = 50.0, materialKind = "laminate"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = laminateCatId, nameEn = "Brown",  nameKn = "ಕಂದು",  nameHi = "भूरा",  defaultPrice = 50.0, materialKind = "laminate"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = laminateCatId, nameEn = "Beige",  nameKn = "ಬೇಜ್",  nameHi = "बेज",   defaultPrice = 55.0, materialKind = "laminate"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = laminateCatId, nameEn = "Skin",   nameKn = "ಚರ್ಮ",  nameHi = "स्किन", defaultPrice = 55.0, materialKind = "laminate"))
        dao.insertRawMaterialType(RawMaterialType(categoryId = laminateCatId, nameEn = "Walnut Grain", nameKn = "ವಾಲ್‌ನಟ್ ಗ್ರೇನ್", nameHi = "वॉलनट ग्रेन", defaultPrice = 65.0, materialKind = "laminate"))
    }

    // Glass Type (surface area + thickness)
    var glassCatId = dao.findRawCategoryByName("Glass Type")?.id
    if (glassCatId == null) {
        glassCatId = dao.insertRawMaterialCategory(RawMaterialCategory(nameEn = "Glass Type", nameKn = "ಗಾಜಿನ ವಿಧ", nameHi = "कांच प्रकार"))
        // Price per sq.ft for each thickness
        dao.insertRawMaterialType(RawMaterialType(categoryId = glassCatId, nameEn = "Clear (0.75 cm)", nameKn = "ಸ್ಪಷ್ಟ (0.75 cm)", nameHi = "साफ (0.75 cm)", defaultPrice = 800.0,  materialKind = "glass", glassThicknessCm = 0.75))
        dao.insertRawMaterialType(RawMaterialType(categoryId = glassCatId, nameEn = "Clear (1.0 cm)",  nameKn = "ಸ್ಪಷ್ಟ (1.0 cm)",  nameHi = "साफ (1.0 cm)",  defaultPrice = 1000.0, materialKind = "glass", glassThicknessCm = 1.0))
        dao.insertRawMaterialType(RawMaterialType(categoryId = glassCatId, nameEn = "Clear (1.5 cm)",  nameKn = "ಸ್ಪಷ್ಟ (1.5 cm)",  nameHi = "साफ (1.5 cm)",  defaultPrice = 1400.0, materialKind = "glass", glassThicknessCm = 1.5))
        dao.insertRawMaterialType(RawMaterialType(categoryId = glassCatId, nameEn = "Black (1.0 cm)",  nameKn = "ಕಪ್ಪು (1.0 cm)",  nameHi = "काला (1.0 cm)",  defaultPrice = 1200.0, materialKind = "glass", glassThicknessCm = 1.0))
        dao.insertRawMaterialType(RawMaterialType(categoryId = glassCatId, nameEn = "Mirror (4 mm)",   nameKn = "ಕನ್ನಡಿ (4 mm)",   nameHi = "दर्पण (4 mm)",  defaultPrice = 950.0,  materialKind = "glass", glassThicknessCm = 0.4))
    }

    // ── Preloaded Products ────────────────────────────────────────────────────
    // Sofa – glass OFF, plywood OFF
    seedSofa(dao)
    // Bed – glass OFF, plywood OFF
    seedBed(dao)
    // Table – glass OFF, plywood ON
    seedTable(dao)
    // Cabinet – glass ON, plywood ON
    seedCabinet(dao)
    // Wardrobe – glass ON, plywood ON
    seedWardrobe(dao)
}

// ── Product seed helpers ───────────────────────────────────────────────────────

/** Insert a product only if it doesn't already exist. Returns the id (existing or new). */
private suspend fun getOrCreateProduct(dao: KashtaKalaDao, product: Product): Long {
    val existing = dao.findProductByName(product.categoryId, product.nameEn)
    if (existing != null) return existing.id
    return dao.insertProduct(product)
}

private suspend fun seedSofa(dao: KashtaKalaDao) {
    // Classic Teak Sofa
    val id1 = getOrCreateProduct(dao, Product(
        categoryId = "sofa", nameEn = "Classic Teak Sofa",
        nameKn = "ಕ್ಲಾಸಿಕ್ ತೇಗ ಸೋಫಾ", nameHi = "क್ಲಾಸಿಕ್ ಸಾಗವಾನ ಸೋಫಾ",
        imageUris = "asset://product_images/sofa/classic_teak_sofa_1.jpg",
        useGlass = false, usePlywood = false
    ))
    if (dao.getWoodDimensionsForProduct(id1).isEmpty()) {
        // General: 6 ft length × 2.5 ft breadth × 3 ft height
        dao.insertDimension(ProductDimension(productId = id1, label = "Length",   value = 6.0,  unit = "ft",    dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id1, label = "Breadth",  value = 2.5,  unit = "ft",    dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id1, label = "Height",   value = 3.0,  unit = "ft",    dimensionKind = "general"))
        // Wood dims
        dao.insertDimension(ProductDimension(productId = id1, label = "Surface Area",    value = 12.0, unit = "sq.ft", dimensionKind = "wood_surface"))
        dao.insertDimension(ProductDimension(productId = id1, label = "Supporting Area", value = 5.0,  unit = "sq.ft", dimensionKind = "wood_supporting"))
        dao.insertDimension(ProductDimension(productId = id1, label = "Foot Area",       value = 1.5,  unit = "sq.ft", dimensionKind = "wood_foot"))
    }
    // Add default hardware: 4 bushes @ Rs.25
    if (dao.getHardwareOnce(id1).isEmpty()) {
        dao.insertHardware(ProductHardware(productId = id1, nameEn = "Bush", nameKn = "ಬುಶ್", nameHi = "बुश", quantity = 4, priceEach = 25.0))
    }

    // L-Shape Walnut Sofa
    val id2 = getOrCreateProduct(dao, Product(
        categoryId = "sofa", nameEn = "L-Shape Walnut Sofa",
        imageUris = "asset://product_images/sofa/L_Shape.png",
        nameKn = "L-ಆಕಾರದ ವಾಲ್‌ನಟ್ ಸೋಫಾ", nameHi = "L-शेप वॉलनट सोफा",
        useGlass = false, usePlywood = false
    ))
    if (dao.getWoodDimensionsForProduct(id2).isEmpty()) {
        dao.insertDimension(ProductDimension(productId = id2, label = "Length",   value = 8.0,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id2, label = "Breadth",  value = 3.0,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id2, label = "Height",   value = 3.0,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id2, label = "Surface Area",    value = 18.0, unit = "sq.ft", dimensionKind = "wood_surface"))
        dao.insertDimension(ProductDimension(productId = id2, label = "Supporting Area", value = 8.0,  unit = "sq.ft", dimensionKind = "wood_supporting"))
        dao.insertDimension(ProductDimension(productId = id2, label = "Foot Area",       value = 2.0,  unit = "sq.ft", dimensionKind = "wood_foot"))
    }
    if (dao.getHardwareOnce(id2).isEmpty()) {
        dao.insertHardware(ProductHardware(productId = id2, nameEn = "Bush", nameKn = "ಬುಶ್", nameHi = "बुश", quantity = 6, priceEach = 25.0))
    }

    // 3-Seater Oak Sofa
    val id3 = getOrCreateProduct(dao, Product(
        categoryId = "sofa", nameEn = "3-Seater Oak Sofa",
        imageUris = "asset://product_images/sofa/oak.jpg",
        nameKn = "3-ಸೀಟರ್ ಓಕ್ ಸೋಫಾ", nameHi = "3-सीटर ओक सोफा",
        useGlass = false, usePlywood = false
    ))
    if (dao.getWoodDimensionsForProduct(id3).isEmpty()) {
        dao.insertDimension(ProductDimension(productId = id3, label = "Length",   value = 7.0,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id3, label = "Breadth",  value = 2.5,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id3, label = "Height",   value = 3.5,  unit = "ft", dimensionKind = "general"))
        dao.insertDimension(ProductDimension(productId = id3, label = "Surface Area",    value = 15.0, unit = "sq.ft", dimensionKind = "wood_surface"))
        dao.insertDimension(ProductDimension(productId = id3, label = "Supporting Area", value = 6.0,  unit = "sq.ft", dimensionKind = "wood_supporting"))
        dao.insertDimension(ProductDimension(productId = id3, label = "Foot Area",       value = 1.5,  unit = "sq.ft", dimensionKind = "wood_foot"))
    }
    if (dao.getHardwareOnce(id3).isEmpty()) {
        dao.insertHardware(ProductHardware(productId = id3, nameEn = "Bush", nameKn = "ಬುಶ್", nameHi = "बुश", quantity = 4, priceEach = 25.0))
    }
}

private suspend fun seedBed(dao: KashtaKalaDao) {
    val beds = listOf(
        Triple("King Teak Bed",   "king",  Triple(7.0, 6.0, 4.0)),  // L×B×H in ft
        Triple("Queen Oak Bed",   "queen", Triple(6.5, 5.5, 3.5)),
        Triple("Single Maple Bed","single",Triple(6.5, 3.5, 3.5))
    )
    beds.forEach { (name, sub, dims) ->
        val (l, b, h) = dims
        val surfaceArea    = l * b * 0.30   // approx
        val supportingArea = l * b * 0.15
        val footArea       = 1.0
        val id = getOrCreateProduct(dao, Product(
            categoryId = "bed", subCategoryId = sub,
            nameEn = name, nameKn = name, nameHi = name,
            useGlass = false, usePlywood = false
        ))
        if (dao.getWoodDimensionsForProduct(id).isEmpty()) {
            dao.insertDimension(ProductDimension(productId = id, label = "Length",  value = l, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Breadth", value = b, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Height",  value = h, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Surface Area",    value = surfaceArea,    unit = "sq.ft", dimensionKind = "wood_surface"))
            dao.insertDimension(ProductDimension(productId = id, label = "Supporting Area", value = supportingArea, unit = "sq.ft", dimensionKind = "wood_supporting"))
            dao.insertDimension(ProductDimension(productId = id, label = "Foot Area",       value = footArea,       unit = "sq.ft", dimensionKind = "wood_foot"))
        }
        if (dao.getHardwareOnce(id).isEmpty()) {
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Bush", nameKn = "ಬುಶ್", nameHi = "बुश", quantity = 4, priceEach = 25.0))
        }
    }
}

private suspend fun seedTable(dao: KashtaKalaDao) {
    data class TableSpec(val name: String, val sub: String, val l: Double, val b: Double, val h: Double)
    val tables = listOf(
        TableSpec("Walnut Dining Table", "dining",        4.0, 2.5, 2.5),
        TableSpec("Maple Office Desk",   "office",        4.5, 2.0, 2.5),
        TableSpec("Study Table",         "study",         3.0, 1.5, 2.5),
        TableSpec("Centre Table",        "regular_table", 3.0, 1.5, 1.5)
    )
    tables.forEach { t ->
        val id = getOrCreateProduct(dao, Product(
            categoryId = "table", subCategoryId = t.sub,
            nameEn = t.name, nameKn = t.name, nameHi = t.name,
            useGlass = false, usePlywood = true
        ))
        if (dao.getWoodDimensionsForProduct(id).isEmpty()) {
            dao.insertDimension(ProductDimension(productId = id, label = "Length",  value = t.l, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Breadth", value = t.b, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Height",  value = t.h, unit = "ft", dimensionKind = "general"))
            val surfaceArea    = t.l * t.b
            val supportingArea = t.l * t.b * 0.4
            val footArea       = 0.5
            dao.insertDimension(ProductDimension(productId = id, label = "Surface Area",    value = surfaceArea,    unit = "sq.ft", dimensionKind = "wood_surface"))
            dao.insertDimension(ProductDimension(productId = id, label = "Supporting Area", value = supportingArea, unit = "sq.ft", dimensionKind = "wood_supporting"))
            dao.insertDimension(ProductDimension(productId = id, label = "Foot Area",       value = footArea,       unit = "sq.ft", dimensionKind = "wood_foot"))
        }
        if (dao.getHardwareOnce(id).isEmpty()) {
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Bush", nameKn = "ಬುಶ್", nameHi = "बुश", quantity = 4, priceEach = 25.0))
        }
    }
}

private suspend fun seedCabinet(dao: KashtaKalaDao) {
    data class CabSpec(val name: String, val l: Double, val b: Double, val h: Double, val glass: Boolean)
    val cabinets = listOf(
        CabSpec("Rosewood Display Cabinet", 3.0, 1.5, 5.0, true),
        CabSpec("Kitchen Cabinet",          4.0, 2.0, 6.0, false),
        CabSpec("Bathroom Cabinet",         2.0, 1.0, 3.0, true)
    )
    cabinets.forEach { c ->
        val id = getOrCreateProduct(dao, Product(
            categoryId = "cabinet",
            nameEn = c.name, nameKn = c.name, nameHi = c.name,
            useGlass = c.glass, usePlywood = true
        ))
        if (dao.getWoodDimensionsForProduct(id).isEmpty()) {
            dao.insertDimension(ProductDimension(productId = id, label = "Length",  value = c.l, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Breadth", value = c.b, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Height",  value = c.h, unit = "ft", dimensionKind = "general"))
            val body    = c.l * c.h * 0.5
            val support = c.l * c.h * 0.2
            val foot    = 0.5
            dao.insertDimension(ProductDimension(productId = id, label = "Surface Area",    value = body,    unit = "sq.ft", dimensionKind = "wood_surface"))
            dao.insertDimension(ProductDimension(productId = id, label = "Supporting Area", value = support, unit = "sq.ft", dimensionKind = "wood_supporting"))
            dao.insertDimension(ProductDimension(productId = id, label = "Foot Area",       value = foot,    unit = "sq.ft", dimensionKind = "wood_foot"))
            if (c.glass) {
                dao.insertDimension(ProductDimension(productId = id, label = "Glass Door Area", value = c.l * c.h * 0.3, unit = "sq.ft", dimensionKind = "glass_area"))
            }
        }
        if (dao.getHardwareOnce(id).isEmpty()) {
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Hinge", nameKn = "ಹಿಂಜ್", nameHi = "हिंज", quantity = 4, priceEach = 40.0))
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Bush",  nameKn = "ಬುಶ್",  nameHi = "बुश",  quantity = 4, priceEach = 25.0))
        }
    }
}

private suspend fun seedWardrobe(dao: KashtaKalaDao) {
    data class WSpec(val name: String, val l: Double, val b: Double, val h: Double)
    val wardrobes = listOf(
        WSpec("2-Door Teak Wardrobe",    4.0, 2.0, 7.0),
        WSpec("3-Door Rosewood Wardrobe",6.0, 2.0, 7.0),
        WSpec("Walk-in Wardrobe",        8.0, 2.0, 7.0)
    )
    wardrobes.forEach { w ->
        val id = getOrCreateProduct(dao, Product(
            categoryId = "wardrobe",
            nameEn = w.name, nameKn = w.name, nameHi = w.name,
            useGlass = true, usePlywood = true
        ))
        if (dao.getWoodDimensionsForProduct(id).isEmpty()) {
            dao.insertDimension(ProductDimension(productId = id, label = "Length",  value = w.l, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Breadth", value = w.b, unit = "ft", dimensionKind = "general"))
            dao.insertDimension(ProductDimension(productId = id, label = "Height",  value = w.h, unit = "ft", dimensionKind = "general"))
            val body    = w.l * w.h * 0.5
            val support = w.l * w.h * 0.2
            val foot    = 0.5
            dao.insertDimension(ProductDimension(productId = id, label = "Surface Area",    value = body,    unit = "sq.ft", dimensionKind = "wood_surface"))
            dao.insertDimension(ProductDimension(productId = id, label = "Supporting Area", value = support, unit = "sq.ft", dimensionKind = "wood_supporting"))
            dao.insertDimension(ProductDimension(productId = id, label = "Foot Area",       value = foot,    unit = "sq.ft", dimensionKind = "wood_foot"))
            // Mirror panel
            dao.insertDimension(ProductDimension(productId = id, label = "Mirror Area", value = w.l * w.h * 0.25, unit = "sq.ft", dimensionKind = "glass_area"))
        }
        if (dao.getHardwareOnce(id).isEmpty()) {
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Hinge",  nameKn = "ಹಿಂಜ್",  nameHi = "हिंज",  quantity = 6, priceEach = 40.0))
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Handle", nameKn = "ಹ್ಯಾಂಡಲ್", nameHi = "हैंडल", quantity = 3, priceEach = 60.0))
            dao.insertHardware(ProductHardware(productId = id, nameEn = "Bush",   nameKn = "ಬುಶ್",   nameHi = "बुश",   quantity = 4, priceEach = 25.0))
        }
    }
}

// ── Extension helpers ──────────────────────────────────────────────────────────

fun Category.localName(lang: String)            = when (lang) { "kn" -> nameKn; "hi" -> nameHi; else -> nameEn }
fun RawMaterialCategory.localName(lang: String) = when (lang) { "kn" -> nameKn; "hi" -> nameHi; else -> nameEn }
fun RawMaterialType.localName(lang: String)     = when (lang) { "kn" -> nameKn; "hi" -> nameHi; else -> nameEn }
fun Product.localName(lang: String)             = when (lang) { "kn" -> nameKn; "hi" -> nameHi; else -> nameEn }

fun resolveImageData(context: Context, uriString: String): Any {
    val normalized = when {
        uriString.startsWith("asset://") ->
            "file:///android_asset/${uriString.removePrefix("asset://")}"
        else -> uriString
    }
    return Uri.parse(normalized)
}

fun woodKindLabel(kind: String) = when (kind) {
    "wood_surface"    -> "Surface Area"
    "wood_supporting" -> "Supporting Area"
    "wood_foot"       -> "Foot Area"
    "glass_area"      -> "Glass Area"
    else              -> "General"
}
