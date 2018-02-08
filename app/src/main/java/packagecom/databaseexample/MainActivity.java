package packagecom.databaseexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private OpenHelper openHelper;
    EditText flo, telph, money, dare;
    TextView showData;
    CheckBox debtOrCredit;
    Button writrData, showAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writrData = (Button) findViewById(R.id.showButton );
        showAll = (Button) findViewById(R.id.showButton1);

        writrData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelper = new OpenHelper(getBaseContext());
                SQLiteDatabase sdb;
                sdb = openHelper.getWritableDatabase();
                flo = (EditText) findViewById(R.id.name);
                telph = (EditText) findViewById(R.id.teieph);
                money = (EditText) findViewById(R.id.money);
                //dare = (EditText) findViewById(R.id.dateDebt);
                String fioStr = flo.getText().toString();
                String dateStr = dare.getText().toString();
                long tel =Long.parseLong(telph.getText().toString());
                double moneyField = Double.parseDouble(money.getText().toString());
                debtOrCredit = (CheckBox) findViewById(R.id.debt_create);

                int i = 0;
                if (debtOrCredit.isChecked()) i=1;
                String insertQuery = "INSERT INTO "+OpenHelper.TABEL_NAME+ "( "+
                        OpenHelper.FIO_COLUMN+ ", "+OpenHelper.MONEY_COLUMN+", "+OpenHelper.TELEPHONE_COLUMN+", "
                        +OpenHelper.DEBT_CREDIT_COLUMN+" , "+
                        OpenHelper.DATE_COLUMN+ " ) VALUES ( \""+
                        fioStr+"\", "+moneyField+", "+tel+" , " +i+ ", \""+dateStr+"\");";
                flo.append("Hello");
                sdb.execSQL(insertQuery);
                sdb.close();

            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelper = new OpenHelper(getBaseContext());
                SQLiteDatabase sdb;
                sdb = openHelper.getReadableDatabase();
                String queryStr = "SELECT * FROM "+OpenHelper.TABEL_NAME+" WHERE "+OpenHelper.DATE_COLUMN+"=="+sdb+";";
                Cursor cursor = sdb.rawQuery(queryStr,null);
                showData = (TextView) findViewById(R.id.showData);
                showData.setText(" ");
                showData.setTextColor(Color.RED);
                showData.setTextSize(24.0f);
                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    String fioStr = cursor.getString(cursor.getColumnIndex(OpenHelper.FIO_COLUMN));
                    String dataStr = cursor.getString(cursor.getColumnIndex(OpenHelper.DATE_COLUMN));
                    long tel = cursor.getLong(cursor.getColumnIndex(OpenHelper.TELEPHONE_COLUMN));
                    double moneyDebt = cursor.getDouble(cursor.getColumnIndex(OpenHelper.MONEY_COLUMN));
                    int debtCredit = cursor.getInt(cursor.getColumnIndex(OpenHelper.DEBT_CREDIT_COLUMN));
                    if (debtCredit ==0)
                         showData.append(fioStr+" Должен "+ Double.toString(moneyDebt)+
                                 " p. Телефон: "+ Long.toString(tel)+", Взял: "+dataStr+"\n");
                    else showData.append(" Должен "+fioStr+"  "+ Double.toString(moneyDebt)+
                            " p. Телефон: "+ Long.toString(tel)+", Взял: "+dataStr+"\n");
                }
                cursor.close();
                sdb.close();
            }
        });

        openHelper = new OpenHelper(this);
        SQLiteDatabase sdb;
        sdb = openHelper.getReadableDatabase();


    }
}
