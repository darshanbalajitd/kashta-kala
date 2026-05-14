package com.example.kashtakala;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\bJ.\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/example/kashtakala/ProductMaterialDimension;", "", "productId", "", "materialTypeId", "dimensionId", "(JJLjava/lang/Long;)V", "getDimensionId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getMaterialTypeId", "()J", "getProductId", "component1", "component2", "component3", "copy", "(JJLjava/lang/Long;)Lcom/example/kashtakala/ProductMaterialDimension;", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
@androidx.room.Entity(tableName = "product_material_dimensions", primaryKeys = {"productId", "materialTypeId"}, foreignKeys = {@androidx.room.ForeignKey(entity = com.example.kashtakala.ProductMaterial.class, parentColumns = {"productId", "materialTypeId"}, childColumns = {"productId", "materialTypeId"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.example.kashtakala.ProductDimension.class, parentColumns = {"id"}, childColumns = {"dimensionId"}, onDelete = 3)}, indices = {@androidx.room.Index(value = {"productId"}), @androidx.room.Index(value = {"materialTypeId"}), @androidx.room.Index(value = {"dimensionId"})})
public final class ProductMaterialDimension {
    private final long productId = 0L;
    private final long materialTypeId = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long dimensionId = null;
    
    public ProductMaterialDimension(long productId, long materialTypeId, @org.jetbrains.annotations.Nullable()
    java.lang.Long dimensionId) {
        super();
    }
    
    public final long getProductId() {
        return 0L;
    }
    
    public final long getMaterialTypeId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDimensionId() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.kashtakala.ProductMaterialDimension copy(long productId, long materialTypeId, @org.jetbrains.annotations.Nullable()
    java.lang.Long dimensionId) {
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