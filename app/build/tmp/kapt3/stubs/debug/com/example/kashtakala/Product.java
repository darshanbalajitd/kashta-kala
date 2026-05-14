package com.example.kashtakala;

/**
 * useGlass: whether glass is toggled on for this product (default false for Sofa/Bed)
 * usePlywood: whether plywood is selected (default true for Table/Cabinet/Wardrobe)
 * totalWoodArea: cached sum of wood dimensions (surface+supporting+foot) used for polish/laminate calc
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001By\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\fH\u00c6\u0003J\t\u0010!\u001a\u00020\fH\u00c6\u0003J\t\u0010\"\u001a\u00020\fH\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\fH\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u0085\u0001\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\fH\u00c6\u0001J\u0013\u0010,\u001a\u00020\f2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020/H\u00d6\u0001J\t\u00100\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\u000f\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\u0010\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017\u00a8\u00061"}, d2 = {"Lcom/example/kashtakala/Product;", "", "id", "", "categoryId", "", "subCategoryId", "nameEn", "nameKn", "nameHi", "imageUris", "isFavorite", "", "lastModified", "isCustom", "useGlass", "usePlywood", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZJZZZ)V", "getCategoryId", "()Ljava/lang/String;", "getId", "()J", "getImageUris", "()Z", "getLastModified", "getNameEn", "getNameHi", "getNameKn", "getSubCategoryId", "getUseGlass", "getUsePlywood", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "products", indices = {@androidx.room.Index(value = {"categoryId", "nameEn"}, unique = true)})
public final class Product {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String categoryId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String subCategoryId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameEn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameKn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameHi = null;
    
    /**
     * Pipe-separated content-URI strings
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String imageUris = null;
    private final boolean isFavorite = false;
    private final long lastModified = 0L;
    private final boolean isCustom = false;
    
    /**
     * Whether glass is enabled for this product
     */
    private final boolean useGlass = false;
    
    /**
     * Whether plywood is selected (affects polish/laminate visibility)
     */
    private final boolean usePlywood = false;
    
    public Product(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.String subCategoryId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameKn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHi, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUris, boolean isFavorite, long lastModified, boolean isCustom, boolean useGlass, boolean usePlywood) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSubCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameEn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameKn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameHi() {
        return null;
    }
    
    /**
     * Pipe-separated content-URI strings
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImageUris() {
        return null;
    }
    
    public final boolean isFavorite() {
        return false;
    }
    
    public final long getLastModified() {
        return 0L;
    }
    
    public final boolean isCustom() {
        return false;
    }
    
    /**
     * Whether glass is enabled for this product
     */
    public final boolean getUseGlass() {
        return false;
    }
    
    /**
     * Whether plywood is selected (affects polish/laminate visibility)
     */
    public final boolean getUsePlywood() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.kashtakala.Product copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String categoryId, @org.jetbrains.annotations.Nullable()
    java.lang.String subCategoryId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameKn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHi, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUris, boolean isFavorite, long lastModified, boolean isCustom, boolean useGlass, boolean usePlywood) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}