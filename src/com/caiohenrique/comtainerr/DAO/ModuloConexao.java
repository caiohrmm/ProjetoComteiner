/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.caiohenrique.comtainerr.DAO;
import java.sql.*;
/**
 *
 * @author cahen
 */
public class ModuloConexao {
    // Conexão com o MySQL
    public static Connection conector(){
        // Crio uma váriavel para receber a conexão, no entanto, no momento nenhuma conexao está estabelecida.
        Connection conexao = null;
        
        
        // Armazenando infos do banco de dados ->
        String url = "jdbc:mysql://26.185.31.219/comtainer?characterEncoding=utf-8";
        String user = "caioh";
        String password = "caiohenrique@12345";
        
        // Estabelecendo conexão com o banco
        // Preciso tratar as excessões, pois caso aconteça alguma coisa o meu sistema não pode simplesmente parar de funcionar
        // Uso try catch
        // O try será executado caso der tudo certo, ele tenta estabelecer, estabelecendo ele executa o código.
        // O catch reporta o erro para gente.
        // Class.forName -> Chama o driver
        // conexao recebe todos os valores do nosso banco e tenta estabelecer a conexao com o sistema, em seguida retorno a variavel
        try {
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
}
