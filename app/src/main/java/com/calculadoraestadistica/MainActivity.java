package com.calculadoraestadistica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    Calculadora calculadora;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Button btnmuestra, btndato;
    private ImageButton btnreset;
    private EditText txtmuestra, txtdato;
    private TextView txtdatos;
    private LinkedList<Double> listMuestra;
    private int valorMuestra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // instancias
        calculadora = new Calculadora();
        listMuestra = new LinkedList<>();

        btnreset = findViewById(R.id.btnreset);
        btnmuestra = findViewById(R.id.btnmuestra);
        btndato = findViewById(R.id.btndato);
        txtmuestra = findViewById(R.id.txtmuestra);
        txtdato = findViewById(R.id.txtdato);
        txtdatos = findViewById(R.id.txtdatos);

        // Eventos botones
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValores();
                Toast.makeText(MainActivity.this, "valores restablecidos", Toast.LENGTH_SHORT).show();
            }
        });

        // enter boton muestra
        btnmuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtmuestra.length() > 0) {
                    //Asignar valores e instanciar muestras
                    valorMuestra = Integer.valueOf(txtmuestra.getText().toString());
                    if (valorMuestra > 0) {
                        btnmuestra.setEnabled(false);
                        txtmuestra.setEnabled(false);
                        btndato.setEnabled(true);
                        txtdato.setEnabled(true);
                    } else {
                        resetValores();
                        Toast.makeText(MainActivity.this, "Ingrese una muestra mayor a 0", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese una muestra antes de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // enter desd teclado texto muestra

        txtmuestra.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    if (txtmuestra.length() > 0) {
                        //Asignar valores e instanciar muestras
                        valorMuestra = Integer.valueOf(txtmuestra.getText().toString());
                        if (valorMuestra > 0) {
                            btnmuestra.setEnabled(false);
                            txtmuestra.setEnabled(false);
                            btndato.setEnabled(true);
                            txtdato.setEnabled(true);
                        } else {
                            resetValores();
                            Toast.makeText(MainActivity.this, "Ingrese una muestra mayor a 0", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Ingrese una muestra antes de continuar", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;            }
        });

        //Enter dato boton
        btndato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtdato.length() > 0) {
                    double dato = Double.valueOf(txtdato.getText().toString());
                    Log.e("DATO", "onClick: " + dato);
                    listMuestra.add(dato); //[6, 5]
                    txtdatos.setText(txtdatos.getText().toString().length() > 0 ? txtdatos.getText().toString().concat("   " + dato) : txtdatos.getText().toString().concat("" + dato));
                    txtdato.setText("");
                    if (listMuestra.size() == valorMuestra) {
                        btndato.setEnabled(false);
                        txtdato.setEnabled(false);
                        Log.e("DATO", "onClick: " + listMuestra.size() + " ==  " + valorMuestra);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mostrarAlert(calculadora.media(listMuestra), calculadora.mediana(listMuestra), calculadora.standardDeviation(listMuestra));
                            }
                        });
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un valor antes de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Enter dato cuadro de texto
        txtdato.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if (txtdato.length() > 0) {
                        double dato = Double.valueOf(txtdato.getText().toString());
                        Log.e("DATO", "onClick: " + dato);
                        listMuestra.add(dato);
                        txtdatos.setText(txtdatos.getText().toString().length() > 0 ? txtdatos.getText().toString().concat("   " + dato) : txtdatos.getText().toString().concat("" + dato));
                        txtdato.setText("");
                        if (listMuestra.size() == valorMuestra) {
                            btndato.setEnabled(false);
                            txtdato.setEnabled(false);
                            Log.e("DATO", "onClick: " + listMuestra.size() + " ==  " + valorMuestra);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mostrarAlert(calculadora.media(listMuestra), calculadora.mediana(listMuestra), calculadora.standardDeviation(listMuestra));
                                }
                            });
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Ingrese un valor antes de continuar", Toast.LENGTH_SHORT).show();
                    }
                    return  true;
                }
                return false;
            }
        });
    }

    void resetValores() {
        btnmuestra.setEnabled(true);
        txtmuestra.setEnabled(true);
        btndato.setEnabled(false);
        txtdato.setEnabled(false);
        //
        txtmuestra.setText("");
        txtdato.setText("");
        txtdatos.setText("");
        //
        valorMuestra = 0;
        listMuestra = new LinkedList<>();
    }

    private void mostrarAlert(double media, double mediana, double desviacionEstandar) {
        TextView txtmedia, txtmediana, txtdesviacion;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = (MainActivity.this).getLayoutInflater();
        builder.setTitle("DATOS ESTADISTICOS");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_logo_alert);
        View dialogView = inflater.inflate(R.layout.layyout_alert, null);
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                resetValores();
                            }
                        }
                );
        txtmedia = dialogView.findViewById(R.id.txtmedia);
        txtmediana = dialogView.findViewById(R.id.txtmediana);
        txtdesviacion = dialogView.findViewById(R.id.txtdesviacionestandar);

        txtmedia.setText(media + "");
        txtmediana.setText(mediana + "");
        txtdesviacion.setText(desviacionEstandar + "");
        builder.create();
        builder.show();
    }

    private void mostrarDialogInfo(String titulo, String mensaje) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensaje);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.e("INFO", "onNavigationItemSelected: " + menuItem.getItemId());
//        int title;
        switch (menuItem.getItemId()) {
            case R.id.nav_ceditos:
//                title = R.string.menu_creditos;
                drawerLayout.closeDrawer(GravityCompat.START);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mostrarDialogInfo("Estadística", "La materia de Estadística nos puso como meta la realización de un proyecto abierto que nos permite agrandar nuevos conocimientos y poner las habilidades de los estudiantes en práctica");
                    }
                });
                break;
            case R.id.nav_close:
//                title = R.string.menu_close;
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
//        setTitle(getString(title));
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        //el drawer se ha abierto completamente
        Toast.makeText(this, getString(R.string.navigation_drawer_open),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }
}