package com.example.kashtakala;

/**
 * Bush/hardware line item (e.g. 4 bushes @ Rs.25 each)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\t\u0010!\u001a\u00020\fH\u00c6\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\nH\u00d6\u0001J\t\u0010\'\u001a\u00020\u0006H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/example/kashtakala/ProductHardware;", "", "id", "", "productId", "nameEn", "", "nameKn", "nameHi", "quantity", "", "priceEach", "", "(JJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ID)V", "getId", "()J", "lineCost", "getLineCost", "()D", "getNameEn", "()Ljava/lang/String;", "getNameHi", "getNameKn", "getPriceEach", "getProductId", "getQuantity", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "product_hardware", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.kashtakala.Product.class, parentColumns = {"id"}, childColumns = {"productId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"productId"})})
public final class ProductHardware {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long productId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameEn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameKn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameHi = null;
    private final int quantity = 0;
    private final double priceEach = 0.0;
    
    public ProductHardware(long id, long productId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameKn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHi, int quantity, double priceEach) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getProductId() {
        return 0L;
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
    
    public final int getQuantity() {
        return 0;
    }
    
    public final double getPriceEach() {
        return 0.0;
    }
    
    public final double getLineCost() {
        return 0.0;
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.kashtakala.ProductHardware copy(long id, long productId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameEn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameKn, @org.jetbrains.annotations.NotNull()
    java.lang.String nameHi, int quantity, double priceEach) {
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