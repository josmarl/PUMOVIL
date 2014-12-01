package union.union_vr1.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import union.union_vr1.Conexion.DbHelper;

public class DbAdapter_Informe_Gastos {

    public static final String GA_id_gasto = "_id";
    public static final String GA_id_tipo_gasto = "ga_in_id_tipo_gasto";
    public static final String GA_id_proced_gasto = "ga_in_id_proced_gasto";
    public static final String GA_id_tipo_doc = "ga_in_id_tipo_doc";
    public static final String GA_nom_tipo_gasto = "ga_te_nom_tipo_gasto";
    public static final String GA_subtotal = "ga_re_subtotal";
    public static final String GA_igv = "ga_re_igv";
    public static final String GA_total = "ga_re_total";
    public static final String GA_fecha = "ga_te_fecha";
    public static final String GA_hora = "ga_te_hora";
    public static final String GA_estado = "ga_int_estado";
    public static final String GA_id_agente = "ga_in_id_agente";

    public static final String TAG = "Informe_Gastos";
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    //private static final int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "ProdUnion.sqlite";
    private static final String SQLITE_TABLE_Informe_Gastos = "m_informe_gastos";
    private final Context mCtx;

    public static final String CREATE_TABLE_INFORME_GASTOS =
            "create table "+SQLITE_TABLE_Informe_Gastos+" ("
                    +GA_id_gasto+" integer primary key autoincrement,"
                    +GA_id_tipo_gasto+" integer,"
                    +GA_id_proced_gasto+" integer,"
                    +GA_id_tipo_doc+" integer,"
                    +GA_nom_tipo_gasto+" text,"
                    +GA_subtotal+" real,"
                    +GA_igv+" real,"
                    +GA_total+" real,"
                    +GA_fecha+" text,"
                    +GA_hora+" text,"
                    +GA_estado+" integer,"
                    +GA_id_agente+" integer);";

    public static final String DELETE_TABLE_INFORME_GASTOS = "DROP TABLE IF EXISTS " + SQLITE_TABLE_Informe_Gastos;

    public DbAdapter_Informe_Gastos(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter_Informe_Gastos open() throws SQLException {
        mDbHelper = new DbHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createInformeGastos(
            int id_tipo_gasto, int id_procedencia_gasto, int id_tipo_doc, String nom_tipo_gasto,
            double subtotal, double igv, double total, String fecha, String hora, int estado,
            int id_agente) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(GA_id_tipo_gasto,id_tipo_gasto);
        initialValues.put(GA_id_proced_gasto,id_procedencia_gasto);
        initialValues.put(GA_id_tipo_doc,id_tipo_doc);
        initialValues.put(GA_nom_tipo_gasto,nom_tipo_gasto);
        initialValues.put(GA_subtotal,subtotal);
        initialValues.put(GA_igv,igv);
        initialValues.put(GA_total,total);
        initialValues.put(GA_fecha,fecha);
        initialValues.put(GA_hora,hora);
        initialValues.put(GA_estado,estado);
        initialValues.put(GA_id_agente,id_agente);

        return mDb.insert(SQLITE_TABLE_Informe_Gastos, null, initialValues);
    }

    public boolean deleteAllInformeGastos() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE_Informe_Gastos, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchInformeGastosByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE_Informe_Gastos, new String[] {GA_id_gasto,
                            GA_nom_tipo_gasto, GA_subtotal, GA_igv, GA_total},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE_Informe_Gastos, new String[] {GA_id_gasto,
                            GA_nom_tipo_gasto, GA_subtotal, GA_igv, GA_total},
                    GA_nom_tipo_gasto + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllInformeGastos() {

        Cursor mCursor = mDb.query(SQLITE_TABLE_Informe_Gastos, new String[] {GA_id_gasto,
                        GA_nom_tipo_gasto, GA_subtotal, GA_igv, GA_total, GA_fecha},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeInformeGastos() {

        createInformeGastos(1, 1, 1, "COMBUSTIBLE", 10.5, 1.0, 11.5, "2014-11-12", "08:10:00", 1, 1);
        createInformeGastos(2, 2, 2, "COMIDA", 20.5, 2.0, 22.5, "2014-11-12", "08:10:00", 1, 1);
        createInformeGastos(3, 3, 3, "DEPARTAMENTO", 30.5, 3.0, 33.5, "2014-11-12", "08:10:00", 1, 1);
        createInformeGastos(4, 4, 4, "VIAJE", 40.5, 4.0, 44.5, "2014-11-12", "08:10:00", 1, 1);
        createInformeGastos(5, 5, 5, "NUEVO", 50.5, 5.0, 55.5, "2014-11-12", "08:10:00", 1, 1);

    }

}