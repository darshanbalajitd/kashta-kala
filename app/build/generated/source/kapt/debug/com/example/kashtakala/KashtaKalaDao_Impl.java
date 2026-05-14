package com.example.kashtakala;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class KashtaKalaDao_Impl implements KashtaKalaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppSettings> __insertionAdapterOfAppSettings;

  private final EntityInsertionAdapter<Category> __insertionAdapterOfCategory;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  private final EntityInsertionAdapter<RawMaterialCategory> __insertionAdapterOfRawMaterialCategory;

  private final EntityInsertionAdapter<RawMaterialType> __insertionAdapterOfRawMaterialType;

  private final EntityInsertionAdapter<ProductMaterial> __insertionAdapterOfProductMaterial;

  private final EntityInsertionAdapter<ProductDimension> __insertionAdapterOfProductDimension;

  private final EntityInsertionAdapter<ProductMaterialDimension> __insertionAdapterOfProductMaterialDimension;

  private final EntityInsertionAdapter<ProductHardware> __insertionAdapterOfProductHardware;

  private final EntityInsertionAdapter<Quote> __insertionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Product> __deletionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<RawMaterialCategory> __deletionAdapterOfRawMaterialCategory;

  private final EntityDeletionOrUpdateAdapter<RawMaterialType> __deletionAdapterOfRawMaterialType;

  private final EntityDeletionOrUpdateAdapter<ProductMaterial> __deletionAdapterOfProductMaterial;

  private final EntityDeletionOrUpdateAdapter<ProductDimension> __deletionAdapterOfProductDimension;

  private final EntityDeletionOrUpdateAdapter<ProductHardware> __deletionAdapterOfProductHardware;

  private final EntityDeletionOrUpdateAdapter<Quote> __deletionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Product> __updateAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<RawMaterialCategory> __updateAdapterOfRawMaterialCategory;

  private final EntityDeletionOrUpdateAdapter<RawMaterialType> __updateAdapterOfRawMaterialType;

  private final EntityDeletionOrUpdateAdapter<ProductDimension> __updateAdapterOfProductDimension;

  private final EntityDeletionOrUpdateAdapter<ProductHardware> __updateAdapterOfProductHardware;

  private final SharedSQLiteStatement __preparedStmtOfClearProductMaterials;

  private final SharedSQLiteStatement __preparedStmtOfClearDimensionsForProduct;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMaterialDimension;

  public KashtaKalaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppSettings = new EntityInsertionAdapter<AppSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `app_settings` (`id`,`languageCode`,`isFirstLaunch`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppSettings entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getLanguageCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getLanguageCode());
        }
        final int _tmp = entity.isFirstLaunch() ? 1 : 0;
        statement.bindLong(3, _tmp);
      }
    };
    this.__insertionAdapterOfCategory = new EntityInsertionAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `categories` (`id`,`nameEn`,`nameKn`,`nameHi`,`parentId`,`imageRes`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Category entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getNameEn() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameHi());
        }
        if (entity.getParentId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getParentId());
        }
        if (entity.getImageRes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getImageRes());
        }
      }
    };
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `products` (`id`,`categoryId`,`subCategoryId`,`nameEn`,`nameKn`,`nameHi`,`imageUris`,`isFavorite`,`lastModified`,`isCustom`,`useGlass`,`usePlywood`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCategoryId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCategoryId());
        }
        if (entity.getSubCategoryId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubCategoryId());
        }
        if (entity.getNameEn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNameHi());
        }
        if (entity.getImageUris() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getImageUris());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getLastModified());
        final int _tmp_1 = entity.isCustom() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.getUseGlass() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        final int _tmp_3 = entity.getUsePlywood() ? 1 : 0;
        statement.bindLong(12, _tmp_3);
      }
    };
    this.__insertionAdapterOfRawMaterialCategory = new EntityInsertionAdapter<RawMaterialCategory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `raw_material_categories` (`id`,`nameEn`,`nameKn`,`nameHi`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialCategory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNameEn() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameHi());
        }
      }
    };
    this.__insertionAdapterOfRawMaterialType = new EntityInsertionAdapter<RawMaterialType>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `raw_material_types` (`id`,`categoryId`,`nameEn`,`nameKn`,`nameHi`,`defaultPrice`,`materialKind`,`isPlywood`,`glassThicknessCm`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialType entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameHi());
        }
        statement.bindDouble(6, entity.getDefaultPrice());
        if (entity.getMaterialKind() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMaterialKind());
        }
        final int _tmp = entity.isPlywood() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindDouble(9, entity.getGlassThicknessCm());
      }
    };
    this.__insertionAdapterOfProductMaterial = new EntityInsertionAdapter<ProductMaterial>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `product_materials` (`productId`,`materialTypeId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductMaterial entity) {
        statement.bindLong(1, entity.getProductId());
        statement.bindLong(2, entity.getMaterialTypeId());
      }
    };
    this.__insertionAdapterOfProductDimension = new EntityInsertionAdapter<ProductDimension>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `product_dimensions` (`id`,`productId`,`label`,`value`,`unit`,`dimensionKind`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductDimension entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLabel());
        }
        statement.bindDouble(4, entity.getValue());
        if (entity.getUnit() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUnit());
        }
        if (entity.getDimensionKind() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDimensionKind());
        }
      }
    };
    this.__insertionAdapterOfProductMaterialDimension = new EntityInsertionAdapter<ProductMaterialDimension>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `product_material_dimensions` (`productId`,`materialTypeId`,`dimensionId`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductMaterialDimension entity) {
        statement.bindLong(1, entity.getProductId());
        statement.bindLong(2, entity.getMaterialTypeId());
        if (entity.getDimensionId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getDimensionId());
        }
      }
    };
    this.__insertionAdapterOfProductHardware = new EntityInsertionAdapter<ProductHardware>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `product_hardware` (`id`,`productId`,`nameEn`,`nameKn`,`nameHi`,`quantity`,`priceEach`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductHardware entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameHi());
        }
        statement.bindLong(6, entity.getQuantity());
        statement.bindDouble(7, entity.getPriceEach());
      }
    };
    this.__insertionAdapterOfQuote = new EntityInsertionAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quotes` (`id`,`productId`,`productNameEn`,`buyerName`,`deliveryDate`,`materialTotal`,`labourAdjustmentType`,`labourAdjustmentValue`,`grandTotal`,`snapshotJson`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getProductNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductNameEn());
        }
        if (entity.getBuyerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBuyerName());
        }
        if (entity.getDeliveryDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDeliveryDate());
        }
        statement.bindDouble(6, entity.getMaterialTotal());
        if (entity.getLabourAdjustmentType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLabourAdjustmentType());
        }
        statement.bindDouble(8, entity.getLabourAdjustmentValue());
        statement.bindDouble(9, entity.getGrandTotal());
        if (entity.getSnapshotJson() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSnapshotJson());
        }
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `products` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfRawMaterialCategory = new EntityDeletionOrUpdateAdapter<RawMaterialCategory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `raw_material_categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialCategory entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfRawMaterialType = new EntityDeletionOrUpdateAdapter<RawMaterialType>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `raw_material_types` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialType entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfProductMaterial = new EntityDeletionOrUpdateAdapter<ProductMaterial>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `product_materials` WHERE `productId` = ? AND `materialTypeId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductMaterial entity) {
        statement.bindLong(1, entity.getProductId());
        statement.bindLong(2, entity.getMaterialTypeId());
      }
    };
    this.__deletionAdapterOfProductDimension = new EntityDeletionOrUpdateAdapter<ProductDimension>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `product_dimensions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductDimension entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfProductHardware = new EntityDeletionOrUpdateAdapter<ProductHardware>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `product_hardware` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductHardware entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfQuote = new EntityDeletionOrUpdateAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quotes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`categoryId` = ?,`subCategoryId` = ?,`nameEn` = ?,`nameKn` = ?,`nameHi` = ?,`imageUris` = ?,`isFavorite` = ?,`lastModified` = ?,`isCustom` = ?,`useGlass` = ?,`usePlywood` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getCategoryId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCategoryId());
        }
        if (entity.getSubCategoryId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubCategoryId());
        }
        if (entity.getNameEn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNameHi());
        }
        if (entity.getImageUris() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getImageUris());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getLastModified());
        final int _tmp_1 = entity.isCustom() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.getUseGlass() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        final int _tmp_3 = entity.getUsePlywood() ? 1 : 0;
        statement.bindLong(12, _tmp_3);
        statement.bindLong(13, entity.getId());
      }
    };
    this.__updateAdapterOfRawMaterialCategory = new EntityDeletionOrUpdateAdapter<RawMaterialCategory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `raw_material_categories` SET `id` = ?,`nameEn` = ?,`nameKn` = ?,`nameHi` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialCategory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNameEn() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameHi());
        }
        statement.bindLong(5, entity.getId());
      }
    };
    this.__updateAdapterOfRawMaterialType = new EntityDeletionOrUpdateAdapter<RawMaterialType>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `raw_material_types` SET `id` = ?,`categoryId` = ?,`nameEn` = ?,`nameKn` = ?,`nameHi` = ?,`defaultPrice` = ?,`materialKind` = ?,`isPlywood` = ?,`glassThicknessCm` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RawMaterialType entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameHi());
        }
        statement.bindDouble(6, entity.getDefaultPrice());
        if (entity.getMaterialKind() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMaterialKind());
        }
        final int _tmp = entity.isPlywood() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindDouble(9, entity.getGlassThicknessCm());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__updateAdapterOfProductDimension = new EntityDeletionOrUpdateAdapter<ProductDimension>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `product_dimensions` SET `id` = ?,`productId` = ?,`label` = ?,`value` = ?,`unit` = ?,`dimensionKind` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductDimension entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLabel());
        }
        statement.bindDouble(4, entity.getValue());
        if (entity.getUnit() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUnit());
        }
        if (entity.getDimensionKind() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDimensionKind());
        }
        statement.bindLong(7, entity.getId());
      }
    };
    this.__updateAdapterOfProductHardware = new EntityDeletionOrUpdateAdapter<ProductHardware>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `product_hardware` SET `id` = ?,`productId` = ?,`nameEn` = ?,`nameKn` = ?,`nameHi` = ?,`quantity` = ?,`priceEach` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductHardware entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getNameEn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNameEn());
        }
        if (entity.getNameKn() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNameKn());
        }
        if (entity.getNameHi() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameHi());
        }
        statement.bindLong(6, entity.getQuantity());
        statement.bindDouble(7, entity.getPriceEach());
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfClearProductMaterials = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM product_materials WHERE productId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearDimensionsForProduct = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM product_dimensions WHERE productId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteMaterialDimension = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM product_material_dimensions WHERE productId = ? AND materialTypeId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object updateSettings(final AppSettings settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAppSettings.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCategories(final List<Category> categories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategory.insert(categories);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertProduct(final Product product, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProduct.insertAndReturnId(product);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRawMaterialCategory(final RawMaterialCategory cat,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRawMaterialCategory.insertAndReturnId(cat);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRawMaterialType(final RawMaterialType type,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRawMaterialType.insertAndReturnId(type);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertProductMaterial(final ProductMaterial pm,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductMaterial.insert(pm);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDimension(final ProductDimension dim,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProductDimension.insertAndReturnId(dim);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertMaterialDimension(final ProductMaterialDimension pmd,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductMaterialDimension.insert(pmd);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertHardware(final ProductHardware hw,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProductHardware.insertAndReturnId(hw);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertQuote(final Quote quote, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuote.insertAndReturnId(quote);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProduct(final Product product, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProduct.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRawMaterialCategory(final RawMaterialCategory cat,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRawMaterialCategory.handle(cat);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRawMaterialType(final RawMaterialType type,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRawMaterialType.handle(type);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProductMaterial(final ProductMaterial pm,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProductMaterial.handle(pm);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDimension(final ProductDimension dim,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProductDimension.handle(dim);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHardware(final ProductHardware hw,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProductHardware.handle(hw);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQuote(final Quote quote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQuote.handle(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProduct(final Product product, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProduct.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRawMaterialCategory(final RawMaterialCategory cat,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRawMaterialCategory.handle(cat);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRawMaterialType(final RawMaterialType type,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRawMaterialType.handle(type);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDimension(final ProductDimension dim,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProductDimension.handle(dim);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHardware(final ProductHardware hw,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProductHardware.handle(hw);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object selectMaterialInCategory(final long productId, final long catId,
      final long newTypeId, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> KashtaKalaDao.DefaultImpls.selectMaterialInCategory(KashtaKalaDao_Impl.this, productId, catId, newTypeId, __cont), $completion);
  }

  @Override
  public Object clearProductMaterials(final long productId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearProductMaterials.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, productId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearProductMaterials.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearDimensionsForProduct(final long productId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearDimensionsForProduct.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, productId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearDimensionsForProduct.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMaterialDimension(final long productId, final long materialTypeId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMaterialDimension.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, productId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, materialTypeId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteMaterialDimension.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<AppSettings> getSettings() {
    final String _sql = "SELECT * FROM app_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"app_settings"}, new Callable<AppSettings>() {
      @Override
      @Nullable
      public AppSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLanguageCode = CursorUtil.getColumnIndexOrThrow(_cursor, "languageCode");
          final int _cursorIndexOfIsFirstLaunch = CursorUtil.getColumnIndexOrThrow(_cursor, "isFirstLaunch");
          final AppSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpLanguageCode;
            if (_cursor.isNull(_cursorIndexOfLanguageCode)) {
              _tmpLanguageCode = null;
            } else {
              _tmpLanguageCode = _cursor.getString(_cursorIndexOfLanguageCode);
            }
            final boolean _tmpIsFirstLaunch;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFirstLaunch);
            _tmpIsFirstLaunch = _tmp != 0;
            _result = new AppSettings(_tmpId,_tmpLanguageCode,_tmpIsFirstLaunch);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Category>> getRootCategories() {
    final String _sql = "SELECT * FROM categories WHERE parentId IS NULL ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<Category>>() {
      @Override
      @NonNull
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpParentId;
            if (_cursor.isNull(_cursorIndexOfParentId)) {
              _tmpParentId = null;
            } else {
              _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
            }
            final String _tmpImageRes;
            if (_cursor.isNull(_cursorIndexOfImageRes)) {
              _tmpImageRes = null;
            } else {
              _tmpImageRes = _cursor.getString(_cursorIndexOfImageRes);
            }
            _item = new Category(_tmpId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpParentId,_tmpImageRes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Category>> getSubCategories(final String parentId) {
    final String _sql = "SELECT * FROM categories WHERE parentId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (parentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<Category>>() {
      @Override
      @NonNull
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpParentId;
            if (_cursor.isNull(_cursorIndexOfParentId)) {
              _tmpParentId = null;
            } else {
              _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
            }
            final String _tmpImageRes;
            if (_cursor.isNull(_cursorIndexOfImageRes)) {
              _tmpImageRes = null;
            } else {
              _tmpImageRes = _cursor.getString(_cursorIndexOfImageRes);
            }
            _item = new Category(_tmpId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpParentId,_tmpImageRes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCategoryById(final String id, final Continuation<? super Category> $completion) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Category>() {
      @Override
      @Nullable
      public Category call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfParentId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentId");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final Category _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpParentId;
            if (_cursor.isNull(_cursorIndexOfParentId)) {
              _tmpParentId = null;
            } else {
              _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
            }
            final String _tmpImageRes;
            if (_cursor.isNull(_cursorIndexOfImageRes)) {
              _tmpImageRes = null;
            } else {
              _tmpImageRes = _cursor.getString(_cursorIndexOfImageRes);
            }
            _result = new Category(_tmpId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpParentId,_tmpImageRes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Product>> getProductsByCategory(final String catId) {
    final String _sql = "\n"
            + "        SELECT * FROM products\n"
            + "        WHERE categoryId = ? OR subCategoryId = ?\n"
            + "        ORDER BY isFavorite DESC, lastModified DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    _argIndex = 2;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<Product>>() {
      @Override
      @NonNull
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfSubCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "subCategoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfImageUris = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUris");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfUseGlass = CursorUtil.getColumnIndexOrThrow(_cursor, "useGlass");
          final int _cursorIndexOfUsePlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "usePlywood");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpSubCategoryId;
            if (_cursor.isNull(_cursorIndexOfSubCategoryId)) {
              _tmpSubCategoryId = null;
            } else {
              _tmpSubCategoryId = _cursor.getString(_cursorIndexOfSubCategoryId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpImageUris;
            if (_cursor.isNull(_cursorIndexOfImageUris)) {
              _tmpImageUris = null;
            } else {
              _tmpImageUris = _cursor.getString(_cursorIndexOfImageUris);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final boolean _tmpUseGlass;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUseGlass);
            _tmpUseGlass = _tmp_2 != 0;
            final boolean _tmpUsePlywood;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfUsePlywood);
            _tmpUsePlywood = _tmp_3 != 0;
            _item = new Product(_tmpId,_tmpCategoryId,_tmpSubCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpImageUris,_tmpIsFavorite,_tmpLastModified,_tmpIsCustom,_tmpUseGlass,_tmpUsePlywood);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Product>> getCustomProductsByCategory(final String catId) {
    final String _sql = "\n"
            + "        SELECT * FROM products\n"
            + "        WHERE (categoryId = ? OR subCategoryId = ?) AND isCustom = 1\n"
            + "        ORDER BY isFavorite DESC, lastModified DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    _argIndex = 2;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<Product>>() {
      @Override
      @NonNull
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfSubCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "subCategoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfImageUris = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUris");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfUseGlass = CursorUtil.getColumnIndexOrThrow(_cursor, "useGlass");
          final int _cursorIndexOfUsePlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "usePlywood");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpSubCategoryId;
            if (_cursor.isNull(_cursorIndexOfSubCategoryId)) {
              _tmpSubCategoryId = null;
            } else {
              _tmpSubCategoryId = _cursor.getString(_cursorIndexOfSubCategoryId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpImageUris;
            if (_cursor.isNull(_cursorIndexOfImageUris)) {
              _tmpImageUris = null;
            } else {
              _tmpImageUris = _cursor.getString(_cursorIndexOfImageUris);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final boolean _tmpUseGlass;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUseGlass);
            _tmpUseGlass = _tmp_2 != 0;
            final boolean _tmpUsePlywood;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfUsePlywood);
            _tmpUsePlywood = _tmp_3 != 0;
            _item = new Product(_tmpId,_tmpCategoryId,_tmpSubCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpImageUris,_tmpIsFavorite,_tmpLastModified,_tmpIsCustom,_tmpUseGlass,_tmpUsePlywood);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Product> getProductById(final long id) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<Product>() {
      @Override
      @Nullable
      public Product call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfSubCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "subCategoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfImageUris = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUris");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfUseGlass = CursorUtil.getColumnIndexOrThrow(_cursor, "useGlass");
          final int _cursorIndexOfUsePlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "usePlywood");
          final Product _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpSubCategoryId;
            if (_cursor.isNull(_cursorIndexOfSubCategoryId)) {
              _tmpSubCategoryId = null;
            } else {
              _tmpSubCategoryId = _cursor.getString(_cursorIndexOfSubCategoryId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpImageUris;
            if (_cursor.isNull(_cursorIndexOfImageUris)) {
              _tmpImageUris = null;
            } else {
              _tmpImageUris = _cursor.getString(_cursorIndexOfImageUris);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final boolean _tmpUseGlass;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUseGlass);
            _tmpUseGlass = _tmp_2 != 0;
            final boolean _tmpUsePlywood;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfUsePlywood);
            _tmpUsePlywood = _tmp_3 != 0;
            _result = new Product(_tmpId,_tmpCategoryId,_tmpSubCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpImageUris,_tmpIsFavorite,_tmpLastModified,_tmpIsCustom,_tmpUseGlass,_tmpUsePlywood);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findProductByName(final String catId, final String nameEn,
      final Continuation<? super Product> $completion) {
    final String _sql = "SELECT * FROM products WHERE categoryId = ? AND nameEn = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (catId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, catId);
    }
    _argIndex = 2;
    if (nameEn == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nameEn);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Product>() {
      @Override
      @Nullable
      public Product call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfSubCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "subCategoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfImageUris = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUris");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfUseGlass = CursorUtil.getColumnIndexOrThrow(_cursor, "useGlass");
          final int _cursorIndexOfUsePlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "usePlywood");
          final Product _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpSubCategoryId;
            if (_cursor.isNull(_cursorIndexOfSubCategoryId)) {
              _tmpSubCategoryId = null;
            } else {
              _tmpSubCategoryId = _cursor.getString(_cursorIndexOfSubCategoryId);
            }
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final String _tmpImageUris;
            if (_cursor.isNull(_cursorIndexOfImageUris)) {
              _tmpImageUris = null;
            } else {
              _tmpImageUris = _cursor.getString(_cursorIndexOfImageUris);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final boolean _tmpUseGlass;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfUseGlass);
            _tmpUseGlass = _tmp_2 != 0;
            final boolean _tmpUsePlywood;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfUsePlywood);
            _tmpUsePlywood = _tmp_3 != 0;
            _result = new Product(_tmpId,_tmpCategoryId,_tmpSubCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpImageUris,_tmpIsFavorite,_tmpLastModified,_tmpIsCustom,_tmpUseGlass,_tmpUsePlywood);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RawMaterialCategory>> getRawMaterialCategories() {
    final String _sql = "SELECT * FROM raw_material_categories ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"raw_material_categories"}, new Callable<List<RawMaterialCategory>>() {
      @Override
      @NonNull
      public List<RawMaterialCategory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final List<RawMaterialCategory> _result = new ArrayList<RawMaterialCategory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RawMaterialCategory _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            _item = new RawMaterialCategory(_tmpId,_tmpNameEn,_tmpNameKn,_tmpNameHi);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findRawCategoryByName(final String nameEn,
      final Continuation<? super RawMaterialCategory> $completion) {
    final String _sql = "SELECT * FROM raw_material_categories WHERE nameEn = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (nameEn == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nameEn);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialCategory>() {
      @Override
      @Nullable
      public RawMaterialCategory call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final RawMaterialCategory _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            _result = new RawMaterialCategory(_tmpId,_tmpNameEn,_tmpNameKn,_tmpNameHi);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RawMaterialType>> getRawMaterialTypes(final long catId) {
    final String _sql = "SELECT * FROM raw_material_types WHERE categoryId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, catId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"raw_material_types"}, new Callable<List<RawMaterialType>>() {
      @Override
      @NonNull
      public List<RawMaterialType> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final List<RawMaterialType> _result = new ArrayList<RawMaterialType>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RawMaterialType _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _item = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllRawMaterialTypes(
      final Continuation<? super List<RawMaterialType>> $completion) {
    final String _sql = "SELECT * FROM raw_material_types ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RawMaterialType>>() {
      @Override
      @NonNull
      public List<RawMaterialType> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final List<RawMaterialType> _result = new ArrayList<RawMaterialType>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RawMaterialType _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _item = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRawMaterialTypeById(final long id,
      final Continuation<? super RawMaterialType> $completion) {
    final String _sql = "SELECT * FROM raw_material_types WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialType>() {
      @Override
      @Nullable
      public RawMaterialType call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final RawMaterialType _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _result = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object findRawTypeByName(final long catId, final String nameEn,
      final Continuation<? super RawMaterialType> $completion) {
    final String _sql = "SELECT * FROM raw_material_types WHERE categoryId = ? AND nameEn = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, catId);
    _argIndex = 2;
    if (nameEn == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nameEn);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialType>() {
      @Override
      @Nullable
      public RawMaterialType call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final RawMaterialType _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _result = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFirstTypeInCategory(final long catId,
      final Continuation<? super RawMaterialType> $completion) {
    final String _sql = "SELECT * FROM raw_material_types WHERE categoryId = ? ORDER BY id ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, catId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialType>() {
      @Override
      @Nullable
      public RawMaterialType call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final RawMaterialType _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _result = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlywoodTypeInCategory(final long catId,
      final Continuation<? super RawMaterialType> $completion) {
    final String _sql = "SELECT * FROM raw_material_types WHERE categoryId = ? AND isPlywood = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, catId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialType>() {
      @Override
      @Nullable
      public RawMaterialType call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final RawMaterialType _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _result = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RawMaterialType>> getMaterialsForProduct(final long productId) {
    final String _sql = "\n"
            + "        SELECT t.* FROM raw_material_types t\n"
            + "        INNER JOIN product_materials pm ON t.id = pm.materialTypeId\n"
            + "        WHERE pm.productId = ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"raw_material_types",
        "product_materials"}, new Callable<List<RawMaterialType>>() {
      @Override
      @NonNull
      public List<RawMaterialType> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final List<RawMaterialType> _result = new ArrayList<RawMaterialType>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RawMaterialType _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _item = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSelectedTypeForCategory(final long productId, final long catId,
      final Continuation<? super RawMaterialType> $completion) {
    final String _sql = "\n"
            + "        SELECT t.* FROM raw_material_types t\n"
            + "        INNER JOIN product_materials pm ON t.id = pm.materialTypeId\n"
            + "        WHERE pm.productId = ? AND t.categoryId = ?\n"
            + "        LIMIT 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, catId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RawMaterialType>() {
      @Override
      @Nullable
      public RawMaterialType call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfDefaultPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultPrice");
          final int _cursorIndexOfMaterialKind = CursorUtil.getColumnIndexOrThrow(_cursor, "materialKind");
          final int _cursorIndexOfIsPlywood = CursorUtil.getColumnIndexOrThrow(_cursor, "isPlywood");
          final int _cursorIndexOfGlassThicknessCm = CursorUtil.getColumnIndexOrThrow(_cursor, "glassThicknessCm");
          final RawMaterialType _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final double _tmpDefaultPrice;
            _tmpDefaultPrice = _cursor.getDouble(_cursorIndexOfDefaultPrice);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final double _tmpGlassThicknessCm;
            _tmpGlassThicknessCm = _cursor.getDouble(_cursorIndexOfGlassThicknessCm);
            _result = new RawMaterialType(_tmpId,_tmpCategoryId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpDefaultPrice,_tmpMaterialKind,_tmpIsPlywood,_tmpGlassThicknessCm);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProductDimension>> getDimensionsForProduct(final long productId) {
    final String _sql = "SELECT * FROM product_dimensions WHERE productId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"product_dimensions"}, new Callable<List<ProductDimension>>() {
      @Override
      @NonNull
      public List<ProductDimension> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfDimensionKind = CursorUtil.getColumnIndexOrThrow(_cursor, "dimensionKind");
          final List<ProductDimension> _result = new ArrayList<ProductDimension>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductDimension _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final String _tmpDimensionKind;
            if (_cursor.isNull(_cursorIndexOfDimensionKind)) {
              _tmpDimensionKind = null;
            } else {
              _tmpDimensionKind = _cursor.getString(_cursorIndexOfDimensionKind);
            }
            _item = new ProductDimension(_tmpId,_tmpProductId,_tmpLabel,_tmpValue,_tmpUnit,_tmpDimensionKind);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWoodDimensionsForProduct(final long productId,
      final Continuation<? super List<ProductDimension>> $completion) {
    final String _sql = "SELECT * FROM product_dimensions WHERE productId = ? AND dimensionKind IN ('wood_surface','wood_supporting','wood_foot')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProductDimension>>() {
      @Override
      @NonNull
      public List<ProductDimension> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfDimensionKind = CursorUtil.getColumnIndexOrThrow(_cursor, "dimensionKind");
          final List<ProductDimension> _result = new ArrayList<ProductDimension>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductDimension _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final String _tmpDimensionKind;
            if (_cursor.isNull(_cursorIndexOfDimensionKind)) {
              _tmpDimensionKind = null;
            } else {
              _tmpDimensionKind = _cursor.getString(_cursorIndexOfDimensionKind);
            }
            _item = new ProductDimension(_tmpId,_tmpProductId,_tmpLabel,_tmpValue,_tmpUnit,_tmpDimensionKind);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProductMaterialDimension>> getMaterialDimensionsForProduct(
      final long productId) {
    final String _sql = "SELECT * FROM product_material_dimensions WHERE productId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"product_material_dimensions"}, new Callable<List<ProductMaterialDimension>>() {
      @Override
      @NonNull
      public List<ProductMaterialDimension> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfMaterialTypeId = CursorUtil.getColumnIndexOrThrow(_cursor, "materialTypeId");
          final int _cursorIndexOfDimensionId = CursorUtil.getColumnIndexOrThrow(_cursor, "dimensionId");
          final List<ProductMaterialDimension> _result = new ArrayList<ProductMaterialDimension>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductMaterialDimension _item;
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final long _tmpMaterialTypeId;
            _tmpMaterialTypeId = _cursor.getLong(_cursorIndexOfMaterialTypeId);
            final Long _tmpDimensionId;
            if (_cursor.isNull(_cursorIndexOfDimensionId)) {
              _tmpDimensionId = null;
            } else {
              _tmpDimensionId = _cursor.getLong(_cursorIndexOfDimensionId);
            }
            _item = new ProductMaterialDimension(_tmpProductId,_tmpMaterialTypeId,_tmpDimensionId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProductHardware>> getHardwareForProduct(final long productId) {
    final String _sql = "SELECT * FROM product_hardware WHERE productId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"product_hardware"}, new Callable<List<ProductHardware>>() {
      @Override
      @NonNull
      public List<ProductHardware> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfPriceEach = CursorUtil.getColumnIndexOrThrow(_cursor, "priceEach");
          final List<ProductHardware> _result = new ArrayList<ProductHardware>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductHardware _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpPriceEach;
            _tmpPriceEach = _cursor.getDouble(_cursorIndexOfPriceEach);
            _item = new ProductHardware(_tmpId,_tmpProductId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpQuantity,_tmpPriceEach);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getHardwareOnce(final long productId,
      final Continuation<? super List<ProductHardware>> $completion) {
    final String _sql = "SELECT * FROM product_hardware WHERE productId = ? ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProductHardware>>() {
      @Override
      @NonNull
      public List<ProductHardware> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfNameKn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameKn");
          final int _cursorIndexOfNameHi = CursorUtil.getColumnIndexOrThrow(_cursor, "nameHi");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfPriceEach = CursorUtil.getColumnIndexOrThrow(_cursor, "priceEach");
          final List<ProductHardware> _result = new ArrayList<ProductHardware>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductHardware _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpNameEn;
            if (_cursor.isNull(_cursorIndexOfNameEn)) {
              _tmpNameEn = null;
            } else {
              _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            }
            final String _tmpNameKn;
            if (_cursor.isNull(_cursorIndexOfNameKn)) {
              _tmpNameKn = null;
            } else {
              _tmpNameKn = _cursor.getString(_cursorIndexOfNameKn);
            }
            final String _tmpNameHi;
            if (_cursor.isNull(_cursorIndexOfNameHi)) {
              _tmpNameHi = null;
            } else {
              _tmpNameHi = _cursor.getString(_cursorIndexOfNameHi);
            }
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpPriceEach;
            _tmpPriceEach = _cursor.getDouble(_cursorIndexOfPriceEach);
            _item = new ProductHardware(_tmpId,_tmpProductId,_tmpNameEn,_tmpNameKn,_tmpNameHi,_tmpQuantity,_tmpPriceEach);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MaterialWithDimension>> getMaterialBreakdownForProduct(final long productId) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            pm.materialTypeId,\n"
            + "            t.nameEn   AS materialNameEn,\n"
            + "            t.nameKn   AS materialNameKn,\n"
            + "            t.nameHi   AS materialNameHi,\n"
            + "            t.defaultPrice AS pricePerUnit,\n"
            + "            t.materialKind,\n"
            + "            t.isPlywood,\n"
            + "            pmd.dimensionId,\n"
            + "            d.label    AS dimensionLabel,\n"
            + "            d.value    AS dimensionValue,\n"
            + "            d.unit     AS dimensionUnit,\n"
            + "            d.dimensionKind\n"
            + "        FROM product_materials pm\n"
            + "        INNER JOIN raw_material_types t    ON t.id  = pm.materialTypeId\n"
            + "        LEFT  JOIN product_material_dimensions pmd\n"
            + "                   ON pmd.productId = pm.productId AND pmd.materialTypeId = pm.materialTypeId\n"
            + "        LEFT  JOIN product_dimensions d    ON d.id  = pmd.dimensionId\n"
            + "        WHERE pm.productId = ?\n"
            + "        ORDER BY t.categoryId ASC, t.id ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"product_materials",
        "raw_material_types", "product_material_dimensions",
        "product_dimensions"}, new Callable<List<MaterialWithDimension>>() {
      @Override
      @NonNull
      public List<MaterialWithDimension> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMaterialTypeId = 0;
          final int _cursorIndexOfMaterialNameEn = 1;
          final int _cursorIndexOfMaterialNameKn = 2;
          final int _cursorIndexOfMaterialNameHi = 3;
          final int _cursorIndexOfPricePerUnit = 4;
          final int _cursorIndexOfMaterialKind = 5;
          final int _cursorIndexOfIsPlywood = 6;
          final int _cursorIndexOfDimensionId = 7;
          final int _cursorIndexOfDimensionLabel = 8;
          final int _cursorIndexOfDimensionValue = 9;
          final int _cursorIndexOfDimensionUnit = 10;
          final int _cursorIndexOfDimensionKind = 11;
          final List<MaterialWithDimension> _result = new ArrayList<MaterialWithDimension>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MaterialWithDimension _item;
            final long _tmpMaterialTypeId;
            _tmpMaterialTypeId = _cursor.getLong(_cursorIndexOfMaterialTypeId);
            final String _tmpMaterialNameEn;
            if (_cursor.isNull(_cursorIndexOfMaterialNameEn)) {
              _tmpMaterialNameEn = null;
            } else {
              _tmpMaterialNameEn = _cursor.getString(_cursorIndexOfMaterialNameEn);
            }
            final String _tmpMaterialNameKn;
            if (_cursor.isNull(_cursorIndexOfMaterialNameKn)) {
              _tmpMaterialNameKn = null;
            } else {
              _tmpMaterialNameKn = _cursor.getString(_cursorIndexOfMaterialNameKn);
            }
            final String _tmpMaterialNameHi;
            if (_cursor.isNull(_cursorIndexOfMaterialNameHi)) {
              _tmpMaterialNameHi = null;
            } else {
              _tmpMaterialNameHi = _cursor.getString(_cursorIndexOfMaterialNameHi);
            }
            final double _tmpPricePerUnit;
            _tmpPricePerUnit = _cursor.getDouble(_cursorIndexOfPricePerUnit);
            final String _tmpMaterialKind;
            if (_cursor.isNull(_cursorIndexOfMaterialKind)) {
              _tmpMaterialKind = null;
            } else {
              _tmpMaterialKind = _cursor.getString(_cursorIndexOfMaterialKind);
            }
            final boolean _tmpIsPlywood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPlywood);
            _tmpIsPlywood = _tmp != 0;
            final Long _tmpDimensionId;
            if (_cursor.isNull(_cursorIndexOfDimensionId)) {
              _tmpDimensionId = null;
            } else {
              _tmpDimensionId = _cursor.getLong(_cursorIndexOfDimensionId);
            }
            final String _tmpDimensionLabel;
            if (_cursor.isNull(_cursorIndexOfDimensionLabel)) {
              _tmpDimensionLabel = null;
            } else {
              _tmpDimensionLabel = _cursor.getString(_cursorIndexOfDimensionLabel);
            }
            final Double _tmpDimensionValue;
            if (_cursor.isNull(_cursorIndexOfDimensionValue)) {
              _tmpDimensionValue = null;
            } else {
              _tmpDimensionValue = _cursor.getDouble(_cursorIndexOfDimensionValue);
            }
            final String _tmpDimensionUnit;
            if (_cursor.isNull(_cursorIndexOfDimensionUnit)) {
              _tmpDimensionUnit = null;
            } else {
              _tmpDimensionUnit = _cursor.getString(_cursorIndexOfDimensionUnit);
            }
            final String _tmpDimensionKind;
            if (_cursor.isNull(_cursorIndexOfDimensionKind)) {
              _tmpDimensionKind = null;
            } else {
              _tmpDimensionKind = _cursor.getString(_cursorIndexOfDimensionKind);
            }
            _item = new MaterialWithDimension(_tmpMaterialTypeId,_tmpMaterialNameEn,_tmpMaterialNameKn,_tmpMaterialNameHi,_tmpPricePerUnit,_tmpMaterialKind,_tmpIsPlywood,_tmpDimensionId,_tmpDimensionLabel,_tmpDimensionValue,_tmpDimensionUnit,_tmpDimensionKind);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Quote>> getAllQuotes() {
    final String _sql = "SELECT * FROM quotes ORDER BY deliveryDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quotes"}, new Callable<List<Quote>>() {
      @Override
      @NonNull
      public List<Quote> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "productNameEn");
          final int _cursorIndexOfBuyerName = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerName");
          final int _cursorIndexOfDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryDate");
          final int _cursorIndexOfMaterialTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "materialTotal");
          final int _cursorIndexOfLabourAdjustmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "labourAdjustmentType");
          final int _cursorIndexOfLabourAdjustmentValue = CursorUtil.getColumnIndexOrThrow(_cursor, "labourAdjustmentValue");
          final int _cursorIndexOfGrandTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "grandTotal");
          final int _cursorIndexOfSnapshotJson = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Quote _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpProductNameEn;
            if (_cursor.isNull(_cursorIndexOfProductNameEn)) {
              _tmpProductNameEn = null;
            } else {
              _tmpProductNameEn = _cursor.getString(_cursorIndexOfProductNameEn);
            }
            final String _tmpBuyerName;
            if (_cursor.isNull(_cursorIndexOfBuyerName)) {
              _tmpBuyerName = null;
            } else {
              _tmpBuyerName = _cursor.getString(_cursorIndexOfBuyerName);
            }
            final String _tmpDeliveryDate;
            if (_cursor.isNull(_cursorIndexOfDeliveryDate)) {
              _tmpDeliveryDate = null;
            } else {
              _tmpDeliveryDate = _cursor.getString(_cursorIndexOfDeliveryDate);
            }
            final double _tmpMaterialTotal;
            _tmpMaterialTotal = _cursor.getDouble(_cursorIndexOfMaterialTotal);
            final String _tmpLabourAdjustmentType;
            if (_cursor.isNull(_cursorIndexOfLabourAdjustmentType)) {
              _tmpLabourAdjustmentType = null;
            } else {
              _tmpLabourAdjustmentType = _cursor.getString(_cursorIndexOfLabourAdjustmentType);
            }
            final double _tmpLabourAdjustmentValue;
            _tmpLabourAdjustmentValue = _cursor.getDouble(_cursorIndexOfLabourAdjustmentValue);
            final double _tmpGrandTotal;
            _tmpGrandTotal = _cursor.getDouble(_cursorIndexOfGrandTotal);
            final String _tmpSnapshotJson;
            if (_cursor.isNull(_cursorIndexOfSnapshotJson)) {
              _tmpSnapshotJson = null;
            } else {
              _tmpSnapshotJson = _cursor.getString(_cursorIndexOfSnapshotJson);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Quote(_tmpId,_tmpProductId,_tmpProductNameEn,_tmpBuyerName,_tmpDeliveryDate,_tmpMaterialTotal,_tmpLabourAdjustmentType,_tmpLabourAdjustmentValue,_tmpGrandTotal,_tmpSnapshotJson,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
