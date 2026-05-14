package com.example.kashtakala;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class KashtaKalaDatabase_Impl extends KashtaKalaDatabase {
  private volatile KashtaKalaDao _kashtaKalaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `app_settings` (`id` INTEGER NOT NULL, `languageCode` TEXT, `isFirstLaunch` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` TEXT NOT NULL, `nameEn` TEXT NOT NULL, `nameKn` TEXT NOT NULL, `nameHi` TEXT NOT NULL, `parentId` TEXT, `imageRes` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_categories_id` ON `categories` (`id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoryId` TEXT NOT NULL, `subCategoryId` TEXT, `nameEn` TEXT NOT NULL, `nameKn` TEXT NOT NULL, `nameHi` TEXT NOT NULL, `imageUris` TEXT, `isFavorite` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, `isCustom` INTEGER NOT NULL, `useGlass` INTEGER NOT NULL, `usePlywood` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_products_categoryId_nameEn` ON `products` (`categoryId`, `nameEn`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `raw_material_categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nameEn` TEXT NOT NULL, `nameKn` TEXT NOT NULL, `nameHi` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `raw_material_types` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoryId` INTEGER NOT NULL, `nameEn` TEXT NOT NULL, `nameKn` TEXT NOT NULL, `nameHi` TEXT NOT NULL, `defaultPrice` REAL NOT NULL, `materialKind` TEXT NOT NULL, `isPlywood` INTEGER NOT NULL, `glassThicknessCm` REAL NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_raw_material_types_categoryId_nameEn` ON `raw_material_types` (`categoryId`, `nameEn`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `product_materials` (`productId` INTEGER NOT NULL, `materialTypeId` INTEGER NOT NULL, PRIMARY KEY(`productId`, `materialTypeId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `product_dimensions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `label` TEXT NOT NULL, `value` REAL NOT NULL, `unit` TEXT NOT NULL, `dimensionKind` TEXT NOT NULL, FOREIGN KEY(`productId`) REFERENCES `products`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_product_dimensions_productId` ON `product_dimensions` (`productId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `product_material_dimensions` (`productId` INTEGER NOT NULL, `materialTypeId` INTEGER NOT NULL, `dimensionId` INTEGER, PRIMARY KEY(`productId`, `materialTypeId`), FOREIGN KEY(`productId`, `materialTypeId`) REFERENCES `product_materials`(`productId`, `materialTypeId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`dimensionId`) REFERENCES `product_dimensions`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_product_material_dimensions_productId` ON `product_material_dimensions` (`productId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_product_material_dimensions_materialTypeId` ON `product_material_dimensions` (`materialTypeId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_product_material_dimensions_dimensionId` ON `product_material_dimensions` (`dimensionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `product_hardware` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `nameEn` TEXT NOT NULL, `nameKn` TEXT NOT NULL, `nameHi` TEXT NOT NULL, `quantity` INTEGER NOT NULL, `priceEach` REAL NOT NULL, FOREIGN KEY(`productId`) REFERENCES `products`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_product_hardware_productId` ON `product_hardware` (`productId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quotes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `productNameEn` TEXT NOT NULL, `buyerName` TEXT NOT NULL, `deliveryDate` TEXT NOT NULL, `materialTotal` REAL NOT NULL, `labourAdjustmentType` TEXT NOT NULL, `labourAdjustmentValue` REAL NOT NULL, `grandTotal` REAL NOT NULL, `snapshotJson` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '53a8a00f1a0520a48b87f82819c40a6a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `app_settings`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `raw_material_categories`");
        db.execSQL("DROP TABLE IF EXISTS `raw_material_types`");
        db.execSQL("DROP TABLE IF EXISTS `product_materials`");
        db.execSQL("DROP TABLE IF EXISTS `product_dimensions`");
        db.execSQL("DROP TABLE IF EXISTS `product_material_dimensions`");
        db.execSQL("DROP TABLE IF EXISTS `product_hardware`");
        db.execSQL("DROP TABLE IF EXISTS `quotes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsAppSettings = new HashMap<String, TableInfo.Column>(3);
        _columnsAppSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("languageCode", new TableInfo.Column("languageCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("isFirstLaunch", new TableInfo.Column("isFirstLaunch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppSettings = new TableInfo("app_settings", _columnsAppSettings, _foreignKeysAppSettings, _indicesAppSettings);
        final TableInfo _existingAppSettings = TableInfo.read(db, "app_settings");
        if (!_infoAppSettings.equals(_existingAppSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "app_settings(com.example.kashtakala.AppSettings).\n"
                  + " Expected:\n" + _infoAppSettings + "\n"
                  + " Found:\n" + _existingAppSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(6);
        _columnsCategories.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("nameKn", new TableInfo.Column("nameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("nameHi", new TableInfo.Column("nameHi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("parentId", new TableInfo.Column("parentId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("imageRes", new TableInfo.Column("imageRes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(1);
        _indicesCategories.add(new TableInfo.Index("index_categories_id", true, Arrays.asList("id"), Arrays.asList("ASC")));
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.example.kashtakala.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(12);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("subCategoryId", new TableInfo.Column("subCategoryId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("nameKn", new TableInfo.Column("nameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("nameHi", new TableInfo.Column("nameHi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("imageUris", new TableInfo.Column("imageUris", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("useGlass", new TableInfo.Column("useGlass", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("usePlywood", new TableInfo.Column("usePlywood", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(1);
        _indicesProducts.add(new TableInfo.Index("index_products_categoryId_nameEn", true, Arrays.asList("categoryId", "nameEn"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.example.kashtakala.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsRawMaterialCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsRawMaterialCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialCategories.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialCategories.put("nameKn", new TableInfo.Column("nameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialCategories.put("nameHi", new TableInfo.Column("nameHi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRawMaterialCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRawMaterialCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRawMaterialCategories = new TableInfo("raw_material_categories", _columnsRawMaterialCategories, _foreignKeysRawMaterialCategories, _indicesRawMaterialCategories);
        final TableInfo _existingRawMaterialCategories = TableInfo.read(db, "raw_material_categories");
        if (!_infoRawMaterialCategories.equals(_existingRawMaterialCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "raw_material_categories(com.example.kashtakala.RawMaterialCategory).\n"
                  + " Expected:\n" + _infoRawMaterialCategories + "\n"
                  + " Found:\n" + _existingRawMaterialCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsRawMaterialTypes = new HashMap<String, TableInfo.Column>(9);
        _columnsRawMaterialTypes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("nameKn", new TableInfo.Column("nameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("nameHi", new TableInfo.Column("nameHi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("defaultPrice", new TableInfo.Column("defaultPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("materialKind", new TableInfo.Column("materialKind", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("isPlywood", new TableInfo.Column("isPlywood", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRawMaterialTypes.put("glassThicknessCm", new TableInfo.Column("glassThicknessCm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRawMaterialTypes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRawMaterialTypes = new HashSet<TableInfo.Index>(1);
        _indicesRawMaterialTypes.add(new TableInfo.Index("index_raw_material_types_categoryId_nameEn", true, Arrays.asList("categoryId", "nameEn"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoRawMaterialTypes = new TableInfo("raw_material_types", _columnsRawMaterialTypes, _foreignKeysRawMaterialTypes, _indicesRawMaterialTypes);
        final TableInfo _existingRawMaterialTypes = TableInfo.read(db, "raw_material_types");
        if (!_infoRawMaterialTypes.equals(_existingRawMaterialTypes)) {
          return new RoomOpenHelper.ValidationResult(false, "raw_material_types(com.example.kashtakala.RawMaterialType).\n"
                  + " Expected:\n" + _infoRawMaterialTypes + "\n"
                  + " Found:\n" + _existingRawMaterialTypes);
        }
        final HashMap<String, TableInfo.Column> _columnsProductMaterials = new HashMap<String, TableInfo.Column>(2);
        _columnsProductMaterials.put("productId", new TableInfo.Column("productId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductMaterials.put("materialTypeId", new TableInfo.Column("materialTypeId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductMaterials = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProductMaterials = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProductMaterials = new TableInfo("product_materials", _columnsProductMaterials, _foreignKeysProductMaterials, _indicesProductMaterials);
        final TableInfo _existingProductMaterials = TableInfo.read(db, "product_materials");
        if (!_infoProductMaterials.equals(_existingProductMaterials)) {
          return new RoomOpenHelper.ValidationResult(false, "product_materials(com.example.kashtakala.ProductMaterial).\n"
                  + " Expected:\n" + _infoProductMaterials + "\n"
                  + " Found:\n" + _existingProductMaterials);
        }
        final HashMap<String, TableInfo.Column> _columnsProductDimensions = new HashMap<String, TableInfo.Column>(6);
        _columnsProductDimensions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductDimensions.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductDimensions.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductDimensions.put("value", new TableInfo.Column("value", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductDimensions.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductDimensions.put("dimensionKind", new TableInfo.Column("dimensionKind", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductDimensions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProductDimensions.add(new TableInfo.ForeignKey("products", "CASCADE", "NO ACTION", Arrays.asList("productId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesProductDimensions = new HashSet<TableInfo.Index>(1);
        _indicesProductDimensions.add(new TableInfo.Index("index_product_dimensions_productId", false, Arrays.asList("productId"), Arrays.asList("ASC")));
        final TableInfo _infoProductDimensions = new TableInfo("product_dimensions", _columnsProductDimensions, _foreignKeysProductDimensions, _indicesProductDimensions);
        final TableInfo _existingProductDimensions = TableInfo.read(db, "product_dimensions");
        if (!_infoProductDimensions.equals(_existingProductDimensions)) {
          return new RoomOpenHelper.ValidationResult(false, "product_dimensions(com.example.kashtakala.ProductDimension).\n"
                  + " Expected:\n" + _infoProductDimensions + "\n"
                  + " Found:\n" + _existingProductDimensions);
        }
        final HashMap<String, TableInfo.Column> _columnsProductMaterialDimensions = new HashMap<String, TableInfo.Column>(3);
        _columnsProductMaterialDimensions.put("productId", new TableInfo.Column("productId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductMaterialDimensions.put("materialTypeId", new TableInfo.Column("materialTypeId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductMaterialDimensions.put("dimensionId", new TableInfo.Column("dimensionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductMaterialDimensions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysProductMaterialDimensions.add(new TableInfo.ForeignKey("product_materials", "CASCADE", "NO ACTION", Arrays.asList("productId", "materialTypeId"), Arrays.asList("productId", "materialTypeId")));
        _foreignKeysProductMaterialDimensions.add(new TableInfo.ForeignKey("product_dimensions", "SET NULL", "NO ACTION", Arrays.asList("dimensionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesProductMaterialDimensions = new HashSet<TableInfo.Index>(3);
        _indicesProductMaterialDimensions.add(new TableInfo.Index("index_product_material_dimensions_productId", false, Arrays.asList("productId"), Arrays.asList("ASC")));
        _indicesProductMaterialDimensions.add(new TableInfo.Index("index_product_material_dimensions_materialTypeId", false, Arrays.asList("materialTypeId"), Arrays.asList("ASC")));
        _indicesProductMaterialDimensions.add(new TableInfo.Index("index_product_material_dimensions_dimensionId", false, Arrays.asList("dimensionId"), Arrays.asList("ASC")));
        final TableInfo _infoProductMaterialDimensions = new TableInfo("product_material_dimensions", _columnsProductMaterialDimensions, _foreignKeysProductMaterialDimensions, _indicesProductMaterialDimensions);
        final TableInfo _existingProductMaterialDimensions = TableInfo.read(db, "product_material_dimensions");
        if (!_infoProductMaterialDimensions.equals(_existingProductMaterialDimensions)) {
          return new RoomOpenHelper.ValidationResult(false, "product_material_dimensions(com.example.kashtakala.ProductMaterialDimension).\n"
                  + " Expected:\n" + _infoProductMaterialDimensions + "\n"
                  + " Found:\n" + _existingProductMaterialDimensions);
        }
        final HashMap<String, TableInfo.Column> _columnsProductHardware = new HashMap<String, TableInfo.Column>(7);
        _columnsProductHardware.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("nameEn", new TableInfo.Column("nameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("nameKn", new TableInfo.Column("nameKn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("nameHi", new TableInfo.Column("nameHi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductHardware.put("priceEach", new TableInfo.Column("priceEach", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductHardware = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProductHardware.add(new TableInfo.ForeignKey("products", "CASCADE", "NO ACTION", Arrays.asList("productId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesProductHardware = new HashSet<TableInfo.Index>(1);
        _indicesProductHardware.add(new TableInfo.Index("index_product_hardware_productId", false, Arrays.asList("productId"), Arrays.asList("ASC")));
        final TableInfo _infoProductHardware = new TableInfo("product_hardware", _columnsProductHardware, _foreignKeysProductHardware, _indicesProductHardware);
        final TableInfo _existingProductHardware = TableInfo.read(db, "product_hardware");
        if (!_infoProductHardware.equals(_existingProductHardware)) {
          return new RoomOpenHelper.ValidationResult(false, "product_hardware(com.example.kashtakala.ProductHardware).\n"
                  + " Expected:\n" + _infoProductHardware + "\n"
                  + " Found:\n" + _existingProductHardware);
        }
        final HashMap<String, TableInfo.Column> _columnsQuotes = new HashMap<String, TableInfo.Column>(11);
        _columnsQuotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("productNameEn", new TableInfo.Column("productNameEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("buyerName", new TableInfo.Column("buyerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("deliveryDate", new TableInfo.Column("deliveryDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("materialTotal", new TableInfo.Column("materialTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("labourAdjustmentType", new TableInfo.Column("labourAdjustmentType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("labourAdjustmentValue", new TableInfo.Column("labourAdjustmentValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("grandTotal", new TableInfo.Column("grandTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("snapshotJson", new TableInfo.Column("snapshotJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuotes = new TableInfo("quotes", _columnsQuotes, _foreignKeysQuotes, _indicesQuotes);
        final TableInfo _existingQuotes = TableInfo.read(db, "quotes");
        if (!_infoQuotes.equals(_existingQuotes)) {
          return new RoomOpenHelper.ValidationResult(false, "quotes(com.example.kashtakala.Quote).\n"
                  + " Expected:\n" + _infoQuotes + "\n"
                  + " Found:\n" + _existingQuotes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "53a8a00f1a0520a48b87f82819c40a6a", "4fd5c87cbfa6eb147c7e8453f6818c4f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "app_settings","categories","products","raw_material_categories","raw_material_types","product_materials","product_dimensions","product_material_dimensions","product_hardware","quotes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `app_settings`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `raw_material_categories`");
      _db.execSQL("DELETE FROM `raw_material_types`");
      _db.execSQL("DELETE FROM `product_materials`");
      _db.execSQL("DELETE FROM `product_dimensions`");
      _db.execSQL("DELETE FROM `product_material_dimensions`");
      _db.execSQL("DELETE FROM `product_hardware`");
      _db.execSQL("DELETE FROM `quotes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(KashtaKalaDao.class, KashtaKalaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public KashtaKalaDao dao() {
    if (_kashtaKalaDao != null) {
      return _kashtaKalaDao;
    } else {
      synchronized(this) {
        if(_kashtaKalaDao == null) {
          _kashtaKalaDao = new KashtaKalaDao_Impl(this);
        }
        return _kashtaKalaDao;
      }
    }
  }
}
