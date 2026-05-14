package com.example.kashtakala;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/kashtakala/KashtaKalaDatabase;", "Landroidx/room/RoomDatabase;", "()V", "dao", "Lcom/example/kashtakala/KashtaKalaDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.example.kashtakala.AppSettings.class, com.example.kashtakala.Category.class, com.example.kashtakala.Product.class, com.example.kashtakala.RawMaterialCategory.class, com.example.kashtakala.RawMaterialType.class, com.example.kashtakala.ProductMaterial.class, com.example.kashtakala.ProductDimension.class, com.example.kashtakala.ProductMaterialDimension.class, com.example.kashtakala.ProductHardware.class, com.example.kashtakala.Quote.class}, version = 5, exportSchema = false)
public abstract class KashtaKalaDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.example.kashtakala.KashtaKalaDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.kashtakala.KashtaKalaDatabase.Companion Companion = null;
    
    public KashtaKalaDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.kashtakala.KashtaKalaDao dao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/kashtakala/KashtaKalaDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/example/kashtakala/KashtaKalaDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.kashtakala.KashtaKalaDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}