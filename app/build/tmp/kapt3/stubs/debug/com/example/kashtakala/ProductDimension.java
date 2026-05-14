package com.example.kashtakala;

/**
 * dimensionKind: "general" | "wood_surface" | "wood_supporting" | "wood_foot"
 *  - general: length/breadth/height (shown as-is)
 *  - wood_surface: surface area of wood
 *  - wood_supporting: supporting area
 *  - wood_foot: foot area
 * For preloaded products, all wood dimensions are pre-filled.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B=\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/example/kashtakala/ProductDimension;", "", "id", "", "productId", "label", "", "value", "", "unit", "dimensionKind", "(JJLjava/lang/String;DLjava/lang/String;Ljava/lang/String;)V", "getDimensionKind", "()Ljava/lang/String;", "getId", "()J", "getLabel", "getProductId", "getUnit", "getValue", "()D", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "product_dimensions", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.kashtakala.Product.class, parentColumns = {"id"}, childColumns = {"productId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"productId"})})
public final class ProductDimension {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long productId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    private final double value = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String unit = null;
    
    /**
     * "general" | "wood_surface" | "wood_supporting" | "wood_foot" | "glass_area"
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dimensionKind = null;
    
    public ProductDimension(long id, long productId, @org.jetbrains.annotations.NotNull()
    java.lang.String label, double value, @org.jetbrains.annotations.NotNull()
    java.lang.String unit, @org.jetbrains.annotations.NotNull()
    java.lang.String dimensionKind) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getProductId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    public final double getValue() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUnit() {
        return null;
    }
    
    /**
     * "general" | "wood_surface" | "wood_supporting" | "wood_foot" | "glass_area"
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDimensionKind() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.kashtakala.ProductDimension copy(long id, long productId, @org.jetbrains.annotations.NotNull()
    java.lang.String label, double value, @org.jetbrains.annotations.NotNull()
    java.lang.String unit, @org.jetbrains.annotations.NotNull()
    java.lang.String dimensionKind) {
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