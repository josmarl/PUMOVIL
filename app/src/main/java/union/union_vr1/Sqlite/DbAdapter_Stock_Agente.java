package union.union_vr1.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import union.union_vr1.Conexion.DbHelper;

public class DbAdapter_Stock_Agente {

    public static final String ST_id_stock_agente = "_id";
    public static final String ST_id_producto = "st_in_id_producto";
    public static final String ST_nombre = "st_te_nombre";
    public static final String ST_codigo = "st_te_codigo";
    public static final String ST_codigo_barras = "st_te_codigo_barras";
    public static final String ST_inicial = "st_in_inicial";
    public static final String ST_final = "st_in_final";
    public static final String ST_disponible = "st_in_disponible";
    public static final String ST_ventas = "st_in_ventas";
    public static final String ST_canjes = "st_in_canjes";
    public static final String ST_devoluciones = "st_in_devoluciones";
    public static final String ST_buenos = "st_in_buenos";
    public static final String ST_malos = "st_in_malos";
    public static final String ST_id_agente = "st_in_id_agente";

    public static final String TAG = "Stock_Agente";
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    //private static final int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "ProdUnion.sqlite";
    private static final String SQLITE_TABLE_Stock_Agente = "m_stock_agente";
    private final Context mCtx;

    public static final String CREATE_TABLE_STOCK_AGENTE =
            "create table "+SQLITE_TABLE_Stock_Agente+" ("
                    +ST_id_stock_agente+" integer primary key autoincrement,"
                    +ST_id_producto+" integer,"
                    +ST_nombre+" text,"
                    +ST_codigo+" text,"
                    +ST_codigo_barras+" text,"
                    +ST_inicial+" integer,"
                    +ST_final+" integer,"
                    +ST_disponible+" integer,"
                    +ST_ventas+" integer,"
                    +ST_canjes+" integer,"
                    +ST_devoluciones+" integer,"
                    +ST_buenos+" integer,"
                    +ST_malos+" integer,"
                    +ST_id_agente+" integer);";

    public static final String DELETE_TABLE_STOCK_AGENTE = "DROP TABLE IF EXISTS " + SQLITE_TABLE_Stock_Agente;

    public DbAdapter_Stock_Agente(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter_Stock_Agente open() throws SQLException {
        mDbHelper = new DbHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createStockAgente(
            int id_producto, String nombre, String codigo, String codigo_barras, int inicial,
            int finals, int disponible, int ventas, int canjes, int devoluciones,
            int buenos, int malos, int id_agente){

        ContentValues initialValues = new ContentValues();
        initialValues.put(ST_id_producto,id_producto);
        initialValues.put(ST_nombre,nombre);
        initialValues.put(ST_codigo,codigo);
        initialValues.put(ST_codigo_barras,codigo_barras);
        initialValues.put(ST_inicial,inicial);
        initialValues.put(ST_final,finals);
        initialValues.put(ST_disponible,disponible);
        initialValues.put(ST_ventas,ventas);
        initialValues.put(ST_canjes,canjes);
        initialValues.put(ST_devoluciones,devoluciones);
        initialValues.put(ST_buenos,buenos);
        initialValues.put(ST_malos,malos);
        initialValues.put(ST_id_agente,id_agente);

        return mDb.insert(SQLITE_TABLE_Stock_Agente, null, initialValues);
    }

    public boolean deleteAllStockAgente() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE_Stock_Agente, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchStockAgenteByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE_Stock_Agente, new String[] {ST_id_stock_agente,
                            ST_id_producto, ST_nombre, ST_codigo, ST_codigo_barras, ST_disponible},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE_Stock_Agente, new String[] {ST_id_stock_agente,
                            ST_id_producto, ST_nombre, ST_codigo, ST_codigo_barras, ST_disponible},
                    ST_nombre + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchStockAgentePrecioByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                    DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                    ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                    DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = 1", null, null);

        }
        else {
            mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                    DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                    ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                    DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = 1 AND " +
                    ST_nombre + " like '%" + inputText + "%'", null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchStockAgentePrecioByNameEst(String inputText, String inputText2) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                    DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                    ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                    DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = " +
                    inputText, null, null);

        }
        else {
            mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                    DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                    ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                    DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = " +
                    inputText2 + " AND " + ST_nombre + " like '%" + inputText + "%'", null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllStockAgente() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_Stock_Agente, new String[] {ST_id_stock_agente,
                        ST_id_producto, ST_nombre, ST_codigo, ST_disponible},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllStockAgentePrecio() {

        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = 1", null, null);

        //Cursor mCursor = mDb.rawQuery("SELECT * FROM m_stock_agente INNER JOIN m_precio" +
        //        " ON m_stock_agente.st_in_id_producto = m_precio.pr_in_id_producto", null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllStockAgentePrecioEst(String inputText) {

        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE_Stock_Agente + " INNER JOIN " +
                DbAdapter_Precio.SQLITE_TABLE_Precio + " ON " + SQLITE_TABLE_Stock_Agente + "." +
                ST_id_producto + " = " + DbAdapter_Precio.SQLITE_TABLE_Precio + "." +
                DbAdapter_Precio.PR_id_producto + " WHERE " + DbAdapter_Precio.PR_id_cat_est + " = " +
                inputText, null, null);

        //Cursor mCursor = mDb.rawQuery("SELECT * FROM m_stock_agente INNER JOIN m_precio" +
        //        " ON m_stock_agente.st_in_id_producto = m_precio.pr_in_id_producto", null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllStockAgenteVentas() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_Stock_Agente, new String[] {ST_id_stock_agente,
                        ST_codigo,ST_nombre,ST_inicial,ST_final,ST_ventas,ST_devoluciones,ST_canjes,ST_buenos,ST_malos,ST_disponible},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeStockAgente() {

        createStockAgente( 1, "PANs", "1A", "11", 10, 0, 10 , 0, 0, 0, 0, 0, 1);
        createStockAgente( 2, "AGUA", "2A", "22", 20, 0, 20 , 0, 0, 0, 0, 0, 1);
        createStockAgente( 3, "JUGO", "3A", "33", 30, 0, 30 , 0, 0, 0, 0, 0, 1);
        createStockAgente( 4, "PASTEL", "4A", "44", 10, 0, 40 , 0, 0, 0, 0, 0, 1);
        createStockAgente( 5, "PANETON", "5A", "55", 10, 0, 50 , 0, 0, 0, 0, 0, 1);

    }

}