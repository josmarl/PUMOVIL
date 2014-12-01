package union.union_vr1.Conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import union.union_vr1.Sqlite.DbAdapter_Agente;
import union.union_vr1.Sqlite.DbAdapter_Comprob_Cobro;
import union.union_vr1.Sqlite.DbAdaptert_Evento_Establec;
import union.union_vr1.Sqlite.DbAdapter_Tipo_Gasto;
import union.union_vr1.Sqlite.DbAdapter_Informe_Gastos;
import union.union_vr1.Sqlite.DbAdapter_Precio;
import union.union_vr1.Sqlite.DbAdapter_Stock_Agente;
import union.union_vr1.Sqlite.DbAdapter_Comprob_Venta;
import union.union_vr1.Sqlite.DbAdapter_Comprob_Venta_Detalle;
import union.union_vr1.Sqlite.DbAdapter_Histo_Venta;
import union.union_vr1.Sqlite.DbAdapter_Histo_Venta_Detalle;
/**
 * Created by USUARIO on 30/06/2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ProdUnionSXNHG.sqlite";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.w(DbAdaptert_Evento_Establec.TAG, DbAdaptert_Evento_Establec.CREATE_TABLE_EVENTO_ESTABLEC);
        db.execSQL(DbAdaptert_Evento_Establec.CREATE_TABLE_EVENTO_ESTABLEC);
        //Log.w(DbAdapter_Tipo_Gasto.TAG, DbAdapter_Tipo_Gasto.CREATE_TABLE_TIPO_GASTO);
        db.execSQL(DbAdapter_Tipo_Gasto.CREATE_TABLE_TIPO_GASTO);
        //Log.w(DbAdapter_Informe_Gastos.TAG, DbAdapter_Informe_Gastos.CREATE_TABLE_INFORME_GASTOS);
        db.execSQL(DbAdapter_Informe_Gastos.CREATE_TABLE_INFORME_GASTOS);
        //Log.w(DbAdapter_Precio.TAG, DbAdapter_Precio.CREATE_TABLE_PRECIO);
        db.execSQL(DbAdapter_Precio.CREATE_TABLE_PRECIO);
        //Log.w(DbAdapter_Stock_Agente.TAG, DbAdapter_Stock_Agente.CREATE_TABLE_STOCK_AGENTE);
        db.execSQL(DbAdapter_Stock_Agente.CREATE_TABLE_STOCK_AGENTE);
        //Log.w(DbAdapter_Comprob_Venta.TAG, DbAdapter_Comprob_Venta.CREATE_TABLE_COMPROB_VENTA);
        db.execSQL(DbAdapter_Comprob_Venta.CREATE_TABLE_COMPROB_VENTA);
        //Log.w(DbAdapter_Comprob_Venta_Detalle.TAG, DbAdapter_Comprob_Venta_Detalle.CREATE_TABLE_COMPROB_VENTA_DETALLE);
        db.execSQL(DbAdapter_Comprob_Venta_Detalle.CREATE_TABLE_COMPROB_VENTA_DETALLE);
        //Log.w(DbAdaptert_Agente.TAG, DbAdaptert_Agente.CREATE_TABLE_AGENTE);
        db.execSQL(DbAdapter_Agente.CREATE_TABLE_AGENTE);
        //Log.w(DbAdapter_Comprob_Cobro.TAG, DbAdapter_Comprob_Cobro.CREATE_TABLE_COMPROB_COBRO);
        db.execSQL(DbAdapter_Comprob_Cobro.CREATE_TABLE_COMPROB_COBRO);
        //Log.w(DbAdapter_Histo_Venta.TAG, DbAdapter_Histo_Venta.CREATE_TABLE_HISTO_VENTA);
        db.execSQL(DbAdapter_Histo_Venta.CREATE_TABLE_HISTO_VENTA);
        //Log.w(DbAdapter_Histo_Venta_Detalle.TAG, DbAdapter_Histo_Venta_Detalle.CREATE_TABLE_HISTO_VENTA_DETALLE);
        db.execSQL(DbAdapter_Histo_Venta_Detalle.CREATE_TABLE_HISTO_VENTA_DETALLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Log.w(DbAdaptert_Evento_Establec.TAG, "Upgrading database from version " + oldVersion + " to "
        //        + newVersion + ", which will destroy all old data");
        db.execSQL(DbAdaptert_Evento_Establec.DELETE_TABLE_EVENTO_ESTABLEC);
        db.execSQL(DbAdapter_Tipo_Gasto.DELETE_TABLE_TIPO_GASTO);
        db.execSQL(DbAdapter_Informe_Gastos.DELETE_TABLE_INFORME_GASTOS);
        db.execSQL(DbAdapter_Precio.DELETE_TABLE_PRECIO);
        db.execSQL(DbAdapter_Stock_Agente.DELETE_TABLE_STOCK_AGENTE);
        db.execSQL(DbAdapter_Comprob_Venta.DELETE_TABLE_COMPROB_VENTA);
        db.execSQL(DbAdapter_Comprob_Venta_Detalle.DELETE_TABLE_COMPROB_VENTA_DETALLE);
        db.execSQL(DbAdapter_Agente.DELETE_TABLE_AGENTE);
        db.execSQL(DbAdapter_Comprob_Cobro.DELETE_TABLE_COMPROB_COBRO);
        db.execSQL(DbAdapter_Histo_Venta.DELETE_TABLE_HISTO_VENTA);
        db.execSQL(DbAdapter_Histo_Venta_Detalle.DELETE_TABLE_HISTO_VENTA_DETALLE);
        onCreate(db);
    }
}
