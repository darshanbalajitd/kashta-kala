package com.example.kashtakala

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

// ═══════════════════════════════════════════════════════════
//  Entities
// ═══════════════════════════════════════════════════════════

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey val id: Int = 1,
    val languageCode: String? = null,
    val isFirstLaunch: Boolean = true
)

@Entity(
    tableName = "categories",
    indices = [Index(value = ["id"], unique = true)]
)
data class Category(
    @PrimaryKey val id: String,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String,
    val parentId: String? = null,
    val imageRes: String? = null
)

@Entity(tableName = "raw_material_categories")
data class RawMaterialCategory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String
)

/**
 * materialKind: "wood" | "polish" | "laminate" | "glass" | "hardware" | "other"
 * For wood: surface area, supporting area, foot area are used.
 * For polish/laminate: cost = total wood area × price (no separate dimension).
 * For glass: surface area + thickness price.
 * pricePerUnit: Rs. per sq.ft (or per piece for hardware).
 * isPlywood: true only when this wood type is plywood.
 * glassThicknessCm: 0.75 | 1.0 | 1.5 for glass types.
 */
@Entity(
    tableName = "raw_material_types",
    indices = [Index(value = ["categoryId", "nameEn"], unique = true)]
)
data class RawMaterialType(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String,
    /** Price per sq.ft (or per piece for hardware like bushes) */
    val defaultPrice: Double,
    /** "wood" | "polish" | "laminate" | "glass" | "hardware" | "other" */
    val materialKind: String = "other",
    val isPlywood: Boolean = false,
    /** Thickness in cm for glass types (0.75, 1.0, 1.5). 0 = not glass. */
    val glassThicknessCm: Double = 0.0
)

/**
 * useGlass: whether glass is toggled on for this product (default false for Sofa/Bed)
 * usePlywood: whether plywood is selected (default true for Table/Cabinet/Wardrobe)
 * totalWoodArea: cached sum of wood dimensions (surface+supporting+foot) used for polish/laminate calc
 */
@Entity(
    tableName = "products",
    indices = [Index(value = ["categoryId", "nameEn"], unique = true)]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: String,
    val subCategoryId: String? = null,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String,
    /** Pipe-separated content-URI strings */
    val imageUris: String? = null,
    val isFavorite: Boolean = false,
    val lastModified: Long = System.currentTimeMillis(),
    val isCustom: Boolean = false,
    /** Whether glass is enabled for this product */
    val useGlass: Boolean = false,
    /** Whether plywood is selected (affects polish/laminate visibility) */
    val usePlywood: Boolean = false
)

/**
 * dimensionKind: "general" | "wood_surface" | "wood_supporting" | "wood_foot"
 *   - general: length/breadth/height (shown as-is)
 *   - wood_surface: surface area of wood
 *   - wood_supporting: supporting area
 *   - wood_foot: foot area
 * For preloaded products, all wood dimensions are pre-filled.
 */
@Entity(
    tableName = "product_dimensions",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("productId")]
)
data class ProductDimension(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val label: String,
    val value: Double = 0.0,
    val unit: String = "sq.ft",
    /** "general" | "wood_surface" | "wood_supporting" | "wood_foot" | "glass_area" */
    val dimensionKind: String = "general"
)

@Entity(
    tableName = "product_materials",
    primaryKeys = ["productId", "materialTypeId"]
)
data class ProductMaterial(
    val productId: Long,
    val materialTypeId: Long
)

@Entity(
    tableName = "product_material_dimensions",
    primaryKeys = ["productId", "materialTypeId"],
    foreignKeys = [
        ForeignKey(
            entity = ProductMaterial::class,
            parentColumns = ["productId", "materialTypeId"],
            childColumns  = ["productId", "materialTypeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductDimension::class,
            parentColumns = ["id"],
            childColumns  = ["dimensionId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("productId"), Index("materialTypeId"), Index("dimensionId")]
)
data class ProductMaterialDimension(
    val productId: Long,
    val materialTypeId: Long,
    val dimensionId: Long?
)

/** Bush/hardware line item (e.g. 4 bushes @ Rs.25 each) */
@Entity(
    tableName = "product_hardware",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("productId")]
)
data class ProductHardware(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String,
    val quantity: Int,
    val priceEach: Double
) {
    val lineCost: Double get() = quantity * priceEach
}

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val productNameEn: String,
    val buyerName: String,
    val deliveryDate: String,
    val materialTotal: Double,
    val labourAdjustmentType: String,
    val labourAdjustmentValue: Double,
    val grandTotal: Double,
    val snapshotJson: String,
    val createdAt: Long = System.currentTimeMillis()
)

// ═══════════════════════════════════════════════════════════
//  Result projections (not Room entities)
// ═══════════════════════════════════════════════════════════

data class MaterialWithDimension(
    val materialTypeId: Long,
    val materialNameEn: String,
    val materialNameKn: String,
    val materialNameHi: String,
    val pricePerUnit: Double,
    val materialKind: String,
    val isPlywood: Boolean,
    val dimensionId: Long?,
    val dimensionLabel: String?,
    val dimensionValue: Double?,
    val dimensionUnit: String?,
    val dimensionKind: String?
) {
    fun materialName(lang: String) = when (lang) {
        "kn" -> materialNameKn; "hi" -> materialNameHi; else -> materialNameEn
    }
    val lineCost: Double get() = pricePerUnit * (dimensionValue ?: 0.0)
}

// ═══════════════════════════════════════════════════════════
//  DAO
// ═══════════════════════════════════════════════════════════

@Dao
interface KashtaKalaDao {

    // ── Settings ──────────────────────────────────────────
    @Query("SELECT * FROM app_settings WHERE id = 1")
    fun getSettings(): Flow<AppSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: AppSettings)

    // ── Categories ────────────────────────────────────────
    @Query("SELECT * FROM categories WHERE parentId IS NULL ORDER BY id ASC")
    fun getRootCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE parentId = :parentId ORDER BY id ASC")
    fun getSubCategories(parentId: String): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): Category?

    // ── Products ──────────────────────────────────────────
    @Query("""
        SELECT * FROM products
        WHERE categoryId = :catId OR subCategoryId = :catId
        ORDER BY isFavorite DESC, lastModified DESC
    """)
    fun getProductsByCategory(catId: String): Flow<List<Product>>

    @Query("""
        SELECT * FROM products
        WHERE (categoryId = :catId OR subCategoryId = :catId) AND isCustom = 1
        ORDER BY isFavorite DESC, lastModified DESC
    """)
    fun getCustomProductsByCategory(catId: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Long): Flow<Product?>

    /** Returns existing product with same name in same category, or null */
    @Query("SELECT * FROM products WHERE categoryId = :catId AND nameEn = :nameEn LIMIT 1")
    suspend fun findProductByName(catId: String, nameEn: String): Product?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    // ── Raw Material Categories ───────────────────────────
    @Query("SELECT * FROM raw_material_categories ORDER BY id ASC")
    fun getRawMaterialCategories(): Flow<List<RawMaterialCategory>>

    @Query("SELECT * FROM raw_material_categories WHERE nameEn = :nameEn LIMIT 1")
    suspend fun findRawCategoryByName(nameEn: String): RawMaterialCategory?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRawMaterialCategory(cat: RawMaterialCategory): Long

    @Update
    suspend fun updateRawMaterialCategory(cat: RawMaterialCategory)

    @Delete
    suspend fun deleteRawMaterialCategory(cat: RawMaterialCategory)

    // ── Raw Material Types ────────────────────────────────
    @Query("SELECT * FROM raw_material_types WHERE categoryId = :catId ORDER BY id ASC")
    fun getRawMaterialTypes(catId: Long): Flow<List<RawMaterialType>>

    @Query("SELECT * FROM raw_material_types ORDER BY id ASC")
    suspend fun getAllRawMaterialTypes(): List<RawMaterialType>

    @Query("SELECT * FROM raw_material_types WHERE id = :id")
    suspend fun getRawMaterialTypeById(id: Long): RawMaterialType?

    @Query("SELECT * FROM raw_material_types WHERE categoryId = :catId AND nameEn = :nameEn LIMIT 1")
    suspend fun findRawTypeByName(catId: Long, nameEn: String): RawMaterialType?

    @Query("SELECT * FROM raw_material_types WHERE categoryId = :catId ORDER BY id ASC LIMIT 1")
    suspend fun getFirstTypeInCategory(catId: Long): RawMaterialType?

    @Query("SELECT * FROM raw_material_types WHERE categoryId = :catId AND isPlywood = 1 LIMIT 1")
    suspend fun getPlywoodTypeInCategory(catId: Long): RawMaterialType?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRawMaterialType(type: RawMaterialType): Long

    @Update
    suspend fun updateRawMaterialType(type: RawMaterialType)

    @Delete
    suspend fun deleteRawMaterialType(type: RawMaterialType)

    // ── Product ↔ Materials ─────────────────────────────────
    @Query("""
        SELECT t.* FROM raw_material_types t
        INNER JOIN product_materials pm ON t.id = pm.materialTypeId
        WHERE pm.productId = :productId
    """)
    fun getMaterialsForProduct(productId: Long): Flow<List<RawMaterialType>>

    @Query("""
        SELECT t.* FROM raw_material_types t
        INNER JOIN product_materials pm ON t.id = pm.materialTypeId
        WHERE pm.productId = :productId AND t.categoryId = :catId
        LIMIT 1
    """)
    suspend fun getSelectedTypeForCategory(productId: Long, catId: Long): RawMaterialType?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductMaterial(pm: ProductMaterial)

    @Transaction
    suspend fun selectMaterialInCategory(productId: Long, catId: Long, newTypeId: Long) {
        val existing = getSelectedTypeForCategory(productId, catId)
        if (existing != null) {
            deleteMaterialDimension(productId, existing.id)
            deleteProductMaterial(ProductMaterial(productId, existing.id))
        }
        insertProductMaterial(ProductMaterial(productId, newTypeId))
    }

    @Delete
    suspend fun deleteProductMaterial(pm: ProductMaterial)

    @Query("DELETE FROM product_materials WHERE productId = :productId")
    suspend fun clearProductMaterials(productId: Long)

    // ── Product Dimensions ────────────────────────────────
    @Query("SELECT * FROM product_dimensions WHERE productId = :productId ORDER BY id ASC")
    fun getDimensionsForProduct(productId: Long): Flow<List<ProductDimension>>

    @Query("SELECT * FROM product_dimensions WHERE productId = :productId AND dimensionKind IN ('wood_surface','wood_supporting','wood_foot')")
    suspend fun getWoodDimensionsForProduct(productId: Long): List<ProductDimension>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDimension(dim: ProductDimension): Long

    @Update
    suspend fun updateDimension(dim: ProductDimension)

    @Delete
    suspend fun deleteDimension(dim: ProductDimension)

    @Query("DELETE FROM product_dimensions WHERE productId = :productId")
    suspend fun clearDimensionsForProduct(productId: Long)

    // ── Material ↔ Dimension binding ──────────────────────
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMaterialDimension(pmd: ProductMaterialDimension)

    @Query("SELECT * FROM product_material_dimensions WHERE productId = :productId")
    fun getMaterialDimensionsForProduct(productId: Long): Flow<List<ProductMaterialDimension>>

    @Query("DELETE FROM product_material_dimensions WHERE productId = :productId AND materialTypeId = :materialTypeId")
    suspend fun deleteMaterialDimension(productId: Long, materialTypeId: Long)

    // ── Hardware ──────────────────────────────────────────
    @Query("SELECT * FROM product_hardware WHERE productId = :productId ORDER BY id ASC")
    fun getHardwareForProduct(productId: Long): Flow<List<ProductHardware>>

    /** One-shot suspend version used in seed functions (not for UI observation). */
    @Query("SELECT * FROM product_hardware WHERE productId = :productId ORDER BY id ASC")
    suspend fun getHardwareOnce(productId: Long): List<ProductHardware>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHardware(hw: ProductHardware): Long

    @Update
    suspend fun updateHardware(hw: ProductHardware)

    @Delete
    suspend fun deleteHardware(hw: ProductHardware)

    // ── Full breakdown query ───────────────────────────────
    @Query("""
        SELECT
            pm.materialTypeId,
            t.nameEn   AS materialNameEn,
            t.nameKn   AS materialNameKn,
            t.nameHi   AS materialNameHi,
            t.defaultPrice AS pricePerUnit,
            t.materialKind,
            t.isPlywood,
            pmd.dimensionId,
            d.label    AS dimensionLabel,
            d.value    AS dimensionValue,
            d.unit     AS dimensionUnit,
            d.dimensionKind
        FROM product_materials pm
        INNER JOIN raw_material_types t    ON t.id  = pm.materialTypeId
        LEFT  JOIN product_material_dimensions pmd
                   ON pmd.productId = pm.productId AND pmd.materialTypeId = pm.materialTypeId
        LEFT  JOIN product_dimensions d    ON d.id  = pmd.dimensionId
        WHERE pm.productId = :productId
        ORDER BY t.categoryId ASC, t.id ASC
    """)
    fun getMaterialBreakdownForProduct(productId: Long): Flow<List<MaterialWithDimension>>

    // ── Quotes ────────────────────────────────────────────
    @Query("SELECT * FROM quotes ORDER BY deliveryDate ASC")
    fun getAllQuotes(): Flow<List<Quote>>

    @Insert
    suspend fun insertQuote(quote: Quote): Long

    @Delete
    suspend fun deleteQuote(quote: Quote)
}

// ═══════════════════════════════════════════════════════════
//  Database
// ═══════════════════════════════════════════════════════════

@Database(
    entities = [
        AppSettings::class,
        Category::class,
        Product::class,
        RawMaterialCategory::class,
        RawMaterialType::class,
        ProductMaterial::class,
        ProductDimension::class,
        ProductMaterialDimension::class,
        ProductHardware::class,
        Quote::class
    ],
    version = 5,
    exportSchema = false
)
abstract class KashtaKalaDatabase : RoomDatabase() {
    abstract fun dao(): KashtaKalaDao

    companion object {
        @Volatile private var INSTANCE: KashtaKalaDatabase? = null
        fun getDatabase(context: Context): KashtaKalaDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KashtaKalaDatabase::class.java,
                    "kashta_kala_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}
