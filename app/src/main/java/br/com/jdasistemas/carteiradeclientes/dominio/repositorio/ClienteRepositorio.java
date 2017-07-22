package br.com.jdasistemas.carteiradeclientes.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.jdasistemas.carteiradeclientes.dominio.entidade.Cliente;

/**
 * Created by Jader on 19/07/2017.
 */

public class ClienteRepositorio {

    private SQLiteDatabase conexao;

    public ClienteRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }
    public void inserir(Cliente cliente){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME",cliente.nome);
        contentValues.put("ENDERECO",cliente.endereco);
        contentValues.put("EMAIL",cliente.email);
        contentValues.put("TELEFONE",cliente.telefone);

        conexao.insertOrThrow("CLIENTE",null,contentValues);
    }

    public void excluir(int codigo){

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(codigo);
        conexao.delete("CLIENTE","CODIGO = ?",paramentros);

    }

    public void alterar(Cliente cliente){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME",cliente.nome);
        contentValues.put("ENDERECO",cliente.endereco);
        contentValues.put("EMAIL",cliente.email);
        contentValues.put("TELEFONE",cliente.telefone);

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(cliente.codigo);

        conexao.update("CLIENTE",contentValues,"CODIGO = ?",paramentros);
    }

    public List<Cliente> buscarTodos(){

        List<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO,NOME,ENDERECO,EMAIL,TELEFONE ");
        sql.append(" FROM CLIENTE ");
        Cursor resultado = conexao.rawQuery(sql.toString(), null);
        if (resultado.getCount() >0){
            resultado.moveToFirst();

            do{
                Cliente cli = new Cliente();
                cli.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.nome = resultado.getString(resultado.getColumnIndexOrThrow("NOME"));
                cli.endereco = resultado.getString(resultado.getColumnIndexOrThrow("ENDERECO"));
                cli.email = resultado.getString(resultado.getColumnIndexOrThrow("EMAIL"));
                cli.telefone = resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE"));

            }while(resultado.moveToNext());
        }
        return clientes;
    }

    public Cliente buscarCliente(int codigo){
        Cliente cliente = new Cliente();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CODIGO,NOME,ENDERECO,EMAIL,TELEFONE ");
        sql.append(" FROM CLIENTE ");
        sql.append(" WHERE CODIGO = ? ");

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), paramentros);

        if (resultado.getCount() >0) {
            resultado.moveToFirst();

            cliente.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            cliente.nome = resultado.getString(resultado.getColumnIndexOrThrow("NOME"));
            cliente.endereco = resultado.getString(resultado.getColumnIndexOrThrow("ENDERECO"));
            cliente.email = resultado.getString(resultado.getColumnIndexOrThrow("EMAIL"));
            cliente.telefone = resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE"));

            return  cliente;
        }
        return null;
    }


}
