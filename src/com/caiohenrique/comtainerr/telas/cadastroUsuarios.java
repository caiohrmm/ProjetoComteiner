/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.caiohenrique.comtainerr.telas;

import java.sql.*;
import com.caiohenrique.comtainerr.DAO.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author cahen
 */
public class cadastroUsuarios extends javax.swing.JInternalFrame {

    // Todo form que criar e quiser conectar com o sql farei o mesmo processo
    // Crio uma conexão nula no momento
    Connection conexao = null;
    // Manipulando instruções sql com PreparedStatement e ResultSet
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form cadastroUsuarios
     */
    public cadastroUsuarios() {
        initComponents();
        
        // Conectando ao banco de dados.
        conexao = ModuloConexao.conector();
    }

    private void Consultar() {
        // Selecione todos os atributos do usuario quando o id dele for ?
        String sql = "select * from usuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            // Prepara a conexão do número que foi digitado pelo usuario até o id do banco de dados.
            pst.setString(1, txtIdUsuario.getText());
            // Executando o comando
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNomeUsuario.setText(rs.getString(2)); // Nome do usuario está em segundo da fila no meu banco de dados.
                txtSenhaUsuario.setText(rs.getString(5));
                txtLoginUsuario.setText(rs.getString(4));
                txtTelefoneUsuario.setText(rs.getString(3));
                comboPerfil.setSelectedItem(rs.getString(6));
            } else {
                lblErro.setText("Não foi possível encontrar o usuário informado!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro no banco de dados.\n " + e);
            System.out.println(e);
        }

    }

    public void limparCampo() {
        txtIdUsuario.setText("");
        txtNomeUsuario.setText("");
        txtSenhaUsuario.setText("");
        txtLoginUsuario.setText("");
        txtTelefoneUsuario.setText("");
        lblErro.setText("");
    }

    // Adicionar usuários ao banco de dados.
    private void adicionar() {
        String sql = "INSERT INTO usuarios (iduser, usuario,fone, login, senha, perfil) VALUES (?,?,?,?,?,?)";
        try {
            // Preparando a conexão
            pst = conexao.prepareStatement(sql);
            // Adicionando os usuarios ao banco de dados
            pst.setString(1, txtIdUsuario.getText());
            pst.setString(2, txtNomeUsuario.getText());
            pst.setString(3, txtTelefoneUsuario.getText());
            pst.setString(4, txtLoginUsuario.getText());
            pst.setString(5, txtSenhaUsuario.getText());
            pst.setString(6, comboPerfil.getSelectedItem().toString());
            // A linha abaixo atualiza a tabela usuario com os dados do nosso formulario
            // Se o usuário for adicionado, será retornado o valor 1, caso contrário retornará 0

            if (!txtIdUsuario.getText().equals("") && !txtNomeUsuario.getText().equals("") && !txtLoginUsuario.getText().equals("") && !txtSenhaUsuario.getText().equals("") && !comboPerfil.getSelectedItem().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso !");
                    limparCampo();
                } else {
                    lblErro.setText("Não foi possível adicionar o usuário " + txtIdUsuario.getText() + " pois existe/m campo/s não preenchido/s");
                    limparCampo();
                }
            } else {
                lblErro.setText("Não foi possível adicionar esse usuário!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Usuário já existente no banco de dados!", "Não foi possível adicionar usuário !", JOptionPane.ERROR_MESSAGE);
            limparCampo();
        }
    }

    private void editar() {
        // Crio o código de update
        String sql = "UPDATE usuarios SET usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            // Preparando o código
            pst = conexao.prepareStatement(sql);
            // Adicionando os usuarios ao banco de dados
            pst.setString(1, txtNomeUsuario.getText());
            pst.setString(2, txtTelefoneUsuario.getText());
            pst.setString(3, txtLoginUsuario.getText());
            pst.setString(4, txtSenhaUsuario.getText());
            pst.setString(5, comboPerfil.getSelectedItem().toString());
            pst.setString(6, txtIdUsuario.getText());

            if (!txtIdUsuario.getText().equals("") && !txtNomeUsuario.getText().equals("") && !txtLoginUsuario.getText().equals("") && !txtSenhaUsuario.getText().equals("") && !comboPerfil.getSelectedItem().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Dados de usuário editado com sucesso !");
                    limparCampo();
                } else {
                    lblErro.setText("Não foi possível editar o usuário " + txtIdUsuario.getText() + " pois existe/m campo/s não preenchido/s");
                    limparCampo();
                }
            } else {
                lblErro.setText("Não foi possível editar o usuário " + txtIdUsuario.getText() + " pois existe/m campo/s não preenchido/s");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            limparCampo();
        }
    }

    private void deletar() {
        int deletar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o usuário " + txtIdUsuario.getText() + " ?", "Deletar usuário", JOptionPane.YES_NO_OPTION);

        if (deletar == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM usuarios where iduser=?";
            try {
                // Preparando a conexão
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdUsuario.getText());
                int apagado = pst.executeUpdate();
                // Se o usuário for deletado com sucesso do sistema ele devolve a mensagem de sucesso!.
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
                    limparCampo();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        txtSenhaUsuario = new javax.swing.JTextField();
        txtNomeUsuario = new javax.swing.JTextField();
        txtLoginUsuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboPerfil = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnCriarUsuario = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();
        btnConsultarUsuario = new javax.swing.JButton();
        btnDeletarUsuario = new javax.swing.JButton();
        lblErro = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTelefoneUsuario = new javax.swing.JFormattedTextField();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastrar Usuário");
        setPreferredSize(new java.awt.Dimension(678, 601));

        jPanel1.setBackground(new java.awt.Color(229, 228, 228));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-adicionar-usuário-masculino-30.png"))); // NOI18N
        jLabel1.setText(" CADASTRAR USUÁRIOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("* Id :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("* Nome :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("* Login :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("* Senha :");

        txtIdUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtSenhaUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtNomeUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNomeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeUsuarioActionPerformed(evt);
            }
        });

        txtLoginUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Celular:");

        comboPerfil.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("* Perfil : ");

        btnCriarUsuario.setBackground(new java.awt.Color(153, 255, 153));
        btnCriarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCriarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnCriarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-mais-2-matemática-10.png"))); // NOI18N
        btnCriarUsuario.setText("CRIAR");
        btnCriarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCriarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarUsuarioActionPerformed(evt);
            }
        });

        btnEditarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        btnEditarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-edite-a-linha-10.png"))); // NOI18N
        btnEditarUsuario.setText("EDITAR");
        btnEditarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnConsultarUsuario.setBackground(new java.awt.Color(255, 204, 0));
        btnConsultarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsultarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-cliente-de-pesquisa-10.png"))); // NOI18N
        btnConsultarUsuario.setText("CONSULTAR");
        btnConsultarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarUsuarioActionPerformed(evt);
            }
        });

        btnDeletarUsuario.setBackground(new java.awt.Color(255, 102, 102));
        btnDeletarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnDeletarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-excluir-10.png"))); // NOI18N
        btnDeletarUsuario.setText("DELETAR");
        btnDeletarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarUsuarioActionPerformed(evt);
            }
        });

        lblErro.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblErro.setForeground(new java.awt.Color(255, 0, 0));
        lblErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("LIMPAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Campos obrigatórios *");

        try {
            txtTelefoneUsuario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefoneUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnCriarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(btnEditarUsuario)
                        .addGap(82, 82, 82)
                        .addComponent(btnConsultarUsuario)
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeletarUsuario)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSenhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefoneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSenhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTelefoneUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblErro, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCriarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 750, 601);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeUsuarioActionPerformed

    private void btnConsultarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarUsuarioActionPerformed
        Consultar();
    }//GEN-LAST:event_btnConsultarUsuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limparCampo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCriarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarUsuarioActionPerformed
        adicionar();
    }//GEN-LAST:event_btnCriarUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void btnDeletarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarUsuarioActionPerformed
        deletar();
    }//GEN-LAST:event_btnDeletarUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarUsuario;
    private javax.swing.JButton btnCriarUsuario;
    private javax.swing.JButton btnDeletarUsuario;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JComboBox<String> comboPerfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtLoginUsuario;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JTextField txtSenhaUsuario;
    private javax.swing.JFormattedTextField txtTelefoneUsuario;
    // End of variables declaration//GEN-END:variables
}
