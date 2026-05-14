package com.example.kashtakala;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u001a<\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001aJ\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a4\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001a\u009c\u0001\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00032\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001d\u001a\u00020\u00032\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001f\u001a\u00020\u00032\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010!\u001a\u00020\u00032\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00172\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a.\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001ab\u0010&\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020-0*2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020/0*2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0003\u001a\b\u00104\u001a\u00020\u0001H\u0007\u001a\u001e\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u00032\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\u001c\u00108\u001a\u00020\u00012\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001aJ\u0010:\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u001e\u0010=\u001a\u00020\u00012\u0006\u0010>\u001a\u00020?2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a&\u0010@\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001aX\u0010A\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u00010\u00172\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0006\u00100\u001a\u000201H\u0003\u001a&\u0010E\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001aF\u0010F\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010G\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0003\u001a\u0016\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020\u0003\u001a\u000e\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020\u0003\u001a\u001e\u0010O\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0082@\u00a2\u0006\u0002\u0010P\u001a\u0016\u0010Q\u001a\u00020R2\u0006\u0010J\u001a\u00020I2\u0006\u0010S\u001a\u00020\u0003\u001a\u0016\u0010T\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010U\u001a\u0016\u0010V\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010U\u001a\u0016\u0010W\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010U\u001a\u0016\u0010X\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010U\u001a\u0016\u0010Y\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010U\u001a\u0016\u0010Z\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010U\u001a\u000e\u0010[\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0003\u001a\u0012\u0010\\\u001a\u00020\u0003*\u00020]2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\\\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\\\u001a\u00020\u0003*\u00020(2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\\\u001a\u00020\u0003*\u00020+2\u0006\u0010\u0002\u001a\u00020\u0003\u00a8\u0006^"}, d2 = {"CheckoutScreen", "", "lang", "", "productId", "", "dao", "Lcom/example/kashtakala/KashtaKalaDao;", "onBack", "Lkotlin/Function0;", "onConfirmed", "DashboardProductRow", "product", "Lcom/example/kashtakala/Product;", "editMode", "", "onTap", "onFav", "onDelete", "DashboardScreen", "screen", "Lcom/example/kashtakala/Screen$Dashboard;", "onNavigate", "Lkotlin/Function1;", "Lcom/example/kashtakala/Screen;", "DimensionDialog", "title", "label", "onLabelChange", "value", "onValueChange", "unit", "onUnitChange", "kind", "onKindChange", "onConfirm", "onDismiss", "EditProductScreen", "EditableMaterialDimSection", "cat", "Lcom/example/kashtakala/RawMaterialCategory;", "selectedMats", "", "Lcom/example/kashtakala/RawMaterialType;", "dimensions", "Lcom/example/kashtakala/ProductDimension;", "matDimLinks", "Lcom/example/kashtakala/ProductMaterialDimension;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "totalWoodArea", "", "KashtaKalaApp", "LangBtn", "text", "onClick", "LanguageSelectionScreen", "onLanguageSelected", "ProductDetailScreen", "onEdit", "onCheckout", "QuoteCard", "quote", "Lcom/example/kashtakala/Quote;", "QuoteHistoryScreen", "SettingsCategoryCard", "onAddType", "onEditPrice", "onDeleteCat", "SettingsScreen", "ViewOnlyRawMaterialSection", "selectedMaterials", "applyLocale", "Landroid/content/Context;", "context", "langCode", "assetUri", "Landroid/net/Uri;", "path", "getOrCreateProduct", "(Lcom/example/kashtakala/KashtaKalaDao;Lcom/example/kashtakala/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveImageData", "", "uriString", "seedBed", "(Lcom/example/kashtakala/KashtaKalaDao;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedCabinet", "seedInitialData", "seedSofa", "seedTable", "seedWardrobe", "woodKindLabel", "localName", "Lcom/example/kashtakala/Category;", "app_debug"})
public final class MainActivityKt {
    
    /**
     * Apply the stored language to the app context so system strings resolve correctly.
     */
    @org.jetbrains.annotations.NotNull()
    public static final android.content.Context applyLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String langCode) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final android.net.Uri assetUri(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void KashtaKalaApp() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LanguageSelectionScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLanguageSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LangBtn(java.lang.String text, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.Screen.Dashboard screen, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.kashtakala.Screen, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DashboardProductRow(com.example.kashtakala.Product product, java.lang.String lang, boolean editMode, kotlin.jvm.functions.Function0<kotlin.Unit> onTap, kotlin.jvm.functions.Function0<kotlin.Unit> onFav, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void ProductDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, long productId, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCheckout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ViewOnlyRawMaterialSection(java.lang.String lang, com.example.kashtakala.RawMaterialCategory cat, com.example.kashtakala.KashtaKalaDao dao, long productId, java.util.List<com.example.kashtakala.RawMaterialType> selectedMaterials, kotlinx.coroutines.CoroutineScope scope, double totalWoodArea) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void EditProductScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, long productId, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void EditableMaterialDimSection(java.lang.String lang, com.example.kashtakala.RawMaterialCategory cat, com.example.kashtakala.KashtaKalaDao dao, long productId, java.util.List<com.example.kashtakala.RawMaterialType> selectedMats, java.util.List<com.example.kashtakala.ProductDimension> dimensions, java.util.List<com.example.kashtakala.ProductMaterialDimension> matDimLinks, kotlinx.coroutines.CoroutineScope scope, double totalWoodArea) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DimensionDialog(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLabelChange, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    java.lang.String unit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onUnitChange, @org.jetbrains.annotations.NotNull()
    java.lang.String kind, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onKindChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CheckoutScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, long productId, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirmed) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void QuoteHistoryScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuoteCard(com.example.kashtakala.Quote quote, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsCategoryCard(com.example.kashtakala.RawMaterialCategory cat, java.lang.String lang, com.example.kashtakala.KashtaKalaDao dao, kotlin.jvm.functions.Function0<kotlin.Unit> onAddType, kotlin.jvm.functions.Function1<? super com.example.kashtakala.RawMaterialType, kotlin.Unit> onEditPrice, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteCat, kotlinx.coroutines.CoroutineScope scope) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.Object seedInitialData(@org.jetbrains.annotations.NotNull()
    com.example.kashtakala.KashtaKalaDao dao, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Insert a product only if it doesn't already exist. Returns the id (existing or new).
     */
    private static final java.lang.Object getOrCreateProduct(com.example.kashtakala.KashtaKalaDao dao, com.example.kashtakala.Product product, kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    private static final java.lang.Object seedSofa(com.example.kashtakala.KashtaKalaDao dao, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private static final java.lang.Object seedBed(com.example.kashtakala.KashtaKalaDao dao, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private static final java.lang.Object seedTable(com.example.kashtakala.KashtaKalaDao dao, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private static final java.lang.Object seedCabinet(com.example.kashtakala.KashtaKalaDao dao, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private static final java.lang.Object seedWardrobe(com.example.kashtakala.KashtaKalaDao dao, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String localName(@org.jetbrains.annotations.NotNull()
    com.example.kashtakala.Category $this$localName, @org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String localName(@org.jetbrains.annotations.NotNull()
    com.example.kashtakala.RawMaterialCategory $this$localName, @org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String localName(@org.jetbrains.annotations.NotNull()
    com.example.kashtakala.RawMaterialType $this$localName, @org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String localName(@org.jetbrains.annotations.NotNull()
    com.example.kashtakala.Product $this$localName, @org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.Object resolveImageData(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String uriString) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String woodKindLabel(@org.jetbrains.annotations.NotNull()
    java.lang.String kind) {
        return null;
    }
}